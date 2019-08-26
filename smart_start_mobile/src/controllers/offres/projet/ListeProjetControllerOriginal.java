package controllers.offres.projet;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

import dao.offres.projet.ProjetDao;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import entities.offres.Projet;
import entities.offres.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;
import javafx.util.Duration;
import services.offres.projet.ServiceProjet;
import services.offres.projet.ServiceProjetTech;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;
import utils.offre.UserSession;

public class ListeProjetControllerOriginal implements Initializable {

	@FXML
	private BorderPane listProjetBorderPane;

	@FXML
	private TableView<Projet> ProjetTableView;

	@FXML
	private TableColumn<Projet, String> nomProjetC;

	@FXML
	private TableColumn<Projet, String> titreProjetC;

	@FXML
	private TableColumn<Projet, Date> dateDebutProjetC;

	@FXML
	private TableColumn<Projet, Date> dateFinProjetC;

	@FXML
	private TableColumn<Projet, Date> dateCreationProjetC;

	@FXML
	private TableColumn<Projet, Date> datePublicationProjetC;

	@FXML
	private TableColumn<Projet, Double> coutProjetC;

	@FXML
	private TableColumn<Projet, String> descriptionProjetC;

	@FXML
	private TableColumn<Projet, Void> modifierProjetC;
	
	@FXML
	private TableColumn<Projet, Void> publierProjetC;

	@FXML
	private TableColumn<Projet, Void> supprimerProjetC;
	
	@FXML
	private TableColumn<Projet, Void> TechAjoutC;
	
	
	
	User currentUser = new User(); 

	ServiceProjet serviceProjet = new ServiceProjet();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		nomProjetC.setCellValueFactory(new PropertyValueFactory<>("nomProjet"));
		titreProjetC.setCellValueFactory(new PropertyValueFactory<>("TitreProjet"));
		dateDebutProjetC.setCellValueFactory(new PropertyValueFactory<>("DateDebut"));
		dateFinProjetC.setCellValueFactory(new PropertyValueFactory<>("dateFin"));
		dateCreationProjetC.setCellValueFactory(new PropertyValueFactory<>("dateCreation"));
		datePublicationProjetC.setCellValueFactory(new PropertyValueFactory<>("datePublication"));
		coutProjetC.setCellValueFactory(new PropertyValueFactory<>("cout"));
		descriptionProjetC.setCellValueFactory(new PropertyValueFactory<>("description"));
		addAllButtons();
		//charge la table avec les projets
		ProjetTableView.setItems(loadProjectsClient());
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

	//Ajoute un bouton de modification a la table
		private void addModifierButtonToTable() {
			Callback<TableColumn<Projet, Void>, TableCell<Projet, Void>> cellFactory = 
					new Callback<TableColumn<Projet, Void>, TableCell<Projet, Void>>() {
				@Override
				public TableCell<Projet, Void> call(final TableColumn<Projet, Void> param) {
					final TableCell<Projet, Void> cell = new TableCell<Projet, Void>() {
						private final Button btn = new Button();
						{
							btn.setOnAction((ActionEvent event) -> {
								Projet projet = getTableView().getItems().get(getIndex());
								System.out.println("Modifier: " + projet);
								loadModifierProjetGui(listProjetBorderPane, "AjouterProjet.fxml", projet);
							});
						}
						@Override
						public void updateItem(Void item, boolean empty) {
							super.updateItem(item, empty);
							if (empty) {
								setGraphic(null);
							} else {
								FontAwesomeIconView edit = new FontAwesomeIconView();
								edit.setGlyphName("PENCIL");
								btn.setGraphic(edit);
								setGraphic(btn);
							}
						}

					};
					return cell;
				}
			};   
			modifierProjetC.setCellFactory(cellFactory);
		}

