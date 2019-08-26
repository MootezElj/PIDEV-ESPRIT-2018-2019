package controllers.offres.demande;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import controllers.offres.projet.ListeDemandesProjetController;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import entities.offres.Projet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import services.offres.localeProjet.LocaleProjetService;
import services.offres.projet.ServiceProjet;

public class ListeOffresController implements Initializable {
	@FXML
	private AnchorPane projetPublieAnchor = new AnchorPane();

	@FXML
	private TableView<Projet> offresTableView;

	@FXML
	private TableColumn<?, ?> nomProjetC;

	@FXML
	private TableColumn<?, ?> titreProjetC;

	@FXML
	private TableColumn<?, ?> nomClientC;

	@FXML
	private TableColumn<?, ?> dateDebutProjetC;

	@FXML
	private TableColumn<?, ?> dateFinProjetC;

	@FXML
	private TableColumn<?, ?> dateCreationProjetC;

	@FXML
	private TableColumn<?, ?> coutProjetC;

	@FXML
	private TableColumn<?, ?> descriptionProjetC;

	@FXML
	private TableColumn<Projet, Void> ajouterDemandeC;

	@FXML
	private TableColumn<Projet, Void> mapProjetC;

	@FXML
	private TableColumn<Projet, Void> voirDemandes;

