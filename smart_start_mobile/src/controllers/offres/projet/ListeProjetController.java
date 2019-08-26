package controllers.offres.projet;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import entities.offres.Projet;
import entities.offres.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.FlowPane;
import services.offres.projet.ServiceProjet;
import utils.offre.UserSession;

public class ListeProjetController implements Initializable {
//	
//    @FXML
//    private AnchorPane anchor;


    @FXML
    private FlowPane flowPane;
    
    
	User currentUser = new User();
	ServiceProjet serviceProjet = new ServiceProjet();
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loadProjetTemplateGui("ProjetTemplate.fxml",loadProjectsClient());
	}
    
	

	
	//Charge tous les projets
	public ObservableList<Projet> loadProjectsClient() {
		ObservableList<Projet> observableProjet = FXCollections.observableArrayList();
		// A changer par la liste de projet du client connecte
		try {
			currentUser= UserSession.getInstace(new User()).getUser();
			System.out.println(currentUser.getFirstName());
			observableProjet.addAll(serviceProjet.getClientProjets(currentUser));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return observableProjet;
	}
	
	
	
	//Charge la page AjouterProjet.fxml tout en la modifant pour etre capable de faire une MAJ
	public void loadProjetTemplateGui( String gui, ObservableList<Projet> projets) {
		projets.forEach(projet->{
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(AjouterCompetanceProjetController.class.getResource("/views/offres/projet/"+gui));
				flowPane.getChildren().add(loader.load());
				ProjetTemplateController projetTemplateController = loader.getController();
				projetTemplateController.SetProjet(projet);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		});
	}
	


}
