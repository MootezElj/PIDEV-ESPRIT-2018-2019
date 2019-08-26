package controllers.utilisateurs;

import entities.utilisateurs.User;
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
import utils.UserSession;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

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
            loadPage(event,"/views/utilisateurs/Authentification.fxml");
        }
    }

    @FXML
    public void handleBtnMonProfil(MouseEvent event){
        if (currentUser.getRoleJv().equals("Freelancer")){
            loadPage(event,"/views/utilisateurs/ProfileFreelancer.fxml");
        }
        else
            loadPage(event, "/views/utilisateurs/ProfileClient.fxml");
    }  
  @FXML
    public void handleBtnOutils(MouseEvent event){
       
            loadPage(event, "/views/utilisateurs/outildetail.fxml");
    } 
    
    private void loadPage(MouseEvent event, String pageName){
        try {
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            Scene scene = new Scene((Parent) FXMLLoader.load(getClass().getResource(pageName)));
            stage.setScene(scene);
            System.out.println("gre");
            stage.show();

        } catch (    IOException ex) {
            System.err.println(ex.getMessage());
        }
    }
}
