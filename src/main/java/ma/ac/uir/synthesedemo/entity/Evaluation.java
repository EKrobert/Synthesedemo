package ma.ac.uir.synthesedemo.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "evaluation")
public class Evaluation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "score")
    private int score;
    @Column(name = "texte")
    private String texte;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // Clé étrangère vers la table Users
    private Users user;
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Projets project;


    public Evaluation(int score, String texte) {
        this.score = score;
        this.texte = texte;
    }

    public Evaluation() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getTexte() {
        return texte;
    }

    public void setTexte(String texte) {
        this.texte = texte;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Projets getProject() {
        return project;
    }

    public void setProject(Projets project) {
        this.project = project;
    }
}