	//Ajoute un bouton de Suppression a la table
	private void addSupprimerButtonToTable(ProjetDao projetDao) {
		Callback<TableColumn<Projet, Void>, TableCell<Projet, Void>> cellFactory = 
				new Callback<TableColumn<Projet, Void>, TableCell<Projet, Void>>() {
			@Override
			public TableCell<Projet, Void> call(final TableColumn<Projet, Void> param) {
				final TableCell<Projet, Void> cell = new TableCell<Projet, Void>() {
					
					private final Button btn = new Button();

					{
						btn.setOnAction((ActionEvent event) -> {
							Projet data = getTableView().getItems().get(getIndex());
							//affichage d'un message de confirmation
							Alert confirmation=  new Alert(AlertType.CONFIRMATION);
							confirmation.setTitle("Valider");
							confirmation.setHeaderText("Vous etes sur de vouloir supprimer ce projet ?");
							confirmation.setContentText("Cliquer sur ok pour confirmer.");
							Optional<ButtonType> result = confirmation.showAndWait();
							//Si on appuie sur OK
							if (result.get()== ButtonType.OK)
							{
								projetDao.delete(data);
								ProjetTableView.setItems(loadProjectsClient());
							}
							
						});	                        	                   
					}

					@Override
					public void updateItem(Void item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
						} else {
							FontAwesomeIconView del = new FontAwesomeIconView();
							del.setGlyphName("TRASH");
							btn.setGraphic(del);
							
							setGraphic(btn);

						}
					}
				};
				return cell;
			}
		};   
		supprimerProjetC.setCellFactory(cellFactory);
	}

	
	//Ajoute un bouton de publication a la table
	private void addPublierButtonToTable() {
		ServiceProjetTech serviceProjetTech = new ServiceProjetTech();
		Callback<TableColumn<Projet, Void>, TableCell<Projet, Void>> cellFactory = 
				new Callback<TableColumn<Projet, Void>, TableCell<Projet, Void>>() {
			@Override
			public TableCell<Projet, Void> call(final TableColumn<Projet, Void> param) {
				final TableCell<Projet, Void> cell = new TableCell<Projet, Void>() {
					private final Button publierBtn = new Button();
					{
						publierBtn.setOnAction((ActionEvent event) -> {
							Projet projet = getTableView().getItems().get(getIndex());
							if (projet.isPublie()) {
								publierBtn.setText("Publié");
							}
							//affichage d'un message de confirmation
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
								ProjetTableView.setItems(loadProjectsClient());
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
							}
							
						});	                        	                   
					}
					//Bouton des projets deja publiés
					private final Button invalidBtn = new Button();
					{
						invalidBtn.setDisable(true);                  	                   
					}
					
					
					@Override
					public void updateItem(Void item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
						} else {
							Projet projet = getTableView().getItems().get(getIndex());
							if (!projet.isPublie()) 
							{
						
								FontAwesomeIconView upload = new FontAwesomeIconView();
								upload.setGlyphName("UPLOAD");
								publierBtn.setGraphic(upload);
								setGraphic(publierBtn);}
							else {
								FontAwesomeIconView upload = new FontAwesomeIconView();
								upload.setGlyphName("BAN");
								invalidBtn.setGraphic(upload);
								setGraphic(invalidBtn);
							}
						}
					}
				};
				return cell;
			}
		};   
		publierProjetC.setCellFactory(cellFactory);
	}
	
	
	//Ajoute un bouton de modification a la table
	private void addComepanceButtonToTable() {
		Callback<TableColumn<Projet, Void>, TableCell<Projet, Void>> cellFactory = 
				new Callback<TableColumn<Projet, Void>, TableCell<Projet, Void>>() {
			@Override
			public TableCell<Projet, Void> call(final TableColumn<Projet, Void> param) {
				final TableCell<Projet, Void> cell = new TableCell<Projet, Void>() {
					private final Button btn = new Button();
					{
						btn.setOnAction((ActionEvent event) -> {
							Projet projet = getTableView().getItems().get(getIndex());
							System.out.println("Competance: " + projet);
							loadAjouterCompetanceProjetGui(listProjetBorderPane, "AjouterCompetanceProjet.fxml", projet);
						});
					}
					@Override
					public void updateItem(Void item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
						} else {
							FontAwesomeIconView edit = new FontAwesomeIconView();
							edit.setGlyphName("accusoft");
							btn.setGraphic(edit);
							setGraphic(btn);
						}
					}

				};
				return cell;
			}
		};   
		TechAjoutC.setCellFactory(cellFactory);
	}

	
	//charge tous les boutons dans la table
	private void addAllButtons() {
		this.addModifierButtonToTable();
		this.addSupprimerButtonToTable(new ProjetDao());
		this.addPublierButtonToTable();
		addComepanceButtonToTable();
	}

	//Charge la page AjouterProjet.fxml tout en la modifant pour etre capable de faire une MAJ
	public void loadModifierProjetGui(BorderPane borderPane, String gui, Projet projet) {
		AnchorPane root = null;
		try {
			//    		root = FXMLLoader.load(getClass().getResource("/views/offres/"+gui));
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(AjouterProjetController.class.getResource("/views/offres/projet/"+gui));
			root = loader.load();
			AjouterProjetController ajouterProjetController = loader.getController();
			System.out.println(projet.getNomProjet());
			ajouterProjetController.intitModifierProjetAtt(projet);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		borderPane.setCenter(root);
	}
	
	
	//Charge la page AjouterProjet.fxml tout en la modifant pour etre capable de faire une MAJ
	public void loadAjouterCompetanceProjetGui(BorderPane borderPane, String gui, Projet projet) {
		AnchorPane root = null;
		try {
			//    		root = FXMLLoader.load(getClass().getResource("/views/offres/"+gui));
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(AjouterCompetanceProjetController.class.getResource("/views/offres/projet/"+gui));
			root = loader.load();
			AjouterCompetanceProjetController ajouterCompetanceProjetController = loader.getController();
			ajouterCompetanceProjetController.intitProjetAtt(projet);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		borderPane.setCenter(root);
	}
	


}
