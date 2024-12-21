package ma.ac.uir.synthesedemo.service;

import ma.ac.uir.synthesedemo.entity.Competences;

import java.util.List;

public interface CompetenceService {

    List<Competences> findAll();

    Competences findById(int theId);

    Competences save(Competences competences);

    Competences update(Competences competences);

    void deleteById(int theId);

    List<Competences> findAllById(List<Integer> theId);
}
