package controllers.offres.demande;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import entities.offres.Demande;
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
import services.offres.demande.ServiceDemande;
import services.offres.projet.ServiceProjet;
import utils.offre.UserSession;

public class ListeDemandeController implements Initializable {


    @FXML
    private AnchorPane listeDemandeAnchor;

    @FXML
    private TableView<Demande> demandeTableView;

    @FXML
    private TableColumn<?, ?> titreDemandeC;

    @FXML
    private TableColumn<?, ?> projetDemandeC;

    @FXML
    private TableColumn<?, ?> periodeMinDemandeC;

    @FXML
    private TableColumn<?, ?> periodeMaxDemandeC;

    @FXML
    private TableColumn<?, ?> coutDemandeC;

    @FXML
    private TableColumn<?, ?> dateDemandeC;

    @FXML
    private TableColumn<?, ?> descriptionDemandeC;


	@FXML
	private TableColumn<Demande, Void> modifierDemandeC;
	

	@FXML
	private TableColumn<Demande, Void> supprimerDemandeC;
	
	ServiceDemande serviceDemande = new ServiceDemande();
	

	ServiceProjet serviceProjet = new ServiceProjet();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		titreDemandeC.setCellValueFactory(new PropertyValueFactory<>("titreDemande"));
		projetDemandeC.setCellValueFactory(new PropertyValueFactory<>("nomProjet"));
		periodeMinDemandeC.setCellValueFactory(new PropertyValueFactory<>("periodeRealisationMin"));
		periodeMaxDemandeC.setCellValueFactory(new PropertyValueFactory<>("periodeRealisationMax"));
		coutDemandeC.setCellValueFactory(new PropertyValueFactory<>("coutDemande"));
		dateDemandeC.setCellValueFactory(new PropertyValueFactory<>("dateDemande"));
		descriptionDemandeC.setCellValueFactory(new PropertyValueFactory<>("descriptionDemande"));
		addAllButtons();
		//charge la table avec les projets
		demandeTableView.setItems(loadDemandeFreelancer());
	}

	//Charge tous les projets
	public ObservableList<Demande> loadDemandeFreelancer() {
		ObservableList<Demande> observableDemande = FXCollections.observableArrayList();
		//TODO A changer par la liste de demande du freelancer connecte
		User currentUser = UserSession.getInstace(new User()).getUser();
		try {
			observableDemande.addAll(serviceDemande.getDemandeFreelancer(currentUser));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return observableDemande;
	}

	//Ajoute un bouton de modification a la table
		private void addModifierButtonToTable() {
			Callback<TableColumn<Demande, Void>, TableCell<Demande, Void>> cellFactory = 
					new Callback<TableColumn<Demande, Void>, TableCell<Demande, Void>>() {
				@Override
				public TableCell<Demande, Void> call(final TableColumn<Demande, Void> param) {
					final TableCell<Demande, Void> cell = new TableCell<Demande, Void>() {
						private final Button btn = new Button();
						{
							btn.setOnAction((ActionEvent event) -> {
								Demande demande = getTableView().getItems().get(getIndex());
								System.out.println("Modifier: " + demande);
								loadModifierDemandeGui("AjouterDemande.fxml", demande);
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
			modifierDemandeC.setCellFactory(cellFactory);
		}

	//Ajoute un bouton de Suppression a la table
	private void addSupprimerButtonToTable() {
		Callback<TableColumn<Demande, Void>, TableCell<Demande, Void>> cellFactory = 
				new Callback<TableColumn<Demande, Void>, TableCell<Demande, Void>>() {
			@Override
			public TableCell<Demande, Void> call(final TableColumn<Demande, Void> param) {
				final TableCell<Demande, Void> cell = new TableCell<Demande, Void>() {
					
					private final Button btn = new Button();

					{
						btn.setOnAction((ActionEvent event) -> {
							Demande data = getTableView().getItems().get(getIndex());
							//affichage d'un message de confirmation
							Alert confirmation=  new Alert(AlertType.CONFIRMATION);
							confirmation.setTitle("Valider");
							confirmation.setHeaderText("Vous etes sur de vouloir supprimer cet demande ?");
							confirmation.setContentText("Cliquer sur ok pour confirmer.");
							Optional<ButtonType> result = confirmation.showAndWait();
							//Si on appuie sur OK
							if (result.get()== ButtonType.OK)
							{
								serviceDemande.supprimerDemande(data);
								demandeTableView.setItems(loadDemandeFreelancer());
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
		supprimerDemandeC.setCellFactory(cellFactory);
	}

	

	
	//charge tous les boutons dans la table
	private void addAllButtons() {
		this.addModifierButtonToTable();
		this.addSupprimerButtonToTable();
	}

	//Charge la page AjouterProjet.fxml tout en la modifant pour etre capable de faire une MAJ
	public void loadModifierDemandeGui(String gui, Demande demande) {
		AnchorPane root = null;
		try {
			//    		root = FXMLLoader.load(getClass().getResource("/pidev/smartStart/GUI/"+gui));
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(AjouterDemandeController.class.getResource("/views/offres/demande"+gui));
			root = loader.load();
			AjouterDemandeController ajouterDemandeController = loader.getController();
			ajouterDemandeController.intitModifierDemandeAtt(demande);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		listeDemandeAnchor.getChildren().setAll(root);
	}
	


}
