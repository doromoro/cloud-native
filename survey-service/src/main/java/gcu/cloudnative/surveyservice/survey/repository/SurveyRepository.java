package gcu.cloudnative.surveyservice.survey.repository;

import gcu.cloudnative.surveyservice.survey.entity.Survey;
import gcu.cloudnative.surveyservice.survey.entity.SurveyState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SurveyRepository extends JpaRepository<Survey, Long>, JpaSpecificationExecutor<Survey> {
    Optional<Survey> findByIdAndState(Long id, SurveyState state);
    Optional<Survey> findTopByStateOrderByCreatedAt(SurveyState state);

    Page<Survey> findAllByState(SurveyState state, Pageable pageable);
}
