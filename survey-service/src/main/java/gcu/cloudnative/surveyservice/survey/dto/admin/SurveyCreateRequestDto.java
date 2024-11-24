package gcu.cloudnative.surveyservice.survey.dto.admin;


import gcu.cloudnative.surveyservice.survey.entity.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SurveyCreateRequestDto {
    private Long memberId;
    private String title;
    private List<SurveyQuestion> questionList;

    @Getter
    @Setter
    public static class SurveyQuestion {
        private String questionTxt;
        private List<SurveyAnswer> answerList;

        public static SurveyQuestion of(String questionTxt, List<SurveyAnswer> answerList) {
            SurveyQuestion qst = new SurveyQuestion();
            qst.setQuestionTxt(questionTxt);
            qst.setAnswerList(answerList);
            return qst;
        }

        public static SurveyQst createSurveyQst(Survey survey, SurveyQuestion qst){
            return SurveyQst.builder()
                    .survey(survey)
                    .questionTxt(qst.getQuestionTxt())
                    .build();
        }
    }

    @Getter
    @Setter
    public static class SurveyAnswer {
        private String answerTxt;
        private String value;

        public static SurveyAnswer of(String answerTxt, String value) {
            SurveyAnswer ans = new SurveyAnswer();
            ans.setAnswerTxt(answerTxt);
            ans.setValue(value);
            return ans;
        }

        public static SurveyAns createSurveyAns(SurveyQst thisSurveyQst, SurveyAnswer ans) {
            return SurveyAns.builder()
                    .surveyQst(thisSurveyQst)
                    .answerTxt(ans.getAnswerTxt())
                    .value(ans.getValue())
                    .build();
        }

    }

    public static SurveyCreateRequestDto of(List<SurveyQuestion> questionList, String title, Long memberId) {
        SurveyCreateRequestDto requestDto = new SurveyCreateRequestDto();
        requestDto.setQuestionList(questionList);
        requestDto.setTitle(title);
        requestDto.setMemberId(memberId);
        return requestDto;
    }

    public static Survey createSurvey(Member member, String title){
        return Survey.builder()
                .member(member)
                .surveyCnt(0)
                .title(title)
                .state(SurveyState.ACTIVE)
                .build();
    }
}
