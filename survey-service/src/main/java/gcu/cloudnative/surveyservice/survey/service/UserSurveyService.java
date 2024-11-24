package gcu.cloudnative.surveyservice.survey.service;


import gcu.cloudnative.surveyservice.global.CustomResponseCode;
import gcu.cloudnative.surveyservice.global.OrderType;
import gcu.cloudnative.surveyservice.global.SearchType;
import gcu.cloudnative.surveyservice.survey.dto.user.GetSurveyListResponseDto;
import gcu.cloudnative.surveyservice.survey.dto.user.GetSurveyResultListResponseDto;
import gcu.cloudnative.surveyservice.survey.dto.user.QuestionsAndAnswersDto;
import gcu.cloudnative.surveyservice.survey.dto.user.RequestSurveyResultSaveDto;
import org.springframework.http.ResponseEntity;

public interface UserSurveyService {
    QuestionsAndAnswersDto getQuestionsAndAnswers(Long surveyId);

    QuestionsAndAnswersDto saveSurveyResult(RequestSurveyResultSaveDto dto);

    QuestionsAndAnswersDto getSurveyResult(Long userAnsId);

    GetSurveyResultListResponseDto getSurveyResultList(int page, Long memberId);

    ResponseEntity<CustomResponseCode> deleteUserAns(Long userAnsId);

    GetSurveyListResponseDto getSurveyList(int page);
}
