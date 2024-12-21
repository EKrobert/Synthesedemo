package ma.ac.uir.synthesedemo.controller;

import jakarta.servlet.http.HttpSession;
import ma.ac.uir.synthesedemo.entity.Competences;
import ma.ac.uir.synthesedemo.entity.Users;
import ma.ac.uir.synthesedemo.service.CompetenceService;
import ma.ac.uir.synthesedemo.service.UserService;
import ma.ac.uir.synthesedemo.utils.SessionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class ProfilController {
    private final UserService userService;
    private final CompetenceService competenceService;

    public ProfilController(UserService userService, CompetenceService competenceService) {
        this.userService = userService;
        this.competenceService = competenceService;
    }

    @GetMapping("/profile")
    public String profile(HttpSession session, Model model) {
        Users loggedInUser = SessionUtils.getLoggedInUser(session);
        if (loggedInUser == null) {
            return "redirect:/login";
        }
        // Recharger l'utilisateur depuis la base de données
        Users reloadedUser = userService.findById((int) loggedInUser.getId());
        model.addAttribute("user", reloadedUser);
        List<Competences> competences = competenceService.findAll();
        model.addAttribute("competences", competences);
        return "profile";
    }


    @PostMapping("/profile/update")
    public String profile(@ModelAttribute("user") Users user,
                          @RequestParam(value = "competences[]", required = false) List<Integer> competencesIds,
                          @RequestParam("name") String name,
                          @RequestParam("prenom") String prenom,
                          RedirectAttributes redirectAttributes,
                          HttpSession session,
                          Model model) {

        Users loggedInUser = SessionUtils.getLoggedInUser(session);
        if (loggedInUser == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", loggedInUser);
        try {
            // Récupérer l'utilisateur depuis la base de données
            Users existingUser = userService.findById((int) loggedInUser.getId());

            // Vérifier si l'utilisateur existe
            if (existingUser == null) {
                // Si l'utilisateur n'existe pas, ajouter un message d'erreur et rediriger
                redirectAttributes.addFlashAttribute("error", "Utilisateur non trouvé.");
                return "redirect:/profile";
            }

            // Si aucune compétence n'est sélectionnée, on vide la relation Many-to-Many
            if (competencesIds == null || competencesIds.isEmpty()) {
                existingUser.getCompetences().clear();  // Retirer toutes les compétences de l'utilisateur
            } else {
                // Sinon, on met à jour les compétences
                Set<Competences> competences = new HashSet<>();
                for (Integer id : competencesIds) {
                    Competences competence = competenceService.findById(id);
                    if (competence != null) {
                        competences.add(competence);
                    }
                }
                existingUser.setCompetences(competences);
            }

            // Mettre à jour les autres informations de l'utilisateur (nom, prénom, etc.)
            existingUser.setName(name);
            existingUser.setPrenom(prenom);
            existingUser.setDisponible(user.isDisponible());  // Assurez-vous que la propriété 'disponible' existe

            // Sauvegarder les modifications dans la base de données
            userService.update(existingUser);

            // Ajouter un message de succès
            redirectAttributes.addFlashAttribute("message", "Profil mis à jour avec succès !");
        } catch (Exception e) {
            // En cas d'erreur, ajouter un message d'erreur
            redirectAttributes.addFlashAttribute("error", "Une erreur est survenue lors de la mise à jour du profil.");
        }

        // Rediriger vers la page de profil après la mise à jour
        return "redirect:/profile";
    }


}
