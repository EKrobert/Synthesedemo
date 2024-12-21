package ma.ac.uir.synthesedemo.dao;

import ma.ac.uir.synthesedemo.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsersRepository extends JpaRepository<Users, Long> {
    Users findByEmail(String email);
}
