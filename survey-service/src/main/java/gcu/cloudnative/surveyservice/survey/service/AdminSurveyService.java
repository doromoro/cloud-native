package gcu.cloudnative.surveyservice.survey.service;

import gcu.cloudnative.surveyservice.global.CustomResponseCode;
import gcu.cloudnative.surveyservice.survey.dto.admin.GetSurveyResponseDto;
import gcu.cloudnative.surveyservice.survey.dto.admin.SurveyCreateRequestDto;
import org.springframework.http.ResponseEntity;

public interface AdminSurveyService {
    ResponseEntity<CustomResponseCode> createSurvey(SurveyCreateRequestDto dto);

    ResponseEntity<CustomResponseCode> deleteSurvey(Long surveyId);

    GetSurveyResponseDto getSurveyInfo(Long surveyId);
}
