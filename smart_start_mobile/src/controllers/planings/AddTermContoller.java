/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.planings;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

import entities.planings.Notification;
import entities.planings.NotificationType;
import entities.planings.User;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import services.planings.UserSession;
import utils.ConnexionUtil;

/**
 *
 * @author Mélék-kh
 */
public class AddTermContoller implements Initializable {
  User currentUser=null;
    private ListTermsController listController;

    private FXMLDocumentController mainController;

    ConnexionUtil databaseHandler = new ConnexionUtil();

    public void setMainController(FXMLDocumentController mainController) {
        this.mainController = mainController;
    }

    @FXML
    private Label topLabel;
    @FXML 
    private AnchorPane rootPane;

    // Text fields
    @FXML
    private JFXTextField TermName;

    // Buttons
    @FXML
    private JFXButton saveButton;
    @FXML
    private JFXButton cancelButton;

    // Date picker
    @FXML
    private JFXDatePicker TermStartDate;

    private double xOffset;
    private double yOffset;

      @FXML
    void exit(MouseEvent event) {
        // Close the window
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    void cancel(MouseEvent event) {
        // Close the window
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }
    

    @FXML
    void save(MouseEvent event) {

        DateTimeFormatter myFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
      

        if (TermName.getText().isEmpty() || TermStartDate.getValue() == null) {

            Alert alertMessage = new Alert(Alert.AlertType.ERROR);
            alertMessage.setHeaderText(null);
            alertMessage.setContentText("Completer tous les chmaps svp");
            alertMessage.showAndWait();
            return;
        }
          currentUser = UserSession.getInstace(new User()).getUser();
        String termdate = TermStartDate.getValue().format(myFormat);
        String termname = TermName.getText();
        String defaulltcolor = "255-255-255";
        
        
        
         String insertQuery = "INSERT INTO terms VALUES ("+"DEFAULT,"
                        + "'" + termname + "', '" + defaulltcolor + "','" + termdate +   "','"  + currentUser.getId() + "')";

        System.out.println("controller.AddTermContoller.save()" + insertQuery);
       
        
     //      String insertQuery = "INSERT INTO `terms` (`TermID`, `TermName`, `TermColor`, `TermStartDate`) VALUES (DEFAULT, 'TAF', 'FQ', '2019-04-11');";
        

      
if(   databaseHandler.executeAction(insertQuery)){
        System.out.println("save()");
        Alert alertMessage = new Alert(Alert.AlertType.INFORMATION);
        alertMessage.setHeaderText(null);
        alertMessage.setContentText("Treme ajoutee avec succees");
        alertMessage.showAndWait();
        String title = "Creation Terme";
        String message = "vous avez creer votre Treme avec succés en " + TermStartDate.getValue();
        Notification tray = new Notification();
        tray.setTitle(title);
        tray.setMessage(message);
        tray.setNotificationType(NotificationType.SUCCESS);
        tray.showAndWait();}

        else //if there is an error
        {
            Alert alertMessage = new Alert(Alert.AlertType.ERROR);
            alertMessage.setHeaderText(null);
            alertMessage.setContentText("Adding Treme Failed!\nThere is already a Treme with the same information");
            alertMessage.showAndWait();
        }
       

        // Close the window
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //*** Instantiate DBHandler object *******************
        databaseHandler = new ConnexionUtil();
        //****************************************************
      
        
        // ************* Everything below is for Draggable Window ********
        
        // Set up Mouse Dragging for the Event pop up window
        topLabel.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Stage stage = (Stage) rootPane.getScene().getWindow();
                xOffset = stage.getX() - event.getScreenX();
                yOffset = stage.getY() - event.getScreenY();
            }
        });
        // Set up Mouse Dragging for the Event pop up window
        topLabel.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Stage stage = (Stage) rootPane.getScene().getWindow();
                stage.setX(event.getScreenX() + xOffset);
                stage.setY(event.getScreenY() + yOffset);
            }
        });
        // Change cursor when hover over draggable area
        topLabel.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Stage stage = (Stage) rootPane.getScene().getWindow();
                Scene scene = stage.getScene();
                scene.setCursor(Cursor.HAND); //Change cursor to hand
            }
        });

        // Change cursor when hover over draggable area
        topLabel.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Stage stage = (Stage) rootPane.getScene().getWindow();
                Scene scene = stage.getScene();
                scene.setCursor(Cursor.DEFAULT); //Change cursor to hand
            }
        });
    }

    public void setListController(ListTermsController listController) {
        this.listController = listController;
    }

//    public void insert(MouseEvent event) {
//
//        String requete = " INSERT INTO terms (TermName,TermColor,TermStartDate) VALUES (?,?,?)";
//
//        try {
//            PreparedStatement pst = ConnexionUtil.getConn().prepareStatement(requete);
//            pst.setString(1, "hhhhhh");
//            pst.setString(2, "255-255-255");
//            pst.setString(3, "2019-10-02");
////            pst.setString(4, r.getDateR());
////            pst.setInt(5, r.getArchive());
////            pst.setInt(6, r.getCorbeille());
////            pst.setInt(7, r.getType_id());
////            pst.setInt(8, r.getEvent_id());
////            pst.setInt(9, r.getOrganisateur_id());
////            pst.setInt(10, r.getUser_id());
//
//            pst.executeUpdate();
//            System.err.println("reclamation Ajouté !");
//
//        } catch (SQLException ex) {
//            System.out.println("statement fail !");
//            System.err.println(ex.getMessage());
//
//        }
//
//    }

}
