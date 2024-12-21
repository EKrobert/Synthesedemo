package ma.ac.uir.synthesedemo.service;

import ma.ac.uir.synthesedemo.entity.Users;
import ma.ac.uir.synthesedemo.dao.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private final UsersRepository usersRepository;

    public CustomUserDetailsService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users user = usersRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("Utilisateur non trouvé avec l'email : " + email);
        }
        // Conversion du rôle (0 -> DEVELOPPEUR, 1 -> CHEF_PROJET)
        String role = (user.getRole() == 1) ? "CHEF_PROJET" : "DEVELOPPEUR";

        return User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(role) // Rôle mappé
                .build();
    }
}
