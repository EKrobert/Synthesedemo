package ma.ac.uir.synthesedemo.service;



import ma.ac.uir.synthesedemo.entity.Users;

import java.util.List;

public interface UserService {

    List<Users> findAll();

    Users findById(int theId);

    Users save(Users user);

    Users update(Users user);

    void deleteById(int theId);

    Users findByEMail(String email);

}
