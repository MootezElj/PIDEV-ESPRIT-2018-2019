package controllers.offres.user;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import entities.offres.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import utils.offre.UserSession;

public class DashboardController implements Initializable {

    User currentUser=null;

    @FXML
    Label lblUsername;

    public DashboardController() {
        currentUser= UserSession.getInstace(new User()).getUser();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lblUsername.setText(currentUser.getUsername());
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
            loadPage(event,"/views/Authentification.fxml");
        }
    }

    @FXML
    public void handleBtnMonProfil(MouseEvent event){
        if (currentUser.getRoleJv().equals("Freelance")){
            loadPage(event,"/views/ProfileFreelancer.fxml");
        }
        else
            loadPage(event, "/views/ProfileEmbaucheur.fxml");
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
