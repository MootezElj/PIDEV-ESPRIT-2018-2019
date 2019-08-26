package controllers.offres.projet;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

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
import services.offres.projet.ServiceProjet;

public class ListeOffresController implements Initializable {
//	@FXML
//    private BorderPane listOffresBorderPane;

    @FXML
    private AnchorPane projetPublieAnchor;

    @FXML
    private TableView<Projet> offresTableView;

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
    private TableColumn<Projet, Double> coutProjetC;

    @FXML
    private TableColumn<Projet, String> descriptionProjetC;
    

    
    
    
    @FXML
    private TableColumn<Projet, Void> voirDemandes;
    
    private ServiceProjet serviceProjet = new ServiceProjet();
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		nomProjetC.setCellValueFactory(new PropertyValueFactory<>("nomProjet"));
		titreProjetC.setCellValueFactory(new PropertyValueFactory<>("TitreProjet"));
		dateDebutProjetC.setCellValueFactory(new PropertyValueFactory<>("DateDebut"));
		dateFinProjetC.setCellValueFactory(new PropertyValueFactory<>("dateFin"));
		dateCreationProjetC.setCellValueFactory(new PropertyValueFactory<>("dateCreation"));
		coutProjetC.setCellValueFactory(new PropertyValueFactory<>("cout"));
		descriptionProjetC.setCellValueFactory(new PropertyValueFactory<>("description"));
		//charge la table avec les projets
		offresTableView.setItems(loadPublishedProjects());
		addListeDemandesProjetButtonToTable();
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
							loadListeDemandesProjetGui(projetPublieAnchor, "listeDemandesProjet.fxml", projet);
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
	
	

	//Charge la page AjouterProjet.fxml tout en la modifant pour etre capable de faire une MAJ
	public void loadListeDemandesProjetGui(AnchorPane anchor, String gui, Projet projet) {
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
		anchor.getChildren().setAll(root);
	}

}
