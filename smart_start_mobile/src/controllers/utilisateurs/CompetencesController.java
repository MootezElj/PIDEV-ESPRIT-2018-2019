package controllers.utilisateurs;

import entities.utilisateurs.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;
import services.utilisateurs.CompetenceUserService;
import utils.TableViewFormat;
import utils.UserSession;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class CompetencesController implements Initializable {

    @FXML
    TableView<TableViewFormat> tableCompetences;
    @FXML
    TableColumn<TableViewFormat, String> colLib,colId;
    @FXML
    TextField txtRecherche;
    

    @FXML
    Label lblCtrComp,lblUsername;

    User currentUser=null;
    CompetenceUserService competenceService=null;
    List<String> listCompetenses=null;
    ObservableList<TableViewFormat> userCompetencesList=null;

    public CompetencesController(){
        currentUser= UserSession.getInstace(new User()).getUser();
        competenceService=new CompetenceUserService();
        listCompetenses=new ArrayList<>();
        userCompetencesList= FXCollections.observableArrayList();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lblUsername.setText(currentUser.getUsername());
        try {
            listCompetenses=competenceService.getAllCompetences();
            System.out.println(listCompetenses);
            userCompetencesList=competenceService.getCompetencesByUser(currentUser);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        TextFields.bindAutoCompletion(txtRecherche,listCompetenses);
        colId.setCellValueFactory(new PropertyValueFactory<TableViewFormat,String>("idd"));
        colLib.setCellValueFactory(new PropertyValueFactory<TableViewFormat,String>("lib"));
        tableCompetences.setItems(userCompetencesList);
    }

    @FXML
    public void handleBtnMonProfil(MouseEvent event){
        if (currentUser.getRoleJv().equals("Freelance")){
            loadPage(event,"/views/utilisateurs/ProfileFreelancer.fxml");
        }
        else
            loadPage(event, "/views/utilisateurs/ProfileClient.fxml");
    }
    @FXML
    public void handleBtnAjouterCompetence() throws SQLException {
        String competence=txtRecherche.getText();
        if(listCompetenses.contains(competence)){
            if (userCompetencesList.stream().filter(element->element.getLib().equals(competence)).collect(Collectors.toList()).size() !=0 ){
                lblCtrComp.setText("La competence est deja utilisee");
            }
            else{
                lblCtrComp.setText("");
                userCompetencesList.add(competenceService.updateUserCompetence(competence,currentUser));
            }


        }
        else lblCtrComp.setText("La Competence n'est pas valide");

    }

    public void handleBtnSuppComp() throws SQLException {
        TableViewFormat data= new TableViewFormat();
        data=tableCompetences.getSelectionModel().getSelectedItem();
        if (data != null){
            TableViewFormat competence = competenceService.deleteUserCompetence(data.getLib(),currentUser);
            for (TableViewFormat element: userCompetencesList) {
                if (element.getLib().equals(competence.getLib())) userCompetencesList.remove(element);
            }
        }
        else
            System.out.println("nothing selected");
    }

    @FXML
    public void handleBtnLogout(MouseEvent event){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        //alert.setTitle("Deconnexion");
        alert.setHeaderText("Deconnexion");
        alert.setContentText("Vous allez se deconnecter, etes vous sure ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            UserSession.getInstace(currentUser).cleanUserSession();
            loadPage(event,"/views/utilisateurs/Authentification.fxml");
        }
    }
//    @FXML
//    public void handleBtnRetour(MouseEvent event){
//        
//    }
    
    @FXML
    void BackImageAction(MouseEvent event) {
        loadPage(event, "/views/utilisateurs/ProfileClient.fxml");
    }

    private void loadPage(MouseEvent event, String pageName){
        try {
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            Scene scene = new Scene((Parent) FXMLLoader.load(getClass().getResource(pageName)));
            stage.setScene(scene);
            stage.show();

        } catch (    IOException ex) {
            System.err.println(ex.getMessage());
        }
    }
}
