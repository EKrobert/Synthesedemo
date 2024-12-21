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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/competences")
public class CompetenceController {

    private final UserService userService;
    private CompetenceService competenceService;

    @Autowired
    public CompetenceController(CompetenceService competenceService, UserService userService) {
        this.competenceService = competenceService;
        this.userService = userService;
    }

    @ModelAttribute("loggedInUser")
    public Users addLoggedInUserToModel(HttpSession session) {
        Users loggedInUser = SessionUtils.getLoggedInUser(session);
        if (loggedInUser != null) {
            return userService.findById((int) loggedInUser.getId());
        }
        return null;
    }

    @GetMapping("/index")
    public String index(@ModelAttribute("loggedInUser") Users loggedInUser,Model model) {
        if (loggedInUser == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", loggedInUser);
        List<Competences> competences = competenceService.findAll();
        model.addAttribute("competences", competences);
        return "/competences/index";
    }

    @GetMapping("/create")
    public String creat(@ModelAttribute("loggedInUser") Users loggedInUser,Model model) {
        if (loggedInUser == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", loggedInUser);
        return "/competences/create-competence";
    }

    @PostMapping("/store")
    public String create(@ModelAttribute("loggedInUser") Users loggedInUser,
                         @RequestParam("competence") String competence,
                         Model model) {
        if (loggedInUser == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", loggedInUser);
        System.out.println("Compétence reçue : " + competence);
        Competences competences = new Competences(competence);
        competenceService.save(competences);
        return "/competences/create-competence";
    }

}
