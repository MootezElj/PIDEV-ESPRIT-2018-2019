package controllers.offres.projet;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import entities.offres.Projet;
import entities.offres.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import services.offres.projet.ServiceProjet;
import utils.offre.UserSession;

public class NavigationProjetController implements Initializable{


	ServiceProjet serviceProjet= new ServiceProjet();
	User currentUser = new User();
	
    @FXML
    private JFXButton AjouterProjetLink;

    @FXML
    private JFXButton MesProjetLink;
    
    @FXML
    private BorderPane borderPane;
    
    
    public void loadAjouterProjetGUI() {
    	loadGui("AjouterProjet.fxml");
    }
    
    public void loadMesProjetGUI() {
    	loadGui("listeProjet.fxml");
    }

    public void loadOffresGUI(MouseEvent event) {
    	loadGui("listeOffres.fxml");
    }
    
    @FXML
    void loadDemandesMesProjetsGUI(ActionEvent event) {
    	
    	currentUser= UserSession.getInstace(new User()).getUser();

    	loadListeDemandesProjetsGui("listeDemandesProjet.fxml",
    			serviceProjet.getProjetsOfUser(currentUser));
    }
    
    public void goBack(MouseEvent event) {
    	System.out.println("hello");
//    	loadGui("listeOffres.fxml");
    }
    
    @FXML
    void loadOperationGUI(ActionEvent event) {
    	loadGui("operationProjet.fxml");
    }
    
    
    
    
    
    
    @FXML
    void loadGui(String gui) {
    	AnchorPane root = null;
    	try {
    		root = FXMLLoader.load(getClass().getResource("/views/offres/projet/"+gui));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	borderPane.setCenter(root);
    }

    @FXML
    void GUIMesProjets() {

    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loadAjouterProjetGUI();
	}
	
	//Charge la page AjouterProjet.fxml tout en la modifant pour etre capable de faire une MAJ
		public void loadListeDemandesProjetsGui( String gui, ArrayList<Projet> projets) {
			AnchorPane root = null;
			try {
				//    		root = FXMLLoader.load(getClass().getResource("/views/offres/"+gui));
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(ListeDemandesProjetController.class.getResource("/views/offres/projet/"+gui));
				root = loader.load();
				ListeDemandesProjetController listeDemandesProjetController = loader.getController();
				listeDemandesProjetController.loadDemandesOfProjets(projets);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			borderPane.setCenter(root);
		}

}
