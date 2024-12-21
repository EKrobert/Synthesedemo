package ma.ac.uir.synthesedemo.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "projects")
public class Projets {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "title")
    private String titre;
    @Column(name = "description")
    private String description;
    @Column(name = "duree")
    private int duree;
    @ManyToMany
    @JoinTable(
            name = "projets_competences", // Nom de la table de jointure
            joinColumns = @JoinColumn(name = "projet_id"), // Clé étrangère vers la table projets
            inverseJoinColumns = @JoinColumn(name = "competence_id") // Clé étrangère vers la table competences
    )
    private Set<Competences> competences;
    @ManyToMany
    @JoinTable(
            name = "projets_users", // Nom de la table de jointure
            joinColumns = @JoinColumn(name = "projet_id"), // Clé étrangère vers la table projets
            inverseJoinColumns = @JoinColumn(name = "user_id") // Clé étrangère vers la table users
    )
    private Set <Users> users;
    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false) // Clé étrangère vers Users
    private Users createdBy;
    @OneToMany(mappedBy = "project")
    private Set<Evaluation> evaluations ;



    public Set<Users> getUsers() {
        return users;
    }

    public void setUsers(Set<Users> users) {
        this.users = users;
    }

    public Projets() {}
    public Projets(String titre, String description) {
        this.titre = titre;
        this.description = description;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTitre() {
        return titre;
    }
    public void setTitre(String titre) {
        this.titre = titre;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Set<Competences> getCompetences() {
        return competences;
    }
    public void setCompetences(Set<Competences> competences) {
        this.competences = competences;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public Users getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Users createdBy) {
        this.createdBy = createdBy;
    }

    public Set<Evaluation> getEvaluations() {
        return evaluations;
    }

    public void setEvaluations(Set<Evaluation> evaluations) {
        this.evaluations = evaluations;
    }
}
