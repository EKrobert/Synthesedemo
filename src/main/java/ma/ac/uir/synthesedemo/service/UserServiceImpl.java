package ma.ac.uir.synthesedemo.service;


import ma.ac.uir.synthesedemo.dao.UsersRepository;
import ma.ac.uir.synthesedemo.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UsersRepository userRepository;

    @Autowired
    public UserServiceImpl(UsersRepository theUserRepository) {
        this.userRepository = theUserRepository;
    }

    @Override
    public List<ma.ac.uir.synthesedemo.entity.Users> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Users findById(int theId) {
        Optional<Users> result = userRepository.findById((long) theId);

        Users user = null;

        if (result.isPresent()) {
            user = result.get();
        }
        else {
            throw new RuntimeException("!find user id - " + theId);
        }
        return user;
    }

    @Override
    public Users save(Users user) {
        return userRepository.save(user);
    }

    @Override
    public Users update(Users user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteById(int theId) {
        userRepository.deleteById((long) theId);
    }

    @Override
    public Users findByEMail(String email) {
        return userRepository.findByEmail(email);
    }




}
