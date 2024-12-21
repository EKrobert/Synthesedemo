package ma.ac.uir.synthesedemo.service;
import ma.ac.uir.synthesedemo.dao.CompetenceRepository;
import ma.ac.uir.synthesedemo.entity.Competences;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompetenceServiceImpl implements CompetenceService {

    private CompetenceRepository competenceRepository;

    @Autowired
    public CompetenceServiceImpl(CompetenceRepository competenceRepository) {
        this.competenceRepository = competenceRepository;
    }

    @Override
    public List<Competences> findAll() {
        return competenceRepository.findAll();
    }

    @Override
    public Competences findById(int theId) {
        Optional<Competences> result = competenceRepository.findById(theId);

        Competences theCompetences = null;

        if (result.isPresent()) {
            theCompetences = result.get();
        }
        else {
            throw new RuntimeException("!find compétence id - " + theId);
        }
        return theCompetences;
    }

    @Override
    public Competences save(Competences competences) {
        return competenceRepository.save(competences);
    }

    @Override
    public Competences update(Competences competences) {
        return competenceRepository.save(competences);
    }

    @Override
    public void deleteById(int theId) {
        competenceRepository.deleteById(theId);
    }

    @Override
    public List<Competences> findAllById(List<Integer> theId) {
        return competenceRepository.findAllById(theId);
    }
}
