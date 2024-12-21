package ma.ac.uir.synthesedemo.dao;


import ma.ac.uir.synthesedemo.entity.Competences;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompetenceRepository extends JpaRepository <Competences, Integer> {
    List<Competences> findAllById(int theId);
}
