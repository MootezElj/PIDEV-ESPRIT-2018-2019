/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.utilisateurs;

import entities.utilisateurs.User;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import utils.SendMail;

/**
 * FXML Controller class
 *
 * @author Bacem Aloui
 */
public class ModifUserController implements Initializable {

    @FXML
    private ChoiceBox Choice;
    @FXML
    private TableView<entities.utilisateurs.User> t1;
    @FXML
    private TableColumn<entities.utilisateurs.User, Integer> Id;
    @FXML
    private TableColumn<entities.utilisateurs.User, String> UserName;
    @FXML
    private TableColumn<entities.utilisateurs.User, String> Mail;
    @FXML
    private TableColumn<entities.utilisateurs.User, Integer> Enabled;
    @FXML
    private TableColumn<entities.utilisateurs.User, String> Canonical;
    @FXML
    private TableColumn<entities.utilisateurs.User, String> Salt;
    @FXML
    private TableColumn<entities.utilisateurs.User, String> Password;
    @FXML
    private TableColumn<entities.utilisateurs.User, String> RoleJv;
    @FXML
    private TableColumn<entities.utilisateurs.User, String> Competance;
    @FXML
    private TableColumn<entities.utilisateurs.User, String> FirstName;
    @FXML
    private TableColumn<entities.utilisateurs.User, String> LastName;
    @FXML
    private TableColumn<entities.utilisateurs.User, String> DateNaissance;
    @FXML
    private Button backbtn;
    @FXML
    private Button OK;
    @FXML
    private Button show;
    @FXML
    private TextField txtid;
    @FXML
    private Button Bannir;
    @FXML
    private TextField txtusername;
    @FXML
    private TextField txtmail;
    @FXML
    private TextField txtcanonical;
    @FXML
    private TextField txtsalt;
    @FXML
    private TextField txtrolejv;
    @FXML
    private TextField txtcompetance;
    @FXML
    private TextField txtfirstname;
    @FXML
    private TextField txtlastname;
    @FXML
    private DatePicker txtdatenaissance;
    @FXML
    private Button btnupdate;
    @FXML
    private Button stat;
     @FXML
    AnchorPane dd;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        ObservableList<String> Choices = FXCollections
                .observableArrayList("Tous", "Freelancer", "Client");
        Choice.setValue("Tous");
        Choice.setItems(Choices);

        try {

            listUser();
        } catch (SQLException ex) {
            Logger.getLogger(ModifUserController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    dao.utilisateurs.UserDao Udao = new dao.utilisateurs.UserDao();
    //entities.User Competance = new entities.User();
    entities.utilisateurs.User user = new entities.utilisateurs.User();

    @FXML
    public void back(MouseEvent event) throws SQLException {

        loadPage(event, "/views/utilisateurs/AdminDashboard.fxml");

    }

    private void loadPage(MouseEvent event, String pageName) {
        try {
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            Scene scene = new Scene((Parent) FXMLLoader.load(getClass().getResource(pageName)));
            stage.setScene(scene);
            stage.show();

        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void listUser() throws SQLException {
        ObservableList<entities.utilisateurs.User> U = FXCollections.observableArrayList();
        
        U.addAll(Udao.selectAll());
        

        //Id.setCellValueFactory(new PropertyValueFactory<>("id"));
        UserName.setCellValueFactory(new PropertyValueFactory<>("Username"));
        Mail.setCellValueFactory(new PropertyValueFactory<>("Email"));
        Enabled.setCellValueFactory(new PropertyValueFactory<>("Enabled"));
        Canonical.setCellValueFactory(new PropertyValueFactory<>("UsernameCanonical"));
        Salt.setCellValueFactory(new PropertyValueFactory<>("Salt"));
        //Password.setCellValueFactory(new PropertyValueFactory<>("Password"));
        RoleJv.setCellValueFactory(new PropertyValueFactory<>( "RoleJv"));
        Competance.setCellValueFactory(new PropertyValueFactory<>("Comeptance"));
        FirstName.setCellValueFactory(new PropertyValueFactory<>("FirstName"));
        LastName.setCellValueFactory(new PropertyValueFactory<>("LastName"));
        DateNaissance.setCellValueFactory(new PropertyValueFactory<>("DateNaissance"));
        t1.getItems().clear();
        t1.setItems(U);

    }

    @FXML
    public void RechercheUser() throws SQLException {

        String s = (String) Choice.getValue();
        System.out.println(s);
        if (s == "Tous") {
            listUser();
        } else {
            ObservableList<entities.utilisateurs.User> r = FXCollections.observableArrayList();

            r.addAll(Udao.selectRole(s));
            
            //Id.setCellValueFactory(new PropertyValueFactory<>("id"));
            UserName.setCellValueFactory(new PropertyValueFactory<>("Username"));
            Mail.setCellValueFactory(new PropertyValueFactory<>("Email"));
            Enabled.setCellValueFactory(new PropertyValueFactory<>("Enabled"));
            Canonical.setCellValueFactory(new PropertyValueFactory<>("UsernameCanonical"));
            Salt.setCellValueFactory(new PropertyValueFactory<>("Salt"));
            //Password.setCellValueFactory(new PropertyValueFactory<>("Password"));
            RoleJv.setCellValueFactory(new PropertyValueFactory<>("RoleJv"));
            Competance.setCellValueFactory(new PropertyValueFactory<>("Competance"));
            FirstName.setCellValueFactory(new PropertyValueFactory<>("FirstName"));
            LastName.setCellValueFactory(new PropertyValueFactory<>("LastName"));
            DateNaissance.setCellValueFactory(new PropertyValueFactory<>("DateNaissance"));
            t1.getItems().clear();
            t1.setItems(r);

        }

        // TODO
    }

    @FXML
    public void Banne(MouseEvent event) throws SQLException {
        user = t1.getSelectionModel().getSelectedItem();
        Udao.bannir(user.getId(), user.isEnabled());
        RechercheUser();
        if (user.isEnabled()){
             SendMail.sendMail(user.getEmail(), "Account is disabled", "Your Account SmartStart is disabled");
             JOptionPane.showMessageDialog(null, "Account disabled , mail sent successfuly");
        }else 
        {
            SendMail.sendMail(user.getEmail(), "Account is enabled", "Your Account SmartStart is enabled"); 
            JOptionPane.showMessageDialog(null, "Account enabled , mail sent successfuly");
           
        }
            
        /*  t1.getItems().clear();
        t1.setItems(U);
        RechercheUser();*/

    }

    @FXML
    public void show(MouseEvent event) throws SQLException {
        user = t1.getSelectionModel().getSelectedItem();
        //txtid.setText("" + user.getId());
        txtusername.setText(user.getUsername());
        txtmail.setText(user.getEmail());
        //txtenabled.setText(""+user.isEnabled());
        txtcanonical.setText(user.getUsernameCanonical());
        txtsalt.setText(user.getSalt());
        //txtpassword.setText(user.getPassword());
        txtrolejv.setText(user.getRoleJv());
//        txtcompetance.setText(user.getCompetance());
        txtfirstname.setText(user.getFirstName());
        txtlastname.setText(user.getLastName());

        java.sql.Date d = (java.sql.Date) user.getDateNaissance();
        txtdatenaissance.setValue(d.toLocalDate());

        /*  t1.getItems().clear();
        t1.setItems(U);
        RechercheUser();*/
    }

    

    
    
    

}
