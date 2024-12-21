package ma.ac.uir.synthesedemo.controller;

import jakarta.servlet.http.HttpSession;
import ma.ac.uir.synthesedemo.entity.Competences;
import ma.ac.uir.synthesedemo.entity.Users;
import ma.ac.uir.synthesedemo.service.CompetenceService;
import ma.ac.uir.synthesedemo.service.UserService;
import ma.ac.uir.synthesedemo.utils.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class UserController {


    @Autowired
    private UserService userService;
    @Autowired
    private CompetenceService competenceService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/home")
    public String home(HttpSession session, Model model) {
        // Récupérer l'utilisateur connecté
        Users loggedInUser = SessionUtils.getLoggedInUser(session);

        // Vérifier si l'utilisateur est connecté
        if (loggedInUser == null) {
            return "redirect:/login"; // Rediriger vers la page de connexion si non connecté
        }

        // Ajouter l'utilisateur au modèle pour la vue
        model.addAttribute("user", loggedInUser);
        return "home";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new Users());
        return "register"; // Retourne la vue pour l'inscription
    }

}

