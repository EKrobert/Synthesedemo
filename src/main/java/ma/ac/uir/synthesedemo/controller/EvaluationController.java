package ma.ac.uir.synthesedemo.controller;

import ma.ac.uir.synthesedemo.dao.EvaluationRepository;
import ma.ac.uir.synthesedemo.dao.ProjetRepository;
import ma.ac.uir.synthesedemo.dao.UsersRepository;
import ma.ac.uir.synthesedemo.entity.Evaluation;
import ma.ac.uir.synthesedemo.entity.Projets;
import ma.ac.uir.synthesedemo.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Controller
public class EvaluationController {
    private final UsersRepository usersRepository;
    private final EvaluationRepository evaluationRepository;
    @Autowired
    private ProjetRepository projectRepository;

    public EvaluationController(UsersRepository usersRepository, EvaluationRepository evaluationRepository) {
        this.usersRepository = usersRepository;
        this.evaluationRepository = evaluationRepository;
    }

    @PostMapping("/evaluation/save")
    public String saveEvaluation(@RequestParam("userId") Long userId,
                                 @RequestParam("projectId") Long projectId,
                                 @RequestParam("score") int score,
                                 @RequestParam("texte") String texte) {
        // Récupérer l'utilisateur et le projet
        Users user = usersRepository.findById(userId).orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));
        Projets project = projectRepository.findById(projectId).orElseThrow(() -> new RuntimeException("Projet introuvable"));

        // Vérifier si une évaluation existe déjà pour cet utilisateur et ce projet
        Evaluation existingEvaluation = evaluationRepository.findByUserIdAndProjectId(user.getId(), project.getId());

        Evaluation evaluation = new Evaluation();
        if (existingEvaluation != null) {
            // Mise à jour de l'évaluation existante
            existingEvaluation.setScore(score);
            existingEvaluation.setTexte(texte);
            evaluationRepository.save(existingEvaluation);
        } else {
            evaluation.setUser(user);
            evaluation.setProject(project);
            evaluation.setScore(score);
            evaluation.setTexte(texte);
            evaluationRepository.save(evaluation);
        }

        return "redirect:/projects/show?projetId=" + projectId;
    }

}
