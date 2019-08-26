package controllers.offres.user;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import entities.offres.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import services.offres.user.UserService;


public class InscriptionController implements Initializable {

    @FXML
    Button btnVerifierCompte;
    @FXML
    Button btnInscription;
    @FXML
    Button btnRetour;
    @FXML
    TextField txtEmail;
    @FXML
    TextField txtPseudo;
    @FXML
    TextField txtPassword;
    @FXML
    TextField txtPasswordConf;
    @FXML
    RadioButton radioRoleFreelc;
    @FXML
    RadioButton radioRoleEmbch;
    @FXML
    TextField txtConfirmaCompte;
    @FXML
    TextField txtFirstName;
    @FXML
    TextField txtLastName;
    @FXML
    Label lblCtrUsername,lblCtrEmail,lblCtrFirstName,lblCtrLastName,lblCtrPasswd,lblCtrPasswd2,lblVerifCpt;
    @FXML
    Pane pane1,pane2,pane3;
    @FXML
    Button btnAddInfos,btnSkip;

    private static User user;
    private UserService userService=null;

    public InscriptionController() {
        user=new User();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void handleBtnAcceuil(MouseEvent event){
        loadPage(event,"/views/Authentification.fxml");
    }

    @FXML
    public void handleBtnSkip(MouseEvent event){

        if (user.getRoleJv().equals("Freelance")) loadPage(event, "/views/helloo1.fxml");
        else loadPage(event, "/views/DashboardEmbaucheur.fxml");
    }

    @FXML
    public void handleBtnEditBasicInfo(MouseEvent event){
        loadPage(event,"/views/EditBasicInformations.fxml");
    }

    @FXML
    public void handleBtnInscription(MouseEvent event) throws SQLException {

        String username=txtPseudo.getText();
        String email=txtEmail.getText();
        String password=txtPassword.getText();
        String passwordConf=txtPasswordConf.getText();
        String firstName=txtFirstName.getText();
        String lastName=txtLastName.getText();
        String role=(radioRoleFreelc.isSelected())? "Freelance" : "Embaucheur";

        if (isValidUsername(username) && isValidemail(email) && isValidName(firstName) && isValidPname(lastName) && isValidPassword(password,passwordConf)){
            user.setUsername(username);
            user.setEmail(email);
            user.setPlainPassword(password);
            user.setRoleJv(role);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            userService=new UserService();
            switch (userService.checkForUser(user)){
                case 0: lblCtrUsername.setText("le nom d'utilisateur est deja utilisé "); break;
                case 1: lblCtrEmail.setText("email est deja utilisé"); lblCtrUsername.setText(""); break;
                case 2: pane2.toFront();
                        user.setConfirmationToken(userService.sendConfirmationMailToUser(user));
            }
        }

    }

    @FXML
    public void handleBtnVerifierCompte(MouseEvent event) throws SQLException {

        if (txtConfirmaCompte.getText().equals(user.getConfirmationToken())){
            userService.inscription(user);
            lblVerifCpt.setText("");
            pane3.toFront();
        }
        else{
            lblVerifCpt.setText("Le code est incorrect");
        }

    }

    @FXML
    public void handleBtnRetour(MouseEvent event){

        pane1.toFront();

    }

    private void loadPage(MouseEvent event,String pageName){
        try {
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            Scene scene= new Scene((Parent) FXMLLoader.load(getClass().getResource(pageName)));
            stage.setScene(scene);
            stage.show();

        } catch (    IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    private boolean isValidPassword(String pass1,String pass2){
        if (!(pass1.length()>6 && pass1.matches("[a-zA-Z0-9]+"))){
            lblCtrPasswd.setText("Mot de passe doit avoir au moins 6 caracteres, ne doit pas contenir de caractères spéciaux.");
            lblCtrPasswd2.setText("");
            return false;
        }
        else if( ! pass1.equals(pass2)){
            lblCtrPasswd.setText("");
            lblCtrPasswd2.setText("Mot de passe ne correspond pas");
            return false;
        }
        else{
            lblCtrPasswd2.setText("");
            lblCtrPasswd.setText("");
            return true;
        }
    }

    private boolean isValidUsername(String username){
        if (!(username.length()>6 && username.matches("[a-zA-Z][a-zA-Z0-9]+"))){
            lblCtrUsername.setText("le nom d'utilisateur doit contenir au moins 6 lettres, ne doit pas contenir de caractères spéciaux. ");
            return false;
        }
        else{
            lblCtrUsername.setText("");
            return true;
        }
    }

    private boolean isValidemail( String email){
        if (1==1){
            lblCtrEmail.setText("");
            return true;
        }
        else {
            lblCtrEmail.setText("email invalide");
            return false;
        }
    }

    private boolean isValidName( String name){
        if (name.matches("[a-zA-Z]+")){
            lblCtrFirstName.setText("");
            return true;
        }
        else {
            lblCtrFirstName.setText("Le nom doit contenir seulement des caracteres");
            return false;
        }
    }

    private boolean isValidPname( String name){
        if (name.matches("[a-zA-Z]+")){
            lblCtrLastName.setText("");
            return true;
        }
        else {
            lblCtrLastName.setText("Le nom doit contenir seulement des caracteres");
            return false;
        }
    }
    /*final String PERSISTENCE_UNIT_NAME = "todos";
        EntityManagerFactory factory;
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = factory.createEntityManager();
        User u=new User();
        u.setUsername("Mohamed");
        em.getTransaction().begin();
        em.persist(u);
        em.getTransaction().commit();*/



}
