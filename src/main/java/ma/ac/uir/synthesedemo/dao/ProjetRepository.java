package ma.ac.uir.synthesedemo.dao;


import ma.ac.uir.synthesedemo.entity.Projets;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjetRepository extends JpaRepository <Projets,Long> {
}
