package ma.ac.uir.synthesedemo.service;


import ma.ac.uir.synthesedemo.dao.EvaluationRepository;
import ma.ac.uir.synthesedemo.entity.Evaluation;
import ma.ac.uir.synthesedemo.entity.Projets;
import ma.ac.uir.synthesedemo.entity.Users;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public
class EvaluationServiceImpl implements EvaluationService {
    EvaluationRepository repo;

    @Override
    public Evaluation getEvaluation(int id) {
        return null;
    }

    @Override
    public Evaluation saveEvaluation(Evaluation evaluation) {
        return repo.save(evaluation);
    }


}
