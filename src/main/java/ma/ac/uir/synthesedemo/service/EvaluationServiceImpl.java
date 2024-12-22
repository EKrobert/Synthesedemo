package ma.ac.uir.synthesedemo.service;


import ma.ac.uir.synthesedemo.dao.EvaluationRepository;
import ma.ac.uir.synthesedemo.entity.Evaluation;
import ma.ac.uir.synthesedemo.entity.Projets;
import ma.ac.uir.synthesedemo.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public
class EvaluationServiceImpl implements EvaluationService {
    @Autowired
    EvaluationRepository repo;

    public EvaluationServiceImpl(EvaluationRepository repo ) {
        this.repo = repo;
    }

    @Override
    public Evaluation getEvaluation(Long id) {
        return repo.getReferenceById(id);
    }

    @Override
    public Evaluation saveEvaluation(Evaluation evaluation) {
        return repo.save(evaluation);
    }

    @Override
    public List<Evaluation> findEvaluationsByProjectId(Long projectId) {
        return repo.findEvaluationsByProjectId(projectId);
    }

    @Override
    public Evaluation findByUserIdAndProjectId(Long userId, Long projectId) {
        return repo.findByUserIdAndProjectId(userId, projectId);
    }


}
