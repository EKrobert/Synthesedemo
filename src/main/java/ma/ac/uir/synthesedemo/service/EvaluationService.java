package ma.ac.uir.synthesedemo.service;


import ma.ac.uir.synthesedemo.entity.Evaluation;
import ma.ac.uir.synthesedemo.entity.Projets;
import ma.ac.uir.synthesedemo.entity.Users;

import java.util.List;

public interface EvaluationService {
    Evaluation getEvaluation(Long id);

    Evaluation saveEvaluation(Evaluation evaluation);

    List<Evaluation> findEvaluationsByProjectId(Long projectId);

    Evaluation findByUserIdAndProjectId(Long userId, Long projectId);

}