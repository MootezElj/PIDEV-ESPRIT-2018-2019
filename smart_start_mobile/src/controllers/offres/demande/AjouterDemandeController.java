package controllers.offres.demande;



import java.net.URL;
import java.text.ParseException;
import java.util.Optional;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import entities.offres.Demande;
import entities.offres.Projet;
import entities.offres.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import services.offres.demande.ServiceDemande;
import utils.offre.UserSession;

public class AjouterDemandeController implements Initializable {

	@FXML
	private AnchorPane PaneProjet;

	@FXML
	private Text titre;

	@FXML
	private JFXTextField titreDemande;

	@FXML
	private JFXTextArea descriptionDemande;

	@FXML
	private JFXTextField coutDemande;

	@FXML
	private JFXButton valider;

	@FXML
	private JFXTextField perRealMin;

	@FXML
	private JFXTextField perRealMax;

	private Demande demande ;

	private Projet projet;

	private boolean update;
	private User currentUser=null;
	private UserSession userSession = new UserSession();

	public boolean isUpdate() {
		return update;
	}

	public void setUpdate(boolean update) {
		this.update = update;
	}



	public Demande getDemande() {
		return demande;
	}

	public void setDemande(Demande demande) {
		this.demande = demande;
	}

	@FXML
	public void onClickAjouterDemande() throws ParseException{
		//validation des champs
		boolean coutNumeric = true;
		boolean perMaxNumeric = true;
		boolean perMinNumeric = true;
		Double cout = 0.0;
		Double perMin = 0.0;
		Double perMax = 0.0;
		String msgErreur = "";
		try {
			cout = Double.parseDouble(coutDemande.getText());
		} catch (NumberFormatException e) {
			coutNumeric = false; 
			msgErreur += "Le champ cout est invalide \n";
		}
		try {
			perMin = Double.parseDouble(perRealMin.getText());
		} catch (NumberFormatException e) {
			perMinNumeric = false; 
			msgErreur += "Le champ periode de realisation Min est invalide \n";
		}
		try {
			perMax = Double.parseDouble(perRealMax.getText());
		} catch (NumberFormatException e) {
			perMaxNumeric = false; 
			msgErreur += "Le champ periode de realisation Max est invalide \n";
		}
		//Verification si le cout est convertible et supp a 0 et le titre du projet est valide
		if( (coutNumeric ) && ( cout > 0.0) && (perMaxNumeric )&& ( perMin > 0.0) && (perMinNumeric )&& ( perMax > perMin) && (titreDemande.getText().chars().allMatch(Character::isLetter))
				&& (!titreDemande.getText().equals(""))&& (!descriptionDemande.getText().equals("")))

		{
			// Confirmation de l'ajout
			Alert confirmation=  new Alert(AlertType.CONFIRMATION);
			confirmation.setTitle("Valider");
			confirmation.setHeaderText("Vous etes sur ?");
			confirmation.setContentText("Cliquer sur ok pour confirmer.");
			Optional<ButtonType> result = confirmation.showAndWait();
			//Si on appuie sur OK
			if (result.get()== ButtonType.OK)
			{
				ServiceDemande serviceDemande = new ServiceDemande();
				if (isUpdate()) {
					demande.setTitreDemande(titreDemande.getText());
					demande.setPeriodeRealisationMin(Double.parseDouble(perMin.toString()));
					demande.setPeriodeRealisationMax(Double.parseDouble(perMax.toString()));
					demande.setCoutDemande(Double.parseDouble(cout.toString()));
					demande.setDescriptionDemande(descriptionDemande.getText());
					serviceDemande.MAJDemande(demande);
				}
				else {
					currentUser= UserSession.getInstace(new User()).getUser();
					Demande demande = new Demande( projet, currentUser, titreDemande.getText(), descriptionDemande.getText(), Double.parseDouble(perRealMin.getText().toString()), 
					Double.parseDouble(perRealMax.getText().toString()),  Double.parseDouble(coutDemande.getText().toString()));
					serviceDemande.ajouterDemande(demande);
					Alert success = new Alert(AlertType.INFORMATION);
					success.setTitle("Success");
					success.setHeaderText("Demande ajoute avec success");
					success.showAndWait();
				}
				//Reinitialisation des champs
				titreDemande.setText("");
				descriptionDemande.setText("");
				perRealMin.setText("");
				perRealMax.setText("");
				coutDemande.setText("");

			}
			//---- Fin Si on appuie sur OK			
			// ----- Fin Confirmation de l'ajout
		}
		//----- Fin Verification si le cout est convertible et supp a 0 et le titre du projet est valide

		else {

			if (titreDemande.getText().equals("")) {
				msgErreur += "Le champ titre de la demande ne peut pas etre vide \n";
			}

			if (descriptionDemande.getText().equals("")) {
				msgErreur += "Le champ description ne peut pas etre vide \n";
			}
			if (!(titreDemande.getText().chars().allMatch(Character::isLetter)) || (titreDemande.getText().equals(""))) {
				msgErreur += "Le champ titre est non valide \n";
			}

			// Si un des champs est invalide
			Alert erreur = new Alert(AlertType.ERROR);
			erreur.setTitle("Erreur");
			erreur.setHeaderText("Champ(s) invalide(s)");
			erreur.setContentText(msgErreur);
			erreur.showAndWait();
		}
		// ------ Fin Si un des champs est invalide
	}



	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	//charge le projet a modifier
	public void intitModifierDemandeAtt(Demande demande) {
		titreDemande.setText(demande.getTitreDemande());
		descriptionDemande.setText(demande.getDescriptionDemande());
		coutDemande.setText(Double.toString(demande.getCoutDemande()));
		perRealMin.setText(Double.toString(demande.getPeriodeRealisationMin()));
		perRealMax.setText(Double.toString(demande.getPeriodeRealisationMax()));
		setUpdate(true);
		setDemande(demande);
		titre.setText("Modifier Demande");
	}
	
	//charge le projet a modifier
	public void intitAjouterDemandeAtt(Projet pro) {
		this.projet = pro;
		setUpdate(false);
	}


}
