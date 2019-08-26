package controllers.offres.projet;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import entities.offres.Projet;
import entities.offres.Technologie;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import services.offres.projet.ServiceProjetTech;
import services.offres.projet.ServiceTechnologie;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

public class AjouterCompetanceProjetController implements Initializable{

    @FXML
    private JFXTextField nomProjet;
	
	@FXML
	private HBox techHbox = new HBox();
	
	@FXML
	private JFXButton valider;
	
	List<String> techChoix = new ArrayList<String>();
	
	Projet projet = new Projet();
	
	//Dependencies
	ServiceTechnologie serviceTechnologie = new ServiceTechnologie();
	
	//charger les checkbox et suit le comportement tout en changeant une liste des techs choisies
	public void loadTechs(){
		List<Technologie> listeTousTech = serviceTechnologie.getAllTechs();
		final CheckBox[] cbs = new CheckBox[listeTousTech.size()];
		//pour chaque tech charge de la base on va creer un checkbox
		for (int i = 0; i < listeTousTech.size(); i++) {
		    final CheckBox cb = cbs[i] =
		       new CheckBox(listeTousTech.get(i).getNomTechnologie());
		    //permet de definir le comportement du checkbox si il est checked ou non
		    cb.selectedProperty().addListener(new ChangeListener<Boolean>() {
				@Override
				public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
					if (newValue) {
						techChoix.add(cb.getText());
					System.out.println(techChoix.size());}
					else 
						techChoix.remove(cb.getText());
				}
			});
		}
		techHbox.getChildren().addAll(cbs);
	}



	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loadTechs();
	}
	
    @FXML
    void onClickAjouterTechsProjet(ActionEvent event) {
    	ServiceProjetTech serviceProjetTech = new ServiceProjetTech();
    	ServiceTechnologie serviceTechnologie = new ServiceTechnologie();
    	Alert confirmation=  new Alert(AlertType.CONFIRMATION);
		confirmation.setTitle("Valider");
		confirmation.setHeaderText("Vous etes sur ?");
		confirmation.setContentText("Cliquer sur ok pour confirmer.");
		Optional<ButtonType> result = confirmation.showAndWait();
		//Si on appuie sur OK
		if (result.get()== ButtonType.OK)
		{
			serviceProjetTech.removeAllTech(projet);
			serviceProjetTech.ajouterTech(projet, serviceProjetTech.getTechsByNameList(techChoix));
			//Debut Notif
			String title = "Succès";
			String message = "Competance ajouter avec Succès";
			NotificationType notification = NotificationType.SUCCESS;
			TrayNotification tray = new TrayNotification();
			tray.setTitle(title);
			tray.setMessage(message);
			tray.setNotificationType(notification);
			tray.showAndDismiss(new Duration(2000));
			Stage stage = (Stage) valider.getScene().getWindow();
			stage.close();
			//Fin notif
		}
    }
	
	
	//charge le projet a modifier
	public void intitProjetAtt(Projet projet) {
		nomProjet.setText(projet.getNomProjet());
		this.projet = projet;
	}
	
}
