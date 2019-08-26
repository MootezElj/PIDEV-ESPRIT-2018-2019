package controllers.offres.localeProjet;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import entities.offres.LocaleProjet;
import entities.offres.Projet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import services.offres.localeProjet.LocaleProjetService;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

public class LocaleProjetController implements Initializable{

	@FXML
	private AnchorPane PaneLocaleProjet;

	@FXML
	private Text cordZ;

	@FXML
	private JFXTextField cordX;

	@FXML
	private JFXTextField cordY;

	@FXML
	private JFXButton valider;

	private Projet projet = new Projet();

	private LocaleProjetService localeProjetService = new LocaleProjetService();

	@FXML
	void OnClickAjouterLocaleProjet(ActionEvent event) {




		boolean numeric = true;

		try {
			Double num = Double.parseDouble(cordX.getText());
			Double num2 = Double.parseDouble(cordY.getText());
		} catch (NumberFormatException e) {
			numeric = false;
		}

		if(numeric) {
			Alert confirmation=  new Alert(AlertType.CONFIRMATION);
			confirmation.setTitle("Valider");
			confirmation.setHeaderText("Vous etes sur de vouloir confirmer ces cordonnées ?");
			confirmation.setContentText("Cliquer sur ok pour confirmer.");
			Optional<ButtonType> result = confirmation.showAndWait();
			//Si on appuie sur OK
			if (result.get()== ButtonType.OK)
			{
				localeProjetService.ajouterLocaleProjet(new LocaleProjet(projet,Double.parseDouble(cordX.getText()) , Double.parseDouble(cordY.getText())));
				//Debut Notif
				String title = "Succès";
				String message = "Cordonnées de "+projet.getNomProjet()+" ajouté avec Succès";
				NotificationType notification = NotificationType.SUCCESS;
				TrayNotification tray = new TrayNotification();
				tray.setTitle(title);
				tray.setMessage(message);
				tray.setNotificationType(notification);
				tray.showAndDismiss(new Duration(2000));
				cordX.setText("");
				cordY.setText("");
				//Fin notif
			}
		}
		else
		{
			Alert confirmation=  new Alert(AlertType.ERROR);
			confirmation.setTitle("Erreur");
			confirmation.setHeaderText("Entré invalide");
			confirmation.setContentText("Champ invalide !!");
			confirmation.showAndWait();
		}






	}
	public void initProjet(Projet p) {
		this.projet = p;

	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}




}
