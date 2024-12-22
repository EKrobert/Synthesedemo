package ma.ac.uir.synthesedemo.dao;


import ma.ac.uir.synthesedemo.entity.Projets;
import ma.ac.uir.synthesedemo.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjetRepository extends JpaRepository <Projets,Long> {
    List<Projets> findAllByCreatedBy(Users createdBy);
}
