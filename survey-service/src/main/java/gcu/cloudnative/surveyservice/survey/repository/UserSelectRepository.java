package gcu.cloudnative.surveyservice.survey.repository;

import gcu.cloudnative.surveyservice.survey.entity.UserSelect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserSelectRepository extends JpaRepository<UserSelect, Long> {
    List<UserSelect> findAllByUserAnsId(Long userAnsId);
}
