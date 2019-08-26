package controllers.offres.projet;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

import controllers.offres.localeProjet.LocaleProjetController;
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
import javafx.util.Callback;
import services.offres.localeProjet.LocaleProjetService;
import services.offres.projet.ServiceProjet;
import utils.offre.UserSession;

public class OperationProjetController implements Initializable {


	@FXML
	private AnchorPane projetsAnchorPane;

	@FXML
	private TableView<Projet> ProjetTableView;

	@FXML
	private TableColumn<Projet, String> nomProjetC;

	@FXML
	private TableColumn<Projet, String> titreProjetC;

	@FXML
	private TableColumn<Projet, Date> dateCreationProjetC;



	@FXML
	private TableColumn<Projet, Void> modifierProjetC;
		

	@FXML
	private TableColumn<Projet, Void> supprimerProjetC;
	
    @FXML
    private TableColumn<Projet, Void> ajoutLocaleC;
	
	
	User currentUser = new User(); 

	ServiceProjet serviceProjet = new ServiceProjet();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		nomProjetC.setCellValueFactory(new PropertyValueFactory<>("nomProjet"));
		titreProjetC.setCellValueFactory(new PropertyValueFactory<>("TitreProjet"));
		dateCreationProjetC.setCellValueFactory(new PropertyValueFactory<>("dateCreation"));
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
								loadModifierProjetGui(projetsAnchorPane, "AjouterProjet.fxml", projet);
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
								LocaleProjetService localeProjetService = new LocaleProjetService();
								localeProjetService.supprimerLocaleDeProjet(data);
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

	
	
	//charge tous les boutons dans la table
	private void addAllButtons() {
		this.addModifierButtonToTable();
		this.addSupprimerButtonToTable(new ProjetDao());
		addLocaleBtnToTable();
	}

	//Charge la page AjouterProjet.fxml tout en la modifant pour etre capable de faire une MAJ
	public void loadModifierProjetGui(AnchorPane anchor, String gui, Projet projet) {
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
		anchor.getChildren().setAll(root);
	}
	
	//Ajoute un bouton de modification a la table
			private void addLocaleBtnToTable() {
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
									LocaleProjetService localeProjetService = new LocaleProjetService();
									localeProjetService.supprimerLocaleDeProjet(projet);
									loadAjouterLocaleProjetGui(projetsAnchorPane, "AjouterLocaleProjet.fxml", projet);
								});
							}
							@Override
							public void updateItem(Void item, boolean empty) {
								super.updateItem(item, empty);
								if (empty) {
									setGraphic(null);
								} else {
									FontAwesomeIconView edit = new FontAwesomeIconView();
									edit.setGlyphName("GLOBE");
									btn.setGraphic(edit);
									setGraphic(btn);
								}
							}
							

						};
						return cell;
					}
				};   
				ajoutLocaleC.setCellFactory(cellFactory);
			}
	

			
			private void loadAjouterLocaleProjetGui(AnchorPane projetsAnchorPane, String string,
					Projet projet) {
				AnchorPane root = null;
				try {
					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(LocaleProjetController.class.getResource("/views/offres/projet/"+string));
					root = loader.load();
					LocaleProjetController localeProjetController = loader.getController();
					System.out.println(projet.getNomProjet());
					localeProjetController.initProjet(projet);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				projetsAnchorPane.getChildren().setAll(root);
				
			}

}