	private ServiceProjet serviceProjet = new ServiceProjet();
	private LocaleProjetService localeProjetService = new LocaleProjetService();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		nomProjetC.setCellValueFactory(new PropertyValueFactory<>("nomProjet"));
		nomClientC.setCellValueFactory(new PropertyValueFactory<>("idClient"));
		titreProjetC.setCellValueFactory(new PropertyValueFactory<>("TitreProjet"));
		dateDebutProjetC.setCellValueFactory(new PropertyValueFactory<>("DateDebut"));
		dateFinProjetC.setCellValueFactory(new PropertyValueFactory<>("dateFin"));
		dateCreationProjetC.setCellValueFactory(new PropertyValueFactory<>("dateCreation"));
		coutProjetC.setCellValueFactory(new PropertyValueFactory<>("cout"));
		descriptionProjetC.setCellValueFactory(new PropertyValueFactory<>("description"));
		//charge la table avec les projets
		offresTableView.setItems(loadPublishedProjects());
		addDemandeButtonToTable();
		addListeDemandesProjetButtonToTable();
		addMapButtonToTable();
	}


	//Charge tous les projets
	public ObservableList<Projet> loadPublishedProjects() {
		ObservableList<Projet> observableProjet = FXCollections.observableArrayList();
		// A changer par la liste de projet du client connecte
		try {
			observableProjet.addAll(serviceProjet.getPublishedProjets());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return observableProjet;
	}




	//Ajoute un bouton pour afficher les demandes  d'un projet
	private void addListeDemandesProjetButtonToTable() {
		Callback<TableColumn<Projet, Void>, TableCell<Projet, Void>> cellFactory = 
				new Callback<TableColumn<Projet, Void>, TableCell<Projet, Void>>() {
			@Override
			public TableCell<Projet, Void> call(final TableColumn<Projet, Void> param) {
				final TableCell<Projet, Void> cell = new TableCell<Projet, Void>() {
					private final Button btn = new Button();
					{
						btn.setOnAction((ActionEvent event) -> {
							Projet projet = getTableView().getItems().get(getIndex());
							System.out.println("liste demandes: " + projet.getNomProjet());
							loadListeDemandesProjetGui("listeDemandesProjet.fxml", projet);
						});
					}
					@Override
					public void updateItem(Void item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
						} else {
							FontAwesomeIconView edit = new FontAwesomeIconView();
							edit.setGlyphName("eye".toUpperCase());
							btn.setGraphic(edit);
							setGraphic(btn);
						}
					}

				};
				return cell;
			}
		};   
		voirDemandes.setCellFactory(cellFactory);
	}



	//Ajoute un bouton pour ajouter une demande a un projet
	private void addDemandeButtonToTable() {
		Callback<TableColumn<Projet, Void>, TableCell<Projet, Void>> cellFactory = 
				new Callback<TableColumn<Projet, Void>, TableCell<Projet, Void>>() {
			@Override
			public TableCell<Projet, Void> call(final TableColumn<Projet, Void> param) {
				final TableCell<Projet, Void> cell = new TableCell<Projet, Void>() {
					private final Button btn = new Button();
					{
						btn.setOnAction((ActionEvent event) -> {
							Projet projet = getTableView().getItems().get(getIndex());
							System.out.println("Demander: " + projet);
							loadAjouterDemandeGui("AjouterDemande.fxml", projet);
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
		ajouterDemandeC.setCellFactory(cellFactory);
	}



	//Ajoute un bouton pour	afficher la localisation du projet
	private void addMapButtonToTable() {
		Callback<TableColumn<Projet, Void>, TableCell<Projet, Void>> cellFactory = 
				new Callback<TableColumn<Projet, Void>, TableCell<Projet, Void>>() {
			@Override
			public TableCell<Projet, Void> call(final TableColumn<Projet, Void> param) {
				final TableCell<Projet, Void> cell = new TableCell<Projet, Void>() {
					private final Button btn = new Button();
					{
						btn.setOnAction((ActionEvent event) -> {
							Projet projet = getTableView().getItems().get(getIndex());
							System.out.println("Map: " + projet);
							if (localeProjetService.getPositionX(projet)!=0.0)
								loadMapProjetGui(projetPublieAnchor,projet);
							else
								btn.setDisable(true);
						});


					}
					@Override
					public void updateItem(Void item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
						} else {
							Projet projet = getTableView().getItems().get(getIndex());
							if (localeProjetService.getPositionX(projet)!=0.0){
								FontAwesomeIconView edit = new FontAwesomeIconView();
								edit.setGlyphName("GLOBE".toUpperCase());
								btn.setGraphic(edit);
								setGraphic(btn);
							}
						}
					}

				};
				return cell;
			}
		};   
		mapProjetC.setCellFactory(cellFactory);
	}

	//Charge la page AjouterProjet.fxml tout en la modifant pour etre capable de faire une MAJ
	public void loadAjouterDemandeGui(String gui, Projet projet) {
		AnchorPane root = null;
		try {
			//    		root = FXMLLoader.load(getClass().getResource("/views/offres/"+gui));
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(AjouterDemandeController.class.getResource("/views/offres/demande/"+gui));
			root = loader.load();
			AjouterDemandeController ajouterDemandeController = loader.getController();
			ajouterDemandeController.intitAjouterDemandeAtt(projet);
			projetPublieAnchor.getChildren().addAll(root);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.projetPublieAnchor.getChildren().setAll(root);
	}


	//Charge la page AjouterProjet.fxml tout en la modifant pour etre capable de faire une MAJ
	public void loadListeDemandesProjetGui(String gui, Projet projet) {
		AnchorPane root = null;
		try {
			//    		root = FXMLLoader.load(getClass().getResource("/views/offres/"+gui));
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(ListeDemandesProjetController.class.getResource("/views/offres/projet/"+gui));
			root = loader.load();
			ListeDemandesProjetController listeDemandesProjetController = loader.getController();
			listeDemandesProjetController.loadDemandesOfProjet(projet);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		projetPublieAnchor.getChildren().setAll(root);
	}




	public void loadMapProjetGui(AnchorPane anchor, Projet projet) {
		AnchorPane root = null;
		try {
			//    		root = FXMLLoader.load(getClass().getResource("/views/offres/"+gui));
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(ProjetMapController.class.getResource("/views/offres/demande/mapProjet.fxml"));
			root = loader.load();
			ProjetMapController projetMapController = loader.getController();
			projetMapController.initProjet(projet);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		projetPublieAnchor.getChildren().setAll(root);

	}

}
