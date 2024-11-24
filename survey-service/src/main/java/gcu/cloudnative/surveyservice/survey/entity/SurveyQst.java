package gcu.cloudnative.surveyservice.survey.entity;

import gcu.cloudnative.surveyservice.global.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "survey_qst")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SurveyQst extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "survey_id")
    private Survey survey;

    @Column(name = "question_txt")
    private String questionTxt;

    @Builder
    public SurveyQst(Survey survey, String questionTxt) {
        this.survey = survey;
        this.questionTxt = questionTxt;
    }

    public static SurveyQst of(Survey survey, String questionTxt) {
        return SurveyQst.builder()
                .survey(survey)
                .questionTxt(questionTxt)
                .build();
    }
}
