package ma.ac.uir.synthesedemo.service;

import ma.ac.uir.synthesedemo.dao.ProjetRepository;
import ma.ac.uir.synthesedemo.entity.Projets;
import ma.ac.uir.synthesedemo.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ProjetServiceImpl implements ProjetService {

    private ProjetRepository projetRepository;
    @Autowired
    public ProjetServiceImpl(ProjetRepository projetRepository) {
        this.projetRepository = projetRepository;
    }

    @Override
    public List<Projets> findAll() {
        return projetRepository.findAll();
    }

    @Override
    public List<Projets> findAllById(Long id) {
        // Utiliser le repository pour récupérer les projets
        List<Projets> projets = projetRepository.findAllById(Collections.singletonList((long) Math.toIntExact(id)));
        return projets;
    }


    @Override
    public Projets findById(Long theId) {

        Optional <Projets> result = projetRepository.findById(theId);

        Projets projets = null;

        if (result.isPresent()) {
            projets = result.get();
        }
        else {
            throw new RuntimeException("!find projet id - " + theId);
        }
        return projets;
    }

    @Override
    public Projets save(Projets projets) {
        return projetRepository.save(projets);
    }

    @Override
    public Projets update(Projets projets) {
        return projetRepository.save(projets);
    }

    @Override
    public void deleteById(Long theId) {
        projetRepository.deleteById(theId);
    }

    @Override
    public List<Projets> findAllByCreatedBy(Users createdBy) {
        return projetRepository.findAllByCreatedBy(createdBy);
    }


}
