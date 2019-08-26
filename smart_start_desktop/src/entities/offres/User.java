package entities.offres;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name = "fos_user")
public class User {

    //FOSuser attributes from baseuser
    @Id
    @GeneratedValue
    private int id;
    private String username;
    private String usernameCanonical;
    private String email;
    private String emailCanonical;
    private boolean enabled;
    private String salt;
    private String password;
    @Transient
    private String plainPassword;
    @Temporal(TemporalType.DATE)
    private Date lastLogin;
    private String confirmationToken;
    @Temporal(TemporalType.DATE)
    private Date passwordRequestedAt;
    private String roleJv;

    //our user attributes
    private String firstName;
    private float solde;
    private String competance;
    private String lastName;
    private boolean valide;
    private boolean signale;
    @Temporal(TemporalType.DATE)
    private Date dateNaissance;

    /**
    //Liste des langues parlees par l'utilisateur
    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinTable(name = "langue_user",joinColumns = @JoinColumn(name = "idUser"),inverseJoinColumns = @JoinColumn(name = "idLangue"))
    private List<Langue> languesList;


    //Liste des competences du freelance
    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinTable(name = "competence_user",joinColumns = @JoinColumn(name = "idUser"),inverseJoinColumns = @JoinColumn(name = "idCompetence"))
    private List<Competence> competencesList;

    //Liste des annonces d'emplois faites par l'embaucheur
    @OneToMany(mappedBy = "user",cascade=CascadeType.ALL)
    private List<AnnonceEmplois> annonceEmploisList;

    //Liste des postulation aux annonces d'emplois faites par le freelance
    @OneToMany(mappedBy = "freelance",cascade=CascadeType.ALL)
    private List<Postulation> postulationList;

    //Liste des evaluations du freelance
    @OneToMany(mappedBy = "freelance",cascade=CascadeType.ALL)
    private List<Evaluation> evaluationFreelancer;

    //Liste des evaluations faites par l'embaucheur
    @OneToMany(mappedBy = "Embaucheur",cascade=CascadeType.ALL)
    private List<Evaluation> evaluationEmbaucheur;
    ***/
    
    
    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsernameCanonical() {
        return usernameCanonical;
    }

    public void setUsernameCanonical(String usernameCanonical) {
        this.usernameCanonical = usernameCanonical;
    }

    public String getEmail() {
        return email;
    }

    
    
    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmailCanonical() {
        return emailCanonical;
    }

    public void setEmailCanonical(String emailCanonical) {
        this.emailCanonical = emailCanonical;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPlainPassword() {
        return plainPassword;
    }

    public void setPlainPassword(String plainPassword) {
        this.plainPassword = plainPassword;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getConfirmationToken() {
        return confirmationToken;
    }

    public void setConfirmationToken(String confirmationToken) {
        this.confirmationToken = confirmationToken;
    }

    public Date getPasswordRequestedAt() {
        return passwordRequestedAt;
    }

    public void setPasswordRequestedAt(Date passwordRequestedAt) {
        this.passwordRequestedAt = passwordRequestedAt;
    }

    public String getRoleJv() {
        return roleJv;
    }

    public void setRoleJv(String roleJv) {
        this.roleJv = roleJv;
    }

    public String getFirstName() {
        return firstName;
    }

    public float getSolde() {
        return solde;
    }

    public String getCompetance() {
        return competance;
    }

    public String getLastName() {
        return lastName;
    }

    public boolean isValide() {
        return valide;
    }

    public boolean isSignale() {
        return signale;
    }

   

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setSolde(float solde) {
        this.solde = solde;
    }

    public void setCompetance(String competance) {
        this.competance = competance;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setValide(boolean valide) {
        this.valide = valide;
    }

    public void setSignale(boolean signale) {
        this.signale = signale;
    }

    public User(String username, String usernameCanonical, String email, String emailCanonical, boolean enabled, String salt, String password,Date lastLogin, String confirmationToken, Date passwordRequestedAt, String role, String firstName, float solde, String lastName, Date dateNaissance) {
        this.username = username;
        this.usernameCanonical = usernameCanonical;
        this.email = email;
        this.emailCanonical = emailCanonical;
        this.enabled = enabled;
        this.salt = salt;
        this.password = password;
        this.lastLogin = lastLogin;
        this.confirmationToken = confirmationToken;
        this.passwordRequestedAt = passwordRequestedAt;
        this.roleJv = role;
        this.firstName = firstName;
        this.solde = solde;
        this.lastName = lastName;
         this.dateNaissance = dateNaissance;
    }

	public User(int id) {
		super();
		this.id = id;
	}

   
}
