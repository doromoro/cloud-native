package gcu.cloudnative.surveyservice.survey.controller;

import gcu.cloudnative.surveyservice.global.CustomResponseCode;
import gcu.cloudnative.surveyservice.survey.dto.admin.GetSurveyResponseDto;
import gcu.cloudnative.surveyservice.survey.dto.admin.SurveyCreateRequestDto;
import gcu.cloudnative.surveyservice.survey.service.AdminSurveyService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "[관리자] 설문 관련 API", description = "Survey API")
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/survey/admin")
public class AdminSurveyController {
    private final AdminSurveyService adminSurveyService;

    // 1. 설문 생성 API
    @PostMapping("/create")
    public ResponseEntity<CustomResponseCode> createSurvey(
            @RequestBody SurveyCreateRequestDto dto
    ){
        return adminSurveyService.createSurvey(dto);
    }

    // 2. 설문 삭제 API
    @DeleteMapping("/delete")
    public ResponseEntity<CustomResponseCode> deleteSurvey(
            @RequestParam Long surveyId
    ){
        return adminSurveyService.deleteSurvey(surveyId);
    }

    // 3. 설문 세부 조회 API
    @GetMapping("/{surveyId}")
    public ResponseEntity<GetSurveyResponseDto> getSurveyInfo(@PathVariable("surveyId") Long surveyId) {
        return ResponseEntity.ok(adminSurveyService.getSurveyInfo(surveyId));
    }

}
