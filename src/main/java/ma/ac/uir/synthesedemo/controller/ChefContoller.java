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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/chef")
public class ChefContoller {
    @Autowired
    private final UserService userService;
    @Autowired
    private CompetenceService competenceService;

    public ChefContoller(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/dev-details/")
    public String devDetails(@RequestParam("id") int id, HttpSession session, Model model) {
        Users loggedInUser = SessionUtils.getLoggedInUser(session);
        if (loggedInUser == null) {
            return "redirect:/login";
        }

        // Recharger l'utilisateur depuis la base de données
        Users reloadedUser = userService.findById((int) loggedInUser.getId());

        Users userDetails = userService.findById(id);
        if (userDetails == null) {
            return "redirect:/devlist"; // Si l'utilisateur n'existe pas, rediriger vers la liste des développeurs
        }

        model.addAttribute("user", reloadedUser);
        model.addAttribute("userDetails", userDetails); // Ajouter les détails de l'utilisateur
        return "show-dev"; // Retourner la vue des détails de l'utilisateur
    }

    @GetMapping("/devlist")
    public String devlist(HttpSession session, Model model) {
        Users loggedInUser = SessionUtils.getLoggedInUser(session);
        if (loggedInUser == null) {
            return "redirect:/login";
        }
        // Recharger l'utilisateur depuis la base de données
        Users reloadedUser = userService.findById((int) loggedInUser.getId());
        List<Users> users = userService.findAll().stream()
                .filter(user -> user.getRole() == 0) // Inclure uniquement les développeurs (rôle 0)
                .collect(Collectors.toList());
        List<Competences> competences = competenceService.findAll();
        model.addAttribute("user", reloadedUser);
        model.addAttribute("users", users);
        return "devlist";
    }

}
