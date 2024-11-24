package gcu.cloudnative.surveyservice.survey.repository;

import gcu.cloudnative.surveyservice.survey.entity.UserAns;
import gcu.cloudnative.surveyservice.survey.entity.UserAnsState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAnsRepository extends JpaRepository<UserAns, Long>, JpaSpecificationExecutor<UserAns> {
    Optional<UserAns> findByIdAndState(Long id, UserAnsState state);

    Page<UserAns> findAllByMemberIdAndState(Long memberId, UserAnsState state, Pageable pageable);
}

