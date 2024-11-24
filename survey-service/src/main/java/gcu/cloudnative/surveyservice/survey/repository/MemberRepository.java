package gcu.cloudnative.surveyservice.survey.repository;

import gcu.cloudnative.surveyservice.survey.entity.Member;
import gcu.cloudnative.surveyservice.survey.entity.MemberState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long>, JpaSpecificationExecutor<Member> {
    Optional<Member> findByIdAndState(Long id, MemberState state);
    Optional<Member> findByEmailAndPasswordAndState(String email, String password, MemberState state);
}

