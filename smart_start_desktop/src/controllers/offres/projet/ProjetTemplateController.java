package controllers.offres.projet;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Optional;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.qoppa.pdfViewerFX.PDFViewer;

import dao.offres.projet.ProjetDao;
import entities.offres.Projet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import services.offres.projet.ServiceProjet;
import services.offres.projet.ServiceProjetTech;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

public class ProjetTemplateController implements Initializable {

	ServiceProjet serviceProjet = new ServiceProjet();
	ServiceProjetTech serviceProjetTech = new ServiceProjetTech();
	ProjetDao projetDao = new ProjetDao();
	ListeProjetController listeProjetController = new ListeProjetController();
	
	@FXML
	private Text nomProjet;

	@FXML
	private Text coutProjet;

	@FXML
	private Text techStringsProjet;

	@FXML
	private Text dateProjet;
	
	private Projet projet = new Projet();
	
    @FXML
    private JFXButton publierBtn;
    
    @FXML
    void afficherDemandes(ActionEvent event) {
    	loadListeDemandesProjetGui(this.projet);
    }

    @FXML
    void onAjouterCompetance(ActionEvent event) {
    	loadAjouterCompetanceProjetGui("AjouterCompetanceProjet.fxml",projet);
    }

    @FXML
    void onModifierProjet(ActionEvent event) {
    	loadModifierProjetGui("AjouterProjet.fxml", projet);
    }

	
    @FXML
    void onPublierProjet(ActionEvent event) {
		//affichage d'un message de confirmation
    	if(projet.isPublie()) {
    		
    	}
		Alert confirmation=  new Alert(AlertType.CONFIRMATION);
		confirmation.setTitle("Valider");
		confirmation.setHeaderText("Vous etes sur de vouloir publier ce projet ?");
		confirmation.setContentText("Cliquer sur ok pour confirmer.");
		Optional<ButtonType> result = confirmation.showAndWait();
		//Si on appuie sur OK
		if (result.get()== ButtonType.OK)
		{
			serviceProjet.publierProjet(projet);
			serviceProjetTech.notifierFreelancers(projet, serviceProjetTech.getTechsByProjet(projet));
			//Debut Notif
			String title = "Succès";
			String message = "Projet "+projet.getNomProjet()+" publié avec Succès";
			NotificationType notification = NotificationType.SUCCESS;
			TrayNotification tray = new TrayNotification();
			tray.setTitle(title);
			tray.setMessage(message);
			tray.setNotificationType(notification);
			tray.showAndDismiss(new Duration(2000));
			//Fin notif
			SetProjet(projet);
		}
    }
    
    
    
    @FXML
    void onDeleteProjet(ActionEvent event) {
       	Alert confirmation=  new Alert(AlertType.CONFIRMATION);
    		confirmation.setTitle("Valider");
    		confirmation.setHeaderText("Vous etes sur de vouloir supprimer ce projet ?");
    		confirmation.setContentText("Cliquer sur ok pour confirmer.");
    		Optional<ButtonType> result = confirmation.showAndWait();
    		//Si on appuie sur OK
    		if (result.get()== ButtonType.OK)
    		{
    			projetDao.delete(projet);
    			SetProjet(projet);
    		}
    		
    }
    

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}

	public void SetProjet(Projet projet) {
		techStringsProjet.setText("");
		String pattern = "yyyy/MM/dd";
		DateFormat df = new SimpleDateFormat(pattern);
		String  techStringsP = "";
		this.projet = projet;
		System.out.println(this.projet.isPublie());
		nomProjet.setText(projet.getNomProjet());
		coutProjet.setText("$ "+Double.toString(projet.getCout()));
		serviceProjetTech.getTechsByProjet(projet).forEach(tech->{
		techStringsProjet.setText(techStringsProjet.getText()+"|"+tech.getNomTechnologie());
		});
		projet.getDateDebut();	
		//date Debut
		String dateDebut= df.format(projet.getDateDebut());
		String dateFin= df.format(projet.getDateFin());
		this.dateProjet.setText(dateDebut+"-->"+dateFin);
		if (projet.isPublie()) {
    		publierBtn.setText("Publié");
    		publierBtn.setDisable(true);
		}
		
	}


    
  //Charge la page AjouterProjet.fxml tout en la modifant pour etre capable de faire une MAJ
  	public void loadAjouterCompetanceProjetGui(String gui, Projet projet) {
  		AnchorPane root = null;
  		try {
  			Stage stage= new Stage();
  			FXMLLoader loader = new FXMLLoader();
  			loader.setLocation(AjouterCompetanceProjetController.class.getResource("/views/offres/projet/"+gui));
  			root = loader.load();
  			AjouterCompetanceProjetController ajouterCompetanceProjetController = loader.getController();
  			ajouterCompetanceProjetController.intitProjetAtt(projet);
  			stage.setScene(new Scene(root));
  			stage.show();
  		} catch (IOException e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  		}
  		
  	}

  	
	//Charge la page AjouterProjet.fxml tout en la modifant pour etre capable de faire une MAJ
	public void loadModifierProjetGui(String gui, Projet projet) {
		AnchorPane root = null;
		try {
			Stage stage= new Stage();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(AjouterProjetController.class.getResource("/views/offres/projet/"+gui));
			root = loader.load();
			AjouterProjetController ajouterProjetController = loader.getController();
			System.out.println(projet.getNomProjet());
			ajouterProjetController.intitModifierProjetAtt(projet);
			stage.setScene(new Scene(root));
  			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	//Charge la page qui possede une liste de tous les demnde du projet
	public void loadListeDemandesProjetGui(Projet projet) {
		AnchorPane root = null;
		try {
			//    		root = FXMLLoader.load(getClass().getResource("/views/offres/"+gui));
			Stage stage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(ListeDemandesProjetController.class.getResource("/views/offres/projet/listeDemandesProjet.fxml"));
			root = loader.load();
			ListeDemandesProjetController listeDemandesProjetController = loader.getController();
			listeDemandesProjetController.loadDemandesOfProjet(projet);
//			stage.setScene(new Scene(root));
//  			stage.show();
  			PDFViewer m_PDFViewer  = new PDFViewer(); 
  			root.getChildren().add(m_PDFViewer);
  			AnchorPane anchor = new AnchorPane(m_PDFViewer);
  			anchor.setPrefSize(root.getPrefWidth(), root.getPrefHeight());
  			anchor.getChildren().add(root);
  			Scene scene = new Scene(anchor);
  			stage.setScene(scene);
  			stage.show();
  			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
