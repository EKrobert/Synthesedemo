package ma.ac.uir.synthesedemo.service;

import ma.ac.uir.synthesedemo.dao.UsersRepository;
import ma.ac.uir.synthesedemo.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsersRepository userRepository; // Assurez-vous d'avoir un repository pour votre entité Users

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), getAuthorities(user));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Users user) {
        // Assurez-vous que les rôles sont correctement attribués
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + (user.getRole() == 0 ? "DEVELOPER" : "PROJECT_MANAGER"));
        return Collections.singletonList(authority);
    }
}


/*import ma.ac.uir.synthesedemo.entity.Users;
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
}*/
