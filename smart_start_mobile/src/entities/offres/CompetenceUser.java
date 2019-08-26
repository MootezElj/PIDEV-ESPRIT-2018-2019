package entities.offres;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class CompetenceUser {

    @Id
    @GeneratedValue
    private int idCompetence;
    private String libelle;

    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinTable(name = "competence_user",joinColumns = @JoinColumn(name = "idCompetence"),inverseJoinColumns = @JoinColumn(name = "idUser"))
    private List<User> userList;

//    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
//    @JoinTable(name = "competence_emplois",joinColumns = @JoinColumn(name = "idCompetence"),inverseJoinColumns = @JoinColumn(name = "idAnnEmp"))
//    private List<AnnonceEmplois> annonceEmploisList;

    public CompetenceUser() {
    }

    public int getId() {
        return idCompetence;
    }

    public void setId(int idCompetence) {
        this.idCompetence = idCompetence;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
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
}

