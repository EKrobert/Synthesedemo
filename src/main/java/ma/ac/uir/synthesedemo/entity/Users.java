package ma.ac.uir.synthesedemo.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "nom")
    private String name;
    @Column(name = "prenom")
    private String prenom;
    @Column(name = "email", unique = true)
    private String email;
    @Column(name = "role")
    private int role;//Developpeur(0) ou Chef de projet(1)
    @Column(name = "password")
    private String password;
    @ManyToMany(mappedBy = "users") // mappedBy indique que la relation est gérée par l'entité Projets
    private Set <Projets> projets;
    @Column(name = "disponibilite")
    private boolean disponible = false; // Par défaut indisponible
    @ManyToMany
    @JoinTable(
            name = "users_competences",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "competence_id")
    )
    private Set<Competences> competences;
    @OneToMany(mappedBy = "createdBy")
    private Set<Projets> createdProjects; // Projets créés par cet utilisateur
    @OneToMany(mappedBy = "user")
    private Set<Evaluation> evaluations;


    public Users() {}

    public Users(int role, String email, String prenom, String name, String username, String password, boolean disponible) {
        this.role = role;
        this.email = email;
        this.prenom = prenom;
        this.name = name;
        this.password = password;
        this.disponible = disponible;
    }

    public Set<Competences> getCompetences() {
        return competences;
    }

    public void setCompetences(Set<Competences> competences) {
        this.competences = competences;
    }

    public Set<Projets> getProjets() {
        return projets;
    }

    public void setProjets(Set<Projets> projets) {
        this.projets = projets;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public Set<Projets> getCreatedProjects() {
        return createdProjects;
    }

    public void setCreatedProjects(Set<Projets> createdProjects) {
        this.createdProjects = createdProjects;
    }

    public Set<Evaluation> getEvaluations() {
        return evaluations;
    }

    public void setEvaluations(Set<Evaluation> evaluations) {
        this.evaluations = evaluations;
    }
}
