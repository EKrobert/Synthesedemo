package ma.ac.uir.synthesedemo.controller;

import jakarta.servlet.http.HttpSession;
import ma.ac.uir.synthesedemo.dao.UsersRepository;
import ma.ac.uir.synthesedemo.entity.Competences;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    private EvaluationService evaluationService;

    @ModelAttribute("loggedInUser")
    public Users addLoggedInUserToModel(HttpSession session) {
        Users loggedInUser = SessionUtils.getLoggedInUser(session);
        if (loggedInUser != null) {
            return userService.findById((int) loggedInUser.getId());
        }
        return null;
    }

    @Autowired
    UsersRepository usersRepository;


    @Autowired
    private CompetenceService competenceService;
    @Autowired
    private UserService userService;
    private ProjetService projetService;
    @Autowired
    public ProjectController(ProjetService projetService) {
        this.projetService = projetService;
    }

    @GetMapping("/index")
    public String index(@ModelAttribute("loggedInUser") Users loggedInUser, Model model) {
        if (loggedInUser == null) {
            return "redirect:/login";
        }
        List<Projets> projets = projetService.findAll();
        model.addAttribute("projets", projets);
        model.addAttribute("user", loggedInUser);
        return "/projects/index";
    }

    @GetMapping("/create")
    public String create(@ModelAttribute("loggedInUser") Users loggedInUser,
                         Model model) {
        if (loggedInUser == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", loggedInUser);
        List<Competences> competences = competenceService.findAll(); // Récupérer toutes les compétences
        model.addAttribute("competences", competences);
        return "projects/create-project";
    }
    @PostMapping("/store")
    public String store(@ModelAttribute("loggedInUser") Users loggedInUser,
                        @RequestParam("titre") String titre,
                        @RequestParam("competences[]") List<Integer> competencesIds,
                        @RequestParam("description") String description,
                        @RequestParam("duree") Integer duree,
                        RedirectAttributes redirectAttributes,
                        Model model) {
        if (loggedInUser == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", loggedInUser);
        // Convertir la liste en Set
        Set<Integer> competencesSet = new HashSet<>(competencesIds);

        // Créer une instance du projet
        Projets projet = new Projets();
        projet.setTitre(titre);
        projet.setDescription(description);
        projet.setDuree(duree);
        projet.setCreatedBy(loggedInUser); // Associer le créateur

        // Récupérer les compétences une par une
        Set<Competences> competences = new HashSet<>();
        for (Integer id : competencesIds) {
            Competences competence = competenceService.findById(id);
            if (competence != null) {
                competences.add(competence);
            }
        }
        projet.setCompetences(competences);

        // Enregistrer le projet dans la base de données
        projetService.save(projet);

        System.out.println("Projet enregistré : " + projet);

        redirectAttributes.addFlashAttribute("success", "Projet créé avec succès.");
        return "redirect:/projects/index";
    }

    @GetMapping("/show")
    public String show(@ModelAttribute("loggedInUser") Users loggedInUser,
                       @RequestParam("projetId") Long projetId, Model model) {
        if (loggedInUser == null) {
            return "redirect:/login";
        }
        List <Evaluation> evaluations;
        Projets projet = projetService.findById(projetId);
        evaluations = evaluationService.findEvaluationsByProjectId(projetId);
        model.addAttribute("user", loggedInUser);
        model.addAttribute("projet", projet);
        model.addAttribute("evaluations", evaluations);

        return "projects/show";
    }


    @GetMapping("/affectation")
    public String affectation(@ModelAttribute("loggedInUser") Users loggedInUser,
                              Model model) {
        if (loggedInUser == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", loggedInUser);
        List<Projets> projects = projetService.findAll();
       // List<Users> users = userService.findAll();


        List<Users> users = userService.findAll().stream()
                .filter(user -> user.getRole() == 0) // Inclure uniquement les développeurs (rôle 0)
                .filter(Users::isDisponible) // Inclure uniquement les utilisateurs disponibles
                .collect(Collectors.toList());


        model.addAttribute("projects", projects);
        model.addAttribute("users", users);
        return "projects/users-project";
    }


    @PostMapping("/affected")
    public String saveAffectation(@ModelAttribute("loggedInUser") Users loggedInUser,
                                  @RequestParam("projetId") Long projetId,
                                  @RequestParam("dev[]") List<Long> devIds,
                                  Model model) {
        if (loggedInUser == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", loggedInUser);
        try {
            // Récupérer le projet par son ID
            Projets project = projetService.findById(projetId);
            // Récupérer les développeurs par leurs IDs
            List<Users> developers = usersRepository.findAllById(devIds);
            if (developers.isEmpty()) {
                model.addAttribute("error", "Aucun développeur sélectionné ou trouvé.");
                return "/projects/error";
            }

            // Affecter les développeurs au projet
            for (Users developer : developers) {
                project.getUsers().add(developer);
            }
            // Sauvegarder les modifications
            projetService.save(project);  // Enregistrer le projet avec les développeurs affectés
            model.addAttribute("projet", project);
            model.addAttribute("success", "Développeurs affectés avec succès au projet.");
            return "projects/show";
        } catch (Exception e) {
            model.addAttribute("error", "Une erreur s'est produite : " + e.getMessage());
            return "/projects/error";  // Page d'erreur si une exception est levée
        }
    }

}
