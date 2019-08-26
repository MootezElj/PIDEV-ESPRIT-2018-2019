/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.offres.user;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import dao.offres.user.UserDao;
import entities.offres.User;
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
    private TableView<User> t1;
    @FXML
    private TableColumn<User, Integer> Id;
    @FXML
    private TableColumn<User, String> UserName;
    @FXML
    private TableColumn<User, String> Mail;
    @FXML
    private TableColumn<User, Integer> Enabled;
    @FXML
    private TableColumn<User, String> Canonical;
    @FXML
    private TableColumn<User, String> Salt;
    @FXML
    private TableColumn<User, String> Password;
    @FXML
    private TableColumn<User, String> Role;
    @FXML
    private TableColumn<User, String> Bio;
    @FXML
    private TableColumn<User, String> FirstName;
    @FXML
    private TableColumn<User, String> LastName;
    @FXML
    private TableColumn<User, String> DateNaiss;
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
    private TextField txtrole;
    @FXML
    private TextField txtbio;
    @FXML
    private TextField txtfirstname;
    @FXML
    private TextField txtlastname;
    @FXML
    private DatePicker txtdatenaiss;
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
                .observableArrayList("Tous", "freelancer", "Embaucheur", "Admin");
        Choice.setValue("Tous");
        Choice.setItems(Choices);

        try {

            listUser();
        } catch (SQLException ex) {
            Logger.getLogger(ModifUserController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    UserDao Udao = new UserDao();
    //User Competance = new User();
    User user = new User();

    @FXML
    public void back(MouseEvent event) throws SQLException {

        loadPage(event, "/views/AdminDashboard.fxml");

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
        ObservableList<User> U = FXCollections.observableArrayList();
        U.addAll(Udao.selectAll());

        Id.setCellValueFactory(new PropertyValueFactory<>("id"));
        UserName.setCellValueFactory(new PropertyValueFactory<>("Username"));
        Mail.setCellValueFactory(new PropertyValueFactory<>("Email"));
        Enabled.setCellValueFactory(new PropertyValueFactory<>("Enabled"));
        Canonical.setCellValueFactory(new PropertyValueFactory<>("UsernameCanonical"));
        Salt.setCellValueFactory(new PropertyValueFactory<>("Salt"));
        Password.setCellValueFactory(new PropertyValueFactory<>("Password"));
        Role.setCellValueFactory(new PropertyValueFactory<>("Role"));
        Bio.setCellValueFactory(new PropertyValueFactory<>("Bio"));
        FirstName.setCellValueFactory(new PropertyValueFactory<>("FirstName"));
        LastName.setCellValueFactory(new PropertyValueFactory<>("LastName"));
        DateNaiss.setCellValueFactory(new PropertyValueFactory<>("DateNaiss"));
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
            ObservableList<User> r = FXCollections.observableArrayList();

            r.addAll(Udao.selectRole(s));

            Id.setCellValueFactory(new PropertyValueFactory<>("id"));
            UserName.setCellValueFactory(new PropertyValueFactory<>("Username"));
            Mail.setCellValueFactory(new PropertyValueFactory<>("Email"));
            Enabled.setCellValueFactory(new PropertyValueFactory<>("Enabled"));
            Canonical.setCellValueFactory(new PropertyValueFactory<>("UsernameCanonical"));
            Salt.setCellValueFactory(new PropertyValueFactory<>("Salt"));
            Password.setCellValueFactory(new PropertyValueFactory<>("Password"));
            Role.setCellValueFactory(new PropertyValueFactory<>("Role"));
            Bio.setCellValueFactory(new PropertyValueFactory<>("Bio"));
            FirstName.setCellValueFactory(new PropertyValueFactory<>("FirstName"));
            LastName.setCellValueFactory(new PropertyValueFactory<>("LastName"));
            DateNaiss.setCellValueFactory(new PropertyValueFactory<>("DateNaiss"));
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
             SendMail.sendMail(user.getEmail(), "Account is disabled", "Your account is disabled");
             JOptionPane.showMessageDialog(null, "Account disabled , mail sent successfuly");
        }else 
        {
            SendMail.sendMail(user.getEmail(), "Account is enabled", "Your account is enabled"); 
            JOptionPane.showMessageDialog(null, "Account enabled , mail sent successfuly");
           
        }
            
        /*  t1.getItems().clear();
        t1.setItems(U);
        RechercheUser();*/

    }

    @FXML
    public void show(MouseEvent event) throws SQLException {
        user = t1.getSelectionModel().getSelectedItem();
        txtid.setText("" + user.getId());
        txtusername.setText(user.getUsername());
        txtmail.setText(user.getEmail());
        //txtenabled.setText(""+user.isEnabled());
        txtcanonical.setText(user.getUsernameCanonical());
        txtsalt.setText(user.getSalt());
        //txtpassword.setText(user.getPassword());
        txtrole.setText(user.getRoleJv());
        txtfirstname.setText(user.getFirstName());
        txtlastname.setText(user.getLastName());

        java.sql.Date d = (java.sql.Date) user.getDateNaissance();
        txtdatenaiss.setValue(d.toLocalDate());

        /*  t1.getItems().clear();
        t1.setItems(U);
        RechercheUser();*/
    }

    @FXML
    public void update(MouseEvent event) throws SQLException {
        User userup = new User();
        userup.setId(Integer.parseInt(txtid.getText()));
        userup.setFirstName(txtfirstname.getText());
        userup.setUsername(txtusername.getText());
        userup.setEmail(txtmail.getText());
        userup.setUsernameCanonical(txtcanonical.getText());
        userup.setDateNaissance(Date.valueOf(txtdatenaiss.getValue()));
        userup.setLastName(txtlastname.getText());
        userup.setRoleJv(txtrole.getText());
        userup.setSalt(txtsalt.getText());

        Udao.updated(userup);
        RechercheUser();

        txtid.clear();
        txtusername.clear();
        txtmail.clear();
        //txtenabled.setText(""+user.isEnabled());
        txtcanonical.clear();
        txtsalt.clear();
        //txtpassword.setText(user.getPassword());
        txtrole.clear();
        txtbio.clear();
        txtfirstname.clear();
        txtlastname.clear();
        txtdatenaiss.setValue(LocalDate.now());
        JOptionPane.showMessageDialog(null, "User updated");
        

    }

   /* @FXML
    private void StatAction(MouseEvent event) {
         loadPage(event, "/views/StatistiqueLangueFXML.fxml");
    }*/
    public void StatAction(MouseEvent event) throws SQLException, IOException{
     AnchorPane p =FXMLLoader.load(getClass().getResource("/views/StatistiqueLangueFXML.fxml"));
     dd.getChildren().setAll(p);
    }
    
    

}
