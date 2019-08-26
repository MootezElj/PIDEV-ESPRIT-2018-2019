/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.utilisateurs;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import entities.utilisateurs.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ResourceBundle;

import utils.SendMail;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import dao.utilisateurs.AdminDao;
import dao.utilisateurs.UserDao;
import java.sql.SQLException;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import utils.UserSession;

/**
 * FXML Controller class
 *
 * @author Bacem Aloui
 */
public class AdminDashboardController implements Initializable {

    /**
     * Initializes the controller class.
     */
     @FXML
    Button CompBtn;
      @FXML
    Button LangBtn;
      @FXML
    Button UserBtn;
    @FXML
    Button Logout;
      @FXML
    AnchorPane dd;
     
     
     
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }   
   
    @FXML
    public void Competance(MouseEvent event) throws SQLException, IOException {
     AnchorPane p =FXMLLoader.load(getClass().getResource("/views/Competance.fxml"));
     dd.getChildren().setAll(p);
        
    }
    

    public void User(MouseEvent event) throws SQLException, IOException{
     AnchorPane p =FXMLLoader.load(getClass().getResource("/views/ModifUser.fxml"));
     dd.getChildren().setAll(p);
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
    
    @FXML
    public void handleBtnLogout(MouseEvent event){
        
        System.out.println("this works");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        //alert.setTitle("Deconnexion");
        alert.setHeaderText("Deconnexion");
        alert.setContentText("Vous allez se deconnecter, etes vous sure ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            UserSession.getInstace(new User()).cleanUserSession();
            loadPage(event,"/views/Authentification.fxml");
        }
    }
    
    
    public void GestionCadeaux(MouseEvent event) throws SQLException, IOException {
     AnchorPane p =FXMLLoader.load(getClass().getResource("/views/GestionCadeaux.fxml"));
     dd.getChildren().setAll(p);
    }
    
    
    
    @FXML
    private void AfficherNbrPartenaire(ActionEvent event) {
        
        //Utlisateurs.setText(String.valueOf(adminService.get_Number_partenaire()));
    }

    @FXML
    private void AfficherNbrMembre(ActionEvent event) {
        
        
       // Utlisateurs.setText(String.valueOf(adminService.get_Number_Membre()));
    }
    
    @FXML
    private void GestionUtilisateur(ActionEvent event) {
        
        
       // Utlisateurs.setText(String.valueOf(adminService.get_Number_Membre()));
    }
    
}
