package ma.ac.uir.synthesedemo.service;


import ma.ac.uir.synthesedemo.entity.Projets;

import java.util.List;

public interface ProjetService {
    List<Projets> findAll();
    List<Projets> findAllById(Long id);

    Projets findById(Long theId);

    Projets save(Projets projets);

    Projets update(Projets projets);

    void deleteById(Long theId);
}
