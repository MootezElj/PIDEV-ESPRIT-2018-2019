package controllers.offres.demande;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;


public class NavigationDemandeController implements Initializable {
	

    @FXML
    private BorderPane borderPane;


    @FXML
    private JFXButton MesDemandes;

    @FXML
    private JFXButton consulterOffreLink;
    
    @FXML
    AnchorPane navHomeAnchor;
    
    

    @FXML
    void loadMesDemandesGUI(MouseEvent event) {
    	loadGui("listeDemandes.fxml");
    }

    @FXML
    void loadOffresGUI() {
    	loadGui("listeOffres.fxml");
    }
    
    @FXML
    void loadGui(String gui) {
    	AnchorPane root = null;
    	try {
    		root = FXMLLoader.load(getClass().getResource("/views/offres/demande/"+gui));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	borderPane.setCenter(root);
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loadOffresGUI();
		
	}

    
  
	    public void handleBtnLogout(MouseEvent event){
	        System.out.println("this works");
	        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
	        //alert.setTitle("Deconnexion");
	        alert.setHeaderText("Deconnexion");
	        alert.setContentText("Vous allez se deconnecter, etes vous sure ?");
	        Optional<ButtonType> result = alert.showAndWait();
	        
	        AnchorPane root = null;
	    	try {
	    		root = FXMLLoader.load(getClass().getResource("/views/offres/user/Authentification.fxml"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	      navHomeAnchor.getChildren().setAll(root);
	    }

    
    
    
    

}
