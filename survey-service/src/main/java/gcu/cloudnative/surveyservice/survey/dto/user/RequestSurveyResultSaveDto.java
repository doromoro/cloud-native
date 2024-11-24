package gcu.cloudnative.surveyservice.survey.dto.user;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RequestSurveyResultSaveDto {
    private Long memberId;
    private Long surveyId;
    private List<QstAnsIdSet> QstAnsIdSets;

    @Getter
    @Setter
    public static class QstAnsIdSet {
        private Long qstId;
        private Long ansId;
    }
}
