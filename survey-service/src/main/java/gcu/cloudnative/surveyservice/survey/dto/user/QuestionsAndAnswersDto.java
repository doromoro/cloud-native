package gcu.cloudnative.surveyservice.survey.dto.user;

import gcu.cloudnative.surveyservice.survey.entity.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class QuestionsAndAnswersDto {
    private List<QstAnsSet> qstAnsSets;
    private Long surveyId;

    @Getter
    @Setter
    public static class QstAnsSet {
        private Long qstId;
        private String qstText;
        private List<AnsSet> ansSets;

        public static QstAnsSet fromQst(SurveyQst surveyQst, List<AnsSet> ansSets) {
            QstAnsSet qstAnsSet = new QstAnsSet();
            qstAnsSet.setQstId(surveyQst.getId());
            qstAnsSet.setQstText(surveyQst.getQuestionTxt());
            qstAnsSet.setAnsSets(ansSets);
            return qstAnsSet;
        }
    }

    @Getter
    @Setter
    public static class AnsSet {
        private Long ansId;
        private String ansText;
        private String value;

        public static AnsSet fromAns(SurveyAns surveyAns) {
            AnsSet ansSet = new AnsSet();
            ansSet.setAnsId(surveyAns.getId());
            ansSet.setAnsText(surveyAns.getAnswerTxt());
            ansSet.setValue(surveyAns.getValue());
            return ansSet;
        }
    }

    public static QuestionsAndAnswersDto createResponses(List<QstAnsSet> qstAnsSets, Long surveyId) {
        QuestionsAndAnswersDto response = new QuestionsAndAnswersDto();
        response.setQstAnsSets(qstAnsSets);
        response.setSurveyId(surveyId);
        return response;
    }
}
