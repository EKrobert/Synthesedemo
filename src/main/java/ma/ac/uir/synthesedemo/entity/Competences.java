package ma.ac.uir.synthesedemo.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "competences")
public class Competences {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "nom")
    private String competence;

    @ManyToMany(mappedBy = "competences")
    private Set<Users> users;

    // Relation Many-to-Many avec l'entité Projets
    @ManyToMany(mappedBy = "competences") // mappedBy indique que la relation est gérée par l'entité Projets
    private Set<Projets> projets;

    public Competences() {}

    public Competences(String competence) {
        this.competence = competence;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompetence() {
        return competence;
    }

    public void setCompetence(String competence) {
        this.competence = competence;
    }

    public Set<Projets> getProjets() {
        return projets;
    }
    public void setProjets(Set<Projets> projets) {
        this.projets = projets;
    }

}
