package controllers.offres.projet;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import entities.offres.Demande;
import entities.offres.Projet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import services.offres.demande.ServiceDemande;
import services.offres.projet.ServiceProjet;

public class ListeDemandesProjetController implements Initializable {


	@FXML
	private TableView<Demande> demandesProjetTableView;

	@FXML
	private TableColumn<?, ?> nomProjetC;

	@FXML
	private TableColumn<?, ?> freelancerC;

	@FXML
	private TableColumn<?, ?> titreDemandeC;

	@FXML
	private TableColumn<?, ?> perMinC;

	@FXML
	private TableColumn<?, ?> perMaxC;

	@FXML
	private TableColumn<?, ?> coutDemandeC;

	@FXML
	private TableColumn<?, ?> dateDemandeC;

	@FXML
	private TableColumn<?, ?> descriptionDemandeC;
	
	@FXML
	private TableColumn<Demande, Void> negocierDemandeC1;
	

	@FXML
	private TableColumn<Projet, Void> voirDemandes;

	private ServiceDemande serviceDemande = new ServiceDemande();
	private ServiceProjet ServiceProjet = new ServiceProjet();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		negocierDemandeC1.setVisible(false);
	}

	
	//Charge tous les demande d'un projet
	public ObservableList<Demande> loadListeDemandeProjects(Projet p) {
		ObservableList<Demande> observableProjet = FXCollections.observableArrayList();
		observableProjet.addAll(serviceDemande.getListeDemandeOfProjet(p));
		return observableProjet;
	}

	
	//Charge tous les demande d'une liste de projet
		public ObservableList<Demande> loadListeDemandeProjects(ArrayList<Projet> projets) {
			ObservableList<Demande> observableProjet = FXCollections.observableArrayList();
			projets.forEach(p->{
				observableProjet.addAll(serviceDemande.getListeDemandeOfProjet(p));
			});
			return observableProjet;
		}

	public void loadDemandesOfProjet(Projet projet) {
		nomProjetC.setCellValueFactory(new PropertyValueFactory<>("nomProjet"));
		freelancerC.setCellValueFactory(new PropertyValueFactory<>("idFreelancer"));
		titreDemandeC.setCellValueFactory(new PropertyValueFactory<>("titreDemande"));
		perMinC.setCellValueFactory(new PropertyValueFactory<>("periodeRealisationMin"));
		perMaxC.setCellValueFactory(new PropertyValueFactory<>("periodeRealisationMax"));
		coutDemandeC.setCellValueFactory(new PropertyValueFactory<>("coutDemande"));
		dateDemandeC.setCellValueFactory(new PropertyValueFactory<>("dateDemande"));
		descriptionDemandeC.setCellValueFactory(new PropertyValueFactory<>("descriptionDemande"));
		//charge la table avec les projets
		demandesProjetTableView.setItems(loadListeDemandeProjects(projet));
	
	}


	public void loadDemandesOfProjets(ArrayList<Projet> projets) {
		nomProjetC.setCellValueFactory(new PropertyValueFactory<>("nomProjet"));
		freelancerC.setCellValueFactory(new PropertyValueFactory<>("idFreelancer"));
		titreDemandeC.setCellValueFactory(new PropertyValueFactory<>("titreDemande"));
		perMinC.setCellValueFactory(new PropertyValueFactory<>("periodeRealisationMin"));
		perMaxC.setCellValueFactory(new PropertyValueFactory<>("periodeRealisationMax"));
		coutDemandeC.setCellValueFactory(new PropertyValueFactory<>("coutDemande"));
		dateDemandeC.setCellValueFactory(new PropertyValueFactory<>("dateDemande"));
		descriptionDemandeC.setCellValueFactory(new PropertyValueFactory<>("descriptionDemande"));
		demandesProjetTableView.setItems(loadListeDemandeProjects(projets));
		addNegocierButtonToTable();
		negocierDemandeC1.setVisible(true);
	}
	
	
	//Ajoute un bouton pour negocier une demande
	private void addNegocierButtonToTable() {
		Callback<TableColumn<Demande, Void>, TableCell<Demande, Void>> cellFactory = 
				new Callback<TableColumn<Demande, Void>, TableCell<Demande, Void>>() {
			@Override
			public TableCell<Demande, Void> call(final TableColumn<Demande, Void> param) {
				final TableCell<Demande, Void> cell = new TableCell<Demande, Void>() {
					private final Button btn = new Button();
					{
						btn.setOnAction((ActionEvent event) -> {
							Demande demande = getTableView().getItems().get(getIndex());
							System.out.println("Negocier: " + demande.getNomProjet());
//							loadListeDemandesProjetGui("listeDemandesProjet.fxml", projet);
						});
					}
					@Override
					public void updateItem(Void item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
						} else {
							FontAwesomeIconView edit = new FontAwesomeIconView();
							edit.setGlyphName("PLUS".toUpperCase());
							btn.setGraphic(edit);
							setGraphic(btn);
						}
					}

				};
				return cell;
			}
		};   
		negocierDemandeC1.setCellFactory(cellFactory);
	}


}
