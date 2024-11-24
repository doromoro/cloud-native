package gcu.cloudnative.surveyservice.survey.entity;

import gcu.cloudnative.surveyservice.global.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "survey")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Survey extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "survey_cnt")
    @ColumnDefault("0")
    private Integer surveyCnt;

    @Column(name = "title")
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private SurveyState state;

    @Builder
    public Survey(Member member, Integer surveyCnt, String title, SurveyState state) {
        this.member = member;
        this.surveyCnt = surveyCnt;
        this.title = title;
        this.state = state;
    }

    public static Survey of(Member member, Integer surveyCnt, String title, SurveyState state){
        return Survey.builder()
                .member(member)
                .surveyCnt(surveyCnt)
                .title(title)
                .state(state)
                .build();
    }
}
