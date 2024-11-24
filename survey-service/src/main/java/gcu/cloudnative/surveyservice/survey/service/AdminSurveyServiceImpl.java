package gcu.cloudnative.surveyservice.survey.service;


import gcu.cloudnative.surveyservice.global.CustomException;
import gcu.cloudnative.surveyservice.global.CustomResponseCode;
import gcu.cloudnative.surveyservice.global.ResponseCode;
import gcu.cloudnative.surveyservice.survey.dto.admin.GetSurveyResponseDto;
import gcu.cloudnative.surveyservice.survey.dto.admin.SurveyCreateRequestDto;
import gcu.cloudnative.surveyservice.survey.entity.*;
import gcu.cloudnative.surveyservice.survey.repository.MemberRepository;
import gcu.cloudnative.surveyservice.survey.repository.SurveyAnsRepository;
import gcu.cloudnative.surveyservice.survey.repository.SurveyQstRepository;
import gcu.cloudnative.surveyservice.survey.repository.SurveyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminSurveyServiceImpl implements AdminSurveyService{

    private final SurveyRepository surveyRepository;
    private final SurveyAnsRepository surveyAnsRepository;
    private final SurveyQstRepository surveyQstRepository;
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public ResponseEntity<CustomResponseCode> createSurvey(SurveyCreateRequestDto dto) {
        // survey 저장
        Long memberId = dto.getMemberId();
        Member member = checkConflictMember(memberId);
        Survey survey = surveyRepository.save(SurveyCreateRequestDto.createSurvey(member, dto.getTitle()));
        // surveyQst, surveyAns 저장
        for(SurveyCreateRequestDto.SurveyQuestion surveyQuestion : dto.getQuestionList()){
            SurveyQst qst = surveyQstRepository.save(SurveyCreateRequestDto.SurveyQuestion.createSurveyQst(survey,surveyQuestion));

            List<SurveyAns> surveyAns = new ArrayList<>();
            for(SurveyCreateRequestDto.SurveyAnswer surveyAnswer : surveyQuestion.getAnswerList()) {
                surveyAns.add(SurveyCreateRequestDto.SurveyAnswer.createSurveyAns(qst, surveyAnswer));
            }
            surveyAnsRepository.saveAll(surveyAns);
        }
        return ResponseEntity.ok(CustomResponseCode.SURVEY_CREATE_SUCCESS);
    }

    @Override
    @Transactional
    public ResponseEntity<CustomResponseCode> deleteSurvey(Long surveyId) {
        Survey survey = checkConflictSurvey(surveyId);
        survey.setState(SurveyState.INACTIVE);
        surveyRepository.save(survey);
        return ResponseEntity.ok(CustomResponseCode.SURVEY_DELETE_SUCCESS);
    }

    @Override
    @Transactional(readOnly = true)
    public GetSurveyResponseDto getSurveyInfo(Long surveyId) {
        Survey survey = checkConflictSurvey(surveyId);
        // 질문 리스트 가져오기
        List<SurveyQst> qstList = surveyQstRepository.findAllBySurveyId(surveyId);
        // 각 질문의 답변들을 가져와서 dto 부품 객체 생성
        List<GetSurveyResponseDto.GetSurveyQuestion> getSurveyQuestionList = new ArrayList<>();
        for(SurveyQst qst : qstList) {
            List<GetSurveyResponseDto.GetSurveyAnswer> getSurveyAnswerList = surveyAnsRepository.findAllBySurveyQstId(qst.getId()).stream()
                    .map(GetSurveyResponseDto.GetSurveyAnswer::fromSurveyAns)
                    .toList();
            getSurveyQuestionList.add(GetSurveyResponseDto.GetSurveyQuestion.fromSurveyQst(qst, getSurveyAnswerList));
        }
        return GetSurveyResponseDto.fromSurvey(survey, getSurveyQuestionList);
    }

    private Survey checkConflictSurvey(Long id) {
        if (surveyRepository.findByIdAndState(id, SurveyState.ACTIVE).isEmpty()) {
            throw new CustomException(ResponseCode.SURVEY_NOT_FOUND);
        }
        if (surveyRepository.findByIdAndState(id, SurveyState.ACTIVE).get().getState().equals(SurveyState.INACTIVE)) {
            throw new CustomException(ResponseCode.SURVEY_DELETED);
        }
        return surveyRepository.findByIdAndState(id, SurveyState.ACTIVE).get();
    }

    private Member checkConflictMember(Long id) {
        if (memberRepository.findById(id).isEmpty()) {
            throw new CustomException(ResponseCode.MEMBER_NOT_FOUND);
        }
        if (memberRepository.findById(id).get().getState().equals(MemberState.INACTIVE)) {
            throw new CustomException(ResponseCode.MEMBER_DELETED);
        }
        return memberRepository.findById(id).get();
    }

}
