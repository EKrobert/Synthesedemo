package ma.ac.uir.synthesedemo.controller;

import jakarta.servlet.http.HttpSession;
import ma.ac.uir.synthesedemo.entity.Evaluation;
import ma.ac.uir.synthesedemo.entity.Projets;
import ma.ac.uir.synthesedemo.entity.Users;
import ma.ac.uir.synthesedemo.service.CompetenceService;
import ma.ac.uir.synthesedemo.service.EvaluationService;
import ma.ac.uir.synthesedemo.service.ProjetService;
import ma.ac.uir.synthesedemo.service.UserService;
import ma.ac.uir.synthesedemo.utils.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/dev")
public class DeveloppeurController {

    private final ProjetService projetService;
    @Autowired
    private final EvaluationService evaluationService;

    @ModelAttribute("loggedInUser")
    public Users addLoggedInUserToModel(HttpSession session) {
        Users loggedInUser = SessionUtils.getLoggedInUser(session);
        if (loggedInUser != null) {
            return userService.findById((int) loggedInUser.getId());
        }
        return null;
    }

    private final UserService userService;
    private CompetenceService competenceService;

    @Autowired
    public DeveloppeurController(CompetenceService competenceService, UserService userService, ProjetService projetService, EvaluationService evaluationService) {
        this.competenceService = competenceService;
        this.userService = userService;
        this.projetService = projetService;
        this.evaluationService = evaluationService;
    }

    @GetMapping("/projects/list")
    public String index(@ModelAttribute("loggedInUser") Users loggedInUser, Model model) {
        if (loggedInUser == null) {
            return "redirect:/login";
        }

        Set<Projets> projets = loggedInUser.getProjets();

        // Ajouter les projets et l'utilisateur au modèle
        model.addAttribute("projets", projets);
        model.addAttribute("user", loggedInUser);

        return "/dev/p-list";
    }

    @GetMapping("/projects/details/")
    public String details(@RequestParam("id") Long id,
                          @ModelAttribute("loggedInUser") Users loggedInUser,
                          Model model) {
        if (loggedInUser == null) {
            return "redirect:/login";
        }

        // Récupérer les détails du projet par son ID
        Projets projet = projetService.findById(id);
        Users users = loggedInUser;
        Evaluation evaluation = null;
        evaluation = evaluationService.findByUserIdAndProjectId(users.getId(),id);
        if (projet == null) {
            return "redirect:/projects"; // Rediriger si le projet n'existe pas
        }
        model.addAttribute("projet", projet);
        model.addAttribute("user", loggedInUser);
        model.addAttribute("evaluation", evaluation);

        return "dev/p-details";
    }



}
