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
import org.controlsfx.control.Rating;

import services.utilisateurs.UserService;
import utils.UserSession;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class ProfileFreelancerController implements Initializable {

    @FXML
    Label lblName,lblEmail,lblUsername,lblRating,lblDateNaiss,lblProfileProgress,lblMemberSince;
    @FXML
    Rating ratingStars;
    @FXML
    TextArea txtBio;
    @FXML
    Button btnEditBasicInfo;
    @FXML
    ProgressBar profileCompletion;

    User currentUser=null;
     UserService userService=null;
    public ProfileFreelancerController() {

        currentUser= UserSession.getInstace(new User()).getUser();
//        evaluationService=new EvaluationService();
        userService=new UserService();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lblName.setText(currentUser.getFirstName()+" "+currentUser.getLastName());
        lblEmail.setText(currentUser.getEmail());
        lblUsername.setText(currentUser.getUsername());
        lblMemberSince.setText("Membre depuis "+currentUser.getPasswordRequestedAt());
        lblDateNaiss.setText("Né le "+currentUser.getDateNaissance());
        txtBio.setText(currentUser.getCompetance());
        double profileProgress=userService.userProfileCompletion(currentUser);
        lblProfileProgress.setText("Profil "+profileProgress+" % complet");
        profileCompletion.setProgress(profileProgress/100);

     
    }
    @FXML
    public void handleBtnEditBasicInfo(MouseEvent event){
        loadPage(event,"/views/utilisateurs/EditBasicInformations.fxml");
    }
    @FXML
    public void handleBtnShowCompetences(MouseEvent event){
        loadPage(event,"/views/utilisateurs/Competences.fxml");
    }
    @FXML
    public void handleBtnShowLangues(MouseEvent event){
        loadPage(event,"/views/utilisateurs/Langues.fxml");
    }
    @FXML
    public void handleBtnLogout(MouseEvent event){
        System.out.println("this works");
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
