package gcu.cloudnative.surveyservice.survey.dto.user;

import gcu.cloudnative.surveyservice.survey.entity.Survey;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class GetSurveyListResponseDto {

    private List<SurveyListInfo> surveyList;
    private int totalPages;
    private boolean hasNext;

    @Getter
    @Setter
    public static class SurveyListInfo {
        private Long surveyId;
        private String title;
        private String name;
        private Integer surveyCnt;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public static SurveyListInfo fromSurvey(Survey survey) {
            SurveyListInfo info = new SurveyListInfo();
            info.setSurveyId(survey.getId());
            info.setTitle(survey.getTitle());
            info.setName(survey.getMember().getName());
            info.setSurveyCnt(survey.getSurveyCnt());
            info.setCreatedAt(survey.getCreatedAt());
            info.setUpdatedAt(survey.getUpdatedAt());
            return info;
        }
    }

    public static GetSurveyListResponseDto createResponse(List<SurveyListInfo> surveyList, int totalPages, boolean hasNext) {
        GetSurveyListResponseDto response = new GetSurveyListResponseDto();
        response.setSurveyList(surveyList);
        response.setTotalPages(totalPages);
        response.setHasNext(hasNext);
        return response;
    }
}
