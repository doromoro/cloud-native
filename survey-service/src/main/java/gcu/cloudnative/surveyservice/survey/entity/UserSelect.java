package gcu.cloudnative.surveyservice.survey.entity;

import gcu.cloudnative.surveyservice.global.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "user_select")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserSelect extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_ans_id")
    private UserAns userAns;

    @Column(name = "qst_id")
    private Long qstId;

    @Column(name = "ans_id")
    private Long ansId;

    @Builder
    public UserSelect(UserAns userAns, Long qstId, Long ansId) {
        this.userAns = userAns;
        this.qstId = qstId;
        this.ansId = ansId;
    }

    public static UserSelect of(UserAns userAns, Long qstId, Long ansId) {
        return UserSelect.builder()
                .userAns(userAns)
                .qstId(qstId)
                .ansId(ansId)
                .build();
    }
}
