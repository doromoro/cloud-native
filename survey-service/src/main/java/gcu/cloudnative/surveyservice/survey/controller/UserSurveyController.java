package gcu.cloudnative.surveyservice.survey.controller;

import gcu.cloudnative.surveyservice.global.CustomResponseCode;
import gcu.cloudnative.surveyservice.global.OrderType;
import gcu.cloudnative.surveyservice.global.SearchType;
import gcu.cloudnative.surveyservice.survey.dto.user.GetSurveyListResponseDto;
import gcu.cloudnative.surveyservice.survey.dto.user.GetSurveyResultListResponseDto;
import gcu.cloudnative.surveyservice.survey.dto.user.QuestionsAndAnswersDto;
import gcu.cloudnative.surveyservice.survey.dto.user.RequestSurveyResultSaveDto;
import gcu.cloudnative.surveyservice.survey.service.UserSurveyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "[사용자] 설문 관련 API", description = "Survey API")
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/survey/user")
public class UserSurveyController {

    private final UserSurveyService userSurveyService;

    // 1. 설문 목록 조회 API
    @Operation(summary = "설문 목록 조회 API", description = "설문 목록을 조회합니다.")
    @GetMapping("/list/{page}")
    ResponseEntity<GetSurveyListResponseDto> getSurveyList(
            @PathVariable("page") int page
    ) {
        log.info("[Survey Controller - getSurveyList] : 설문 목록 조회 요청이 들어왔습니다. page : {}", page);
        return ResponseEntity.ok(userSurveyService.getSurveyList(page-1));
    }

    // 2. 질문과 답변 세트 조회 API
    @Operation(summary = "질문과 답변 세트 조회 API", description = "해당 설문에 존재하는 질문과 답변 세트를 조회합니다.")
    @GetMapping("/questions/{surveyId}")
    ResponseEntity<QuestionsAndAnswersDto> getQuestionsAndAnswers(
            @PathVariable("surveyId") Long surveyId
    ) {
        log.info("[Survey Controller - getFirstQuestions] 질문과 답변 세트 조회 요청이 들어왔습니다. surveyId: {}", surveyId);
        QuestionsAndAnswersDto response = userSurveyService.getQuestionsAndAnswers(surveyId);
        log.info("[Survey Controller - getFirstQuestions] 요청에 대한 응답이 생성되었습니다. response: {}", response);
        return ResponseEntity.ok(response);
    }

    // 3. 설문 결과 저장
    @Operation(summary = "설문 결과 저장 API", description = "설문 결과를 저장합니다.")
    @PostMapping("/result")
    ResponseEntity<QuestionsAndAnswersDto> postSurveyResult(
            @RequestBody @Valid RequestSurveyResultSaveDto dto
    ) {
        log.info("[Survey Controller - postSurveyResult] : {}번 설문 결과를 저장 요청이 들어왔습니다.", dto.getSurveyId());
        return ResponseEntity.ok(userSurveyService.saveSurveyResult(dto));
    }

    // 4. 설문 결과 세부 조회
    @Operation(summary = "설문 결과 세부 조회 API", description = "설문 결과를 세부 조회합니다.")
    @GetMapping("/result/{userAnsId}")
    ResponseEntity<QuestionsAndAnswersDto> getSurveyResult(
            @PathVariable("userAnsId") Long userAnsId
    ){
        log.info("[Survey Controller - getSurveyResult] :  설문 결과 세부 조회 요청이 들어왔습니다. userAnsId : {}", userAnsId);
        return ResponseEntity.ok(userSurveyService.getSurveyResult(userAnsId));
    }

    // 5. 설문 결과 목록 조회
    @Operation(summary = "설문 결과 목록 조회 API", description = "설문 결과 목록을 조회합니다.")
    @GetMapping("/result/list/{page}")
    ResponseEntity<GetSurveyResultListResponseDto> getSurveyResultList(
            @PathVariable("page") int page,
            @RequestParam("memberId") Long memberId
    ){
        log.info("[Survey Controller - getSurveyResultList] : 설문 결과 목록 조회 요청이 들어왔습니다. page : {}", page);
        return ResponseEntity.ok(userSurveyService.getSurveyResultList(page-1, memberId));
    }

    // 6. 작성한 설문 삭제
    @Operation(summary = "작성한 설문 삭제 API", description = "본인이 작성한 설문을 삭제합니다.")
    @DeleteMapping("/result/{userAnsId}")
    ResponseEntity<CustomResponseCode> deleteUserAns(
            @PathVariable("userAnsId") Long userAnsId
    ){
        log.info("[Survey Controller - deleteUserAns] : {}번 사용자 설문 결과 삭제 요청이 들어왔습니다.", userAnsId);
        return userSurveyService.deleteUserAns(userAnsId);
    }
}
