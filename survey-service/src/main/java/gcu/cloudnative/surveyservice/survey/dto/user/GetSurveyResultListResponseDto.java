package gcu.cloudnative.surveyservice.survey.dto.user;

import gcu.cloudnative.surveyservice.survey.entity.UserAns;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class GetSurveyResultListResponseDto {

    private List<SurveyResultListInfo> surveyList;
    private int totalPages;
    private boolean hasNext;

    @Getter
    @Setter
    public static class SurveyResultListInfo {
        private Long userAnsId;
        private String title;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public static SurveyResultListInfo fromUserAns(UserAns userAns) {
            SurveyResultListInfo info = new SurveyResultListInfo();
            info.setUserAnsId(userAns.getId());
            info.setTitle(userAns.getSurvey().getTitle());
            info.setCreatedAt(userAns.getCreatedAt());
            info.setUpdatedAt(userAns.getUpdatedAt());
            return info;
        }
    }

    public static GetSurveyResultListResponseDto createResponse(List<SurveyResultListInfo> surveyList, int totalPages, boolean hasNext) {
        GetSurveyResultListResponseDto response = new GetSurveyResultListResponseDto();
        response.setSurveyList(surveyList);
        response.setTotalPages(totalPages);
        response.setHasNext(hasNext);
        return response;
    }
}
