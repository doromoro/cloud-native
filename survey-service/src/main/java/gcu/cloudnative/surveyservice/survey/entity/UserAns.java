package gcu.cloudnative.surveyservice.survey.entity;

import gcu.cloudnative.surveyservice.global.BaseEntity;
import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@Entity
@Table(name = "user_ans")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserAns extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "survey_id")
    private Survey survey;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private UserAnsState state;

    @Builder
    public UserAns(Survey survey, Member member, UserAnsState state) {
        this.survey = survey;
        this.member = member;
        this.state = state;
    }

    public static UserAns of(Survey survey, Member member, UserAnsState state) {
        return UserAns.builder()
                .survey(survey)
                .member(member)
                .state(state)
                .build();
    }
}
