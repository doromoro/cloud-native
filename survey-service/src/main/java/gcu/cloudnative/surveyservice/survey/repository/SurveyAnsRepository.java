package gcu.cloudnative.surveyservice.survey.repository;

import gcu.cloudnative.surveyservice.survey.entity.SurveyAns;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SurveyAnsRepository extends JpaRepository<SurveyAns, Long>, JpaSpecificationExecutor<SurveyAns> {
    List<SurveyAns> findAllBySurveyQstId(Long surveyQstId);
}
