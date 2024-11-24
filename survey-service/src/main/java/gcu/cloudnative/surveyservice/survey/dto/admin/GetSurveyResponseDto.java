package gcu.cloudnative.surveyservice.survey.dto.admin;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import gcu.cloudnative.surveyservice.survey.entity.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class GetSurveyResponseDto {
    private Long surveyId;
    private Long memberId; // 설문을 만든 사람
    private Integer surveyCnt;
    private SurveyState state;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<GetSurveyQuestion> questionList;

    @Getter
    @Setter
    public static class GetSurveyQuestion {
        private Long surveyQstId;
        private Long surveyId;
        private String questionTxt;
        private List<GetSurveyAnswer> answerList;

        public static GetSurveyQuestion fromSurveyQst(SurveyQst surveyQst, List<GetSurveyAnswer> answerList) {
            GetSurveyQuestion qst = new GetSurveyQuestion();
            qst.setSurveyQstId(surveyQst.getId());
            qst.setSurveyId(surveyQst.getSurvey().getId());
            qst.setQuestionTxt(surveyQst.getQuestionTxt());
            qst.setAnswerList(answerList);
            return qst;
        }
    }

    @Getter
    @Setter
    public static class GetSurveyAnswer {
        private Long surveyAnsId;
        private Long surveyQstId;
        private String answerTxt;
        private String value;

        public static GetSurveyAnswer fromSurveyAns(SurveyAns surveyAns){
            GetSurveyAnswer ans = new GetSurveyAnswer();
            ans.setSurveyAnsId(surveyAns.getId());
            ans.setSurveyQstId(surveyAns.getSurveyQst().getId());
            ans.setAnswerTxt(surveyAns.getAnswerTxt());
            ans.setValue(surveyAns.getValue());
            return ans;
        }
    }

    public static GetSurveyResponseDto fromSurvey(Survey survey, List<GetSurveyQuestion> questionList){
        GetSurveyResponseDto info = new GetSurveyResponseDto();
        info.setSurveyId(survey.getId());
        info.setMemberId(survey.getMember().getId());
        info.setSurveyCnt(survey.getSurveyCnt());
        info.setState(survey.getState());
        info.setCreatedAt(survey.getCreatedAt());
        info.setUpdatedAt(survey.getUpdatedAt());
        info.setQuestionList(questionList);
        return info;
    }
}
