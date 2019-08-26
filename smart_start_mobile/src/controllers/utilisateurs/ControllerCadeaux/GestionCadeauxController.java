/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.utilisateurs.ControllerCadeaux;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author malek
 */
public class GestionCadeauxController  implements Initializable { 
    
         @FXML
    AnchorPane dd;
         
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
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
    public void goToAffecter(MouseEvent event) throws SQLException, IOException {
        AnchorPane p =FXMLLoader.load(getClass().getResource("/views/utilisateurs/AffecterCadeaux.fxml"));
     dd.getChildren().setAll(p);
    
    }
    
      @FXML
    void GoToCategories(MouseEvent event) throws SQLException, IOException {
     AnchorPane p =FXMLLoader.load(getClass().getResource("/views/utilisateurs/AjouterCategorie.fxml"));
     dd.getChildren().setAll(p);
    
    }
    
      @FXML
    void goToCadeaux(MouseEvent event) throws SQLException, IOException {
     AnchorPane p =FXMLLoader.load(getClass().getResource("/views/utilisateurs/AjouterCadeaux.fxml"));
     dd.getChildren().setAll(p);
    
    }
    
    
    
}
