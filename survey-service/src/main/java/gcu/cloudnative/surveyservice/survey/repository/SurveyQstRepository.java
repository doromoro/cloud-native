package gcu.cloudnative.surveyservice.survey.repository;

import gcu.cloudnative.surveyservice.survey.entity.SurveyQst;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SurveyQstRepository extends JpaRepository<SurveyQst, Long>, JpaSpecificationExecutor<SurveyQst> {

    // 여러 질문 찾기
    List<SurveyQst> findAllBySurveyId(Long surveyId);
}
