package entities.utilisateurs;

import javax.persistence.*;
import java.util.List;

@Entity
public class CompetenceUser {

    @Id
    @GeneratedValue
    private int competence_userId;

    private String libelle;

   private User idUser;
//    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
//    @JoinTable(name = "competence_emplois",joinColumns = @JoinColumn(name = "idCompetence"),inverseJoinColumns = @JoinColumn(name = "idAnnEmp"))
//    private List<AnnonceEmplois> annonceEmploisList;

    public CompetenceUser() {
    }

    public int getId() {
        return competence_userId;
    }

    public void setId(int idCompetence) {
        this.competence_userId = idCompetence;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public void setCompetence_userId(int competence_userId) {
        this.competence_userId = competence_userId;
    }

    public void setIdUser(User idUser) {
        this.idUser = idUser;
    }

    

//    public List<AnnonceEmplois> getAnnonceEmploisList() {
//        return annonceEmploisList;
//    }
//
//    public void setAnnonceEmploisList(List<AnnonceEmplois> annonceEmploisList) {
//        this.annonceEmploisList = annonceEmploisList;
//    }

   /* public int getId() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setId(int aInt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }*/

    public int getCompetence_userId() {
        return competence_userId;
    }

    public User getIdUser() {
        return idUser;
    }
}
