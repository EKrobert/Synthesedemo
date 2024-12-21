package ma.ac.uir.synthesedemo.controller;

import jakarta.servlet.http.HttpSession;
import ma.ac.uir.synthesedemo.dao.UsersRepository;
import ma.ac.uir.synthesedemo.entity.Users;
import ma.ac.uir.synthesedemo.service.UserService;
import ma.ac.uir.synthesedemo.utils.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class AuthController {


    @Autowired
    private UserService userService;
    public AuthController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/login")
    public String login() {
        return "login"; // Retourne la vue personnalisée pour le login
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new Users());
        return "register"; // Retourne la vue pour l'inscription
    }

    @PostMapping("/save")
    public String register(@ModelAttribute("user") Users user, BindingResult bindingResult, Model model) {
        // Vérifier si l'email existe déjà
        if (userService.findByEMail(user.getEmail()) != null) {
            model.addAttribute("error", "Cet email est déjà utilisé.");
            return "redirect:/auth/register"; // Redirige vers la page d'inscription avec un message d'erreur
        }

        // Vérification des erreurs de validation
        if (bindingResult.hasErrors()) {
            return "register"; // Retourne la vue du formulaire avec les erreurs
        }

        // Hacher le mot de passe et sauvegarder l'utilisateur
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userService.save(user);

        return "redirect:/auth/login"; // Redirige vers la page de login après l'inscription
    }


    @PostMapping("/authentication")
    public String authentication(@ModelAttribute("user") Users user, Model model, HttpSession session) {
        // Rechercher l'utilisateur par email
        Users us = userService.findByEMail(user.getEmail());

        // Vérification des identifiants
        if (us == null || !BCrypt.checkpw(user.getPassword(), us.getPassword())) {
            model.addAttribute("error", "Email ou mot de passe incorrect.");
            return "login";
        }

        // Initialiser la session avec l'utilisateur connecté
        SessionUtils.setLoggedInUser(session, us);
        //System.out.println(us);
        return "redirect:/home";
    }


    @GetMapping("/logout")
    public String logout(HttpSession session) {
        SessionUtils.logout(session);
        return "redirect:/login";
    }


}


/*@PostMapping("/authentication")
public String login(@RequestParam("username") String username,
                    @RequestParam("password") String password,
                    Model model) {
    // Vérification des champs
    if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
        model.addAttribute("error", "Username ou mot de passe manquant");
        return "redirect:/home"; // Retourne à la page de login avec un message d'erreur
    }

    try {
        // Créer un token d'authentification
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, password);

        // Utiliser l'AuthenticationManager pour authentifier l'utilisateur
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        // Ajouter l'authentification au SecurityContext
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Rediriger vers la page d'accueil
        return "home"; // Redirige vers la page d'accueil après la connexion réussie
    } catch (BadCredentialsException e) {
        // Authentification échouée
        model.addAttribute("error", "Username ou mot de passe incorrect");
        return "login"; // Retourne à la page de login avec un message d'erreur
    }
}*/