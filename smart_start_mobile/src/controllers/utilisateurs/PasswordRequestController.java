package controllers.utilisateurs;

import entities.utilisateurs.User;
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
import services.utilisateurs.UserService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class PasswordRequestController implements Initializable {

    @FXML
    TextField txtEmail;
    @FXML
    TextField txtPassword;
    @FXML
    TextField txtPasswordConf;
    @FXML
    TextField txtConfirmaCompte;
    @FXML
    Label lblCtrUsername,lblCtrEmail,lblCtrNom,lblCtrPrenom,lblCtrPasswd,lblCtrPasswd2,lblVerifCpt;
    @FXML
    Pane pane1,pane2,pane3,pane4;

    private User user=new User();
    private UserService userService = new UserService();

   

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void handleBtnAcceuil(MouseEvent event){
        loadPage(event,"/views/utilisateurs/Authentification.fxml");
    }

    @FXML
    public void handleBtnValiderEmail(MouseEvent event) throws SQLException {
        String email=txtEmail.getText();
        System.out.println("controllers.PasswordRequestController.handleBtnValiderEmail()"+email);
        if (isValidemail(email)){
             //System.out.println("controllers.PasswordRequestController.handleBtnValiderEmail()"+user.set);
            user=userService.sendPasswordRequestConfirmation(email);
            if(user.getUsername() !=null ) pane2.toFront();
            else lblCtrEmail.setText("Cette adresse email n'appartient a aucun utilisateur");
        }
    }

    @FXML
    public void handleBtnVerifierCompte(MouseEvent event) throws SQLException {

        if (txtConfirmaCompte.getText().equals(user.getConfirmationToken())){
            lblVerifCpt.setText("");
            pane3.toFront();
        }
        else{
            lblVerifCpt.setText("Le code est incorrect");
        }

    }

    @FXML
    public void handleBtnRetour(MouseEvent event){pane1.toFront();}

    @FXML
    public void handleBtnEditPassword(MouseEvent event) throws SQLException {
        if(isValidPassword(txtPassword.getText(),txtPasswordConf.getText())){
            user.setPlainPassword(txtPassword.getText());
            userService.editUserPassword(user);
            pane4.toFront();
        }
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
}
