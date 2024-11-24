package gcu.cloudnative.surveyservice.survey.service;

import gcu.cloudnative.surveyservice.survey.entity.Member;
import gcu.cloudnative.surveyservice.survey.entity.MemberState;
import gcu.cloudnative.surveyservice.survey.repository.MemberRepository;
import gcu.cloudnative.surveyservice.global.*;
import gcu.cloudnative.surveyservice.survey.dto.user.GetSurveyListResponseDto;
import gcu.cloudnative.surveyservice.survey.dto.user.GetSurveyResultListResponseDto;
import gcu.cloudnative.surveyservice.survey.dto.user.QuestionsAndAnswersDto;
import gcu.cloudnative.surveyservice.survey.dto.user.RequestSurveyResultSaveDto;
import gcu.cloudnative.surveyservice.survey.entity.*;
import gcu.cloudnative.surveyservice.survey.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserSurveyServiceImpl implements UserSurveyService{
    private final SurveyRepository surveyRepository;
    private final SurveyAnsRepository surveyAnsRepository;
    private final SurveyQstRepository surveyQstRepository;
    private final UserAnsRepository userAnsRepository;
    private final UserSelectRepository userSelectRepository;

    private final MemberRepository memberRepository;


    @Override
    @Transactional(readOnly = true)
    public GetSurveyListResponseDto getSurveyList(int page) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<Survey> surveys = surveyRepository.findAllByState(SurveyState.ACTIVE, pageable);
        List<GetSurveyListResponseDto.SurveyListInfo> listInfos = surveys.getContent().stream()
                .map(GetSurveyListResponseDto.SurveyListInfo::fromSurvey)
                .toList();
        return GetSurveyListResponseDto.createResponse(listInfos, surveys.getTotalPages(), surveys.hasNext());
    }

    @Override
    @Transactional(readOnly = true)
    public QuestionsAndAnswersDto getQuestionsAndAnswers(Long surveyId) {
        Survey survey = findSurveyByIdAndState(surveyId, SurveyState.ACTIVE);
        // 질문 세팅
        log.info("[Survey Service - getQuestionsAndAnswers]: {}번 설문의 질문을 조회합니다.", survey.getId());
        List<SurveyQst> questions = findQuestions(survey.getId());
        log.info("[Survey Service - getQuestionsAndAnswers]: 질문을 모두 조회했습니다.");

        // 각 질문에 대한 답변 세팅
        List<QuestionsAndAnswersDto.QstAnsSet> qstAnsSets = new ArrayList<QuestionsAndAnswersDto.QstAnsSet>();
        for(SurveyQst surveyQst : questions) {
            log.info("[Survey Service - getQuestionsAndAnswers]: 조회한 각 질문들의 답변을 조회합니다. qstId : {}", surveyQst.getId());
            // 답변 불러오기
            List<QuestionsAndAnswersDto.AnsSet> answers = findAnswers(surveyQst.getId()).stream()
                    .map(QuestionsAndAnswersDto.AnsSet::fromAns)
                    .toList();
            log.info("[Survey Service - getQuestionsAndAnswers]: {}번 질문의 답변을 조회했습니다. answers : {}", surveyQst.getId(), answers.stream().toList());
            QuestionsAndAnswersDto.QstAnsSet ansSet = QuestionsAndAnswersDto.QstAnsSet.fromQst(surveyQst, answers);
            qstAnsSets.add(ansSet);
        }
        log.info("[Survey Service - getQuestionsAndAnswers]: 질문과 답변을 모두 조회했습니다.");
        return QuestionsAndAnswersDto.createResponses(qstAnsSets, surveyId);
    }

    @Override
    @Transactional
    public QuestionsAndAnswersDto saveSurveyResult(RequestSurveyResultSaveDto dto) {
        log.info("[Survey Service - saveSurveyResult]: 설문 결과를 저장합니다. surveyId : {}", dto.getSurveyId());
        // 설문과 멤버 테이블 불러오기
        Long memberId = dto.getMemberId();
        Member member = findMember(memberId, MemberState.ACTIVE);
        Survey survey = findSurveyByIdAndState(dto.getSurveyId(), SurveyState.ACTIVE);
        // 사용자 답변 테이블에 저장
        UserAns userAns = userAnsRepository.save(UserAns.of(survey, member, UserAnsState.ACTIVE));
        // 사용자가 선택한 질문 및 답변 저장 및 응답 생성
        List<QuestionsAndAnswersDto.QstAnsSet> qstAnsSets = new ArrayList<>();
        for(RequestSurveyResultSaveDto.QstAnsIdSet ansIdSet : dto.getQstAnsIdSets()){
            Long ansId = ansIdSet.getAnsId();
            Long qstId = ansIdSet.getQstId();
            // 사용자가 선택한 질문 및 답변 저장
            userSelectRepository.save(UserSelect.of(userAns, ansId, qstId));

            // 응답 세팅
            SurveyAns surveyAns = findSelectedAnswer(ansId);
            SurveyQst surveyQst = findSelectedQuestion(qstId);
            List<QuestionsAndAnswersDto.AnsSet> ansSets = List.of(QuestionsAndAnswersDto.AnsSet.fromAns(surveyAns));
            QuestionsAndAnswersDto.QstAnsSet qstAnsSet = QuestionsAndAnswersDto.QstAnsSet.fromQst(surveyQst, ansSets);
            qstAnsSets.add(qstAnsSet);
        }
        // 설문 사용횟수 증가 후 저장
        survey.setSurveyCnt(survey.getSurveyCnt()+1);
        log.info("[Survey Service - saveSurveyResult]: 작성한 설문을 저장하고 설문 사용횟수를 증가시킵니다. surveyId : {}, surveyCnt: {}", survey.getId(), survey.getSurveyCnt());
        surveyRepository.save(survey);

        return QuestionsAndAnswersDto.createResponses(qstAnsSets, survey.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public QuestionsAndAnswersDto getSurveyResult(Long userAnsId) {
        log.info("[Survey Service - getSurveyResult]: 설문 결과를 세부조회합니다. userAnsId : {}", userAnsId);
        UserAns userAns = findUserAnswer(userAnsId, UserAnsState.ACTIVE);
        Survey survey = userAns.getSurvey();
        // userAnsId로 설문 결과 테이블 조회
        List<UserSelect> userSelects = userSelectRepository.findAllByUserAnsId(userAns.getId());
        // dto 생성
        List<QuestionsAndAnswersDto.QstAnsSet> qstAnsSets = new ArrayList<>();
        for(UserSelect select : userSelects){
            Long ansId = select.getAnsId();
            Long qstId = select.getQstId();
            // 응답 세팅
            SurveyAns surveyAns = findSelectedAnswer(ansId);
            SurveyQst surveyQst = findSelectedQuestion(qstId);
            List<QuestionsAndAnswersDto.AnsSet> ansSets = List.of(QuestionsAndAnswersDto.AnsSet.fromAns(surveyAns));
            QuestionsAndAnswersDto.QstAnsSet qstAnsSet = QuestionsAndAnswersDto.QstAnsSet.fromQst(surveyQst, ansSets);
            qstAnsSets.add(qstAnsSet);
        }

        return QuestionsAndAnswersDto.createResponses(qstAnsSets, survey.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public GetSurveyResultListResponseDto getSurveyResultList(int page, Long memberId) {
        Pageable pageable = PageRequest.of(page, 10);
        log.info("[Survey Service - getSurveyResultList]: 사용자 ID : {} 의 추천 결과 목록을 조회합니다. page : {}", memberId, page);

        log.info("[Survey Service - getSurveyResultList]: 설문 결과 목록을 DB에서 조회합니다.");
        Page<UserAns> listPage = userAnsRepository.findAllByMemberIdAndState(memberId, UserAnsState.ACTIVE, pageable);

        List<GetSurveyResultListResponseDto.SurveyResultListInfo> resultListInfos = listPage.getContent().stream()
                .map(GetSurveyResultListResponseDto.SurveyResultListInfo::fromUserAns)
                .toList();
        return GetSurveyResultListResponseDto.createResponse(resultListInfos, listPage.getTotalPages(), listPage.hasNext());
    }

    @Override
    @Transactional
    public ResponseEntity<CustomResponseCode> deleteUserAns(Long userAnsId) {
        log.info("[Survey Service - deleteUserAns]: {}번 설문 결과를 삭제합니다.", userAnsId);
        UserAns userAns = findUserAnswer(userAnsId, UserAnsState.ACTIVE);

        userAns.setState(UserAnsState.INACTIVE);
        log.info("[Survey Service - deleteUserAns]: 설문 결과를 삭제합니다.(soft delete)");
        userAnsRepository.save(userAns);
        return ResponseEntity.ok(CustomResponseCode.USER_ANS_DELETE_SUCCESS);
    }

    private Survey findSurveyByIdAndState(Long surveyId, SurveyState state) {
        return surveyRepository.findByIdAndState(surveyId, state)
                .orElseThrow(() -> new CustomException(ResponseCode.SURVEY_NOT_FOUND));
    }

    private SurveyAns findSelectedAnswer(Long surveyAnsId) {
        return surveyAnsRepository.findById(surveyAnsId)
                .orElseThrow(() -> new CustomException(ResponseCode.SURVEY_ANS_NOT_FOUND));
    }

    private SurveyQst findSelectedQuestion(Long surveyQstId) {
        return surveyQstRepository.findById(surveyQstId)
                .orElseThrow(() -> new CustomException(ResponseCode.SURVEY_QST_NOT_FOUND));
    }

    private List<SurveyAns> findAnswers(Long surveyQstId) {
        return surveyAnsRepository.findAllBySurveyQstId(surveyQstId);
    }
    private List<SurveyQst> findQuestions(Long surveyId) {
        return surveyQstRepository.findAllBySurveyId(surveyId);
    }
    private Member findMember(Long id, MemberState state) {
        return memberRepository.findByIdAndState(id, state)
                .orElseThrow(() -> new CustomException(ResponseCode.MEMBER_NOT_FOUND));
    }
    private UserAns findUserAnswer(Long id, UserAnsState state) {
        return userAnsRepository.findByIdAndState(id, state)
                .orElseThrow(() -> new CustomException(ResponseCode.SURVEY_USER_ANS_NOT_FOUND));
    }


}
