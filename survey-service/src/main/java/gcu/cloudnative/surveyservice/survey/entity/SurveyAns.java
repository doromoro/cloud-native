package gcu.cloudnative.surveyservice.survey.entity;

import gcu.cloudnative.surveyservice.global.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "survey_ans")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SurveyAns extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "survey_qst_id")
    private SurveyQst surveyQst;

    @Column(name = "answer_txt")
    private String answerTxt;

    @Column(name = "value")
    private String value;

    @Builder
    public SurveyAns(SurveyQst surveyQst, String answerTxt, String value) {
        this.surveyQst = surveyQst;
        this.answerTxt = answerTxt;
        this.value = value;
    }

    public static SurveyAns of(SurveyQst surveyQst, String answerTxt, String value) {
        return SurveyAns.builder()
                .surveyQst(surveyQst)
                .answerTxt(answerTxt)
                .value(value)
                .build();
    }
}
