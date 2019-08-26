package controllers.utilisateurs;

import entities.utilisateurs.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import services.utilisateurs.UserService;
import utils.UserSession;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

public class EditBasicInformationsController implements Initializable {

    @FXML
    Label lblCtrUsername,lblCtrNom,lblCtrPrenom,lblUsername;
    @FXML
    TextField txtUsername,txtNom,txtPrenom;
    @FXML
    TextArea txtBio;
    @FXML
    DatePicker txtDateNaiss;

    User currentUser=null;
    UserService userService=null;

    public EditBasicInformationsController() {
        userService=new UserService();
        currentUser= UserSession.getInstace(new User()).getUser();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lblUsername.setText(currentUser.getUsername());
        txtUsername.setText(currentUser.getUsername());
        txtNom.setText(currentUser.getFirstName());
        txtPrenom.setText(currentUser.getLastName());
        txtDateNaiss.setValue(LocalDate.parse( new SimpleDateFormat("yyyy-MM-dd").format(currentUser.getDateNaissance()) ));
        //txtBio.setText(currentUser.getBio());
    }

    @FXML
    public void handleBtnMonProfil(MouseEvent event){
        if (currentUser.getRoleJv().equals("Freelance")){
            loadPage(event,"/views/utilisateurs/ProfileFreelancer.fxml");
        }
        else
            loadPage(event, "/views/utilisateurs/ProfileClient.fxml");
    }
    @FXML
    public void handleBtnLogout(MouseEvent event){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        //alert.setTitle("Deconnexion");
        alert.setHeaderText("Deconnexion");
        alert.setContentText("Vous allez se deconnecter, etes vous sure ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            UserSession.getInstace(currentUser).cleanUserSession();
            loadPage(event,"/views/utilisateurs/Authentification.fxml");
        }
    }
    @FXML
    public void handleBtnRetour(MouseEvent event){
        if (currentUser.getRoleJv().equals("Freelance"))
            loadPage(event, "/views/utilisateurs/ProfileFreelancer.fxml");
        else
            loadPage(event, "/views/utilisateurs/ProfileClient.fxml");
    }
    @FXML
    public void handleBtnEnregistrer(MouseEvent event) throws SQLException, ParseException {
        boolean resultUsername=isValidUsername(txtUsername.getText());
        boolean resultNom=isValidName(txtNom.getText());
        boolean resultPrenom=isValidPname(txtPrenom.getText());
        if(resultUsername && resultNom && resultPrenom){
            lblCtrUsername.setText("");
            currentUser.setUsername(txtUsername.getText());
            currentUser.setFirstName(txtNom.getText());
            currentUser.setLastName(txtPrenom.getText());
            currentUser.setDateNaissance(new SimpleDateFormat("yyyy-MM-dd").parse(txtDateNaiss.getValue().toString()));
            currentUser.setCompetance(txtBio.getText());
            userService.updateUserProfile(currentUser);
            if (currentUser.getRoleJv().equals("Freelance"))
                loadPage(event, "/views/ProfileFreelancer.fxml");
            else
                loadPage(event, "/views/ProfileClient.fxml");

        }


    }

    private boolean isValidUsername(String username) throws SQLException {
        if(txtUsername.getText().equals(currentUser.getUsername())){
            lblCtrUsername.setText("");
            return true;
        }
        else if (!(username.length()>6 && username.matches("[a-zA-Z][a-zA-Z0-9]+"))){
            lblCtrUsername.setText("le nom d'utilisateur doit contenir au moins 6 lettres, ne doit pas contenir de caractères spéciaux. ");
            return false;
        }

        else if (! userService.usernameIsAvailable(txtUsername.getText())){
            lblCtrUsername.setText("Le nom d'utilisateur existe deja");
            return false;
        }
        else{
            lblCtrUsername.setText("");
            return true;

        }
    }

    private boolean isValidName( String name){
        if (name.matches("[a-zA-Z]+")){
            lblCtrNom.setText("");
            return true;
        }
        else {
            lblCtrNom.setText("Le nom doit contenir seulement des caracteres");
            return false;
        }
    }

    private boolean isValidPname( String name){
        if (name.matches("[a-zA-Z]+")){
            lblCtrPrenom.setText("");
            return true;
        }
        else {
            lblCtrPrenom.setText("Le nom doit contenir seulement des caracteres");
            return false;
        }
    }

    private void loadPage(MouseEvent event, String pageName){
        try {
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            Scene scene = new Scene((Parent) FXMLLoader.load(getClass().getResource(pageName)));
            stage.setScene(scene);
            stage.show();

        } catch (    IOException ex) {
            System.err.println(ex.getMessage());
        }
    }
}
