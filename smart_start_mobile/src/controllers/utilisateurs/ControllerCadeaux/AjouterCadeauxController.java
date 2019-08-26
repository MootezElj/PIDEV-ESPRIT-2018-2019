/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.utilisateurs.ControllerCadeaux;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.GrayColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import  entities.utilisateurs.Boncadeaux;
import  entities.utilisateurs.Cadeaux;
import  entities.utilisateurs.Compte;
import  entities.utilisateurs.User;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import services.utilisateurs.ServiceSysdate;
import services.utilisateurs.UserService;

/**
 * FXML Controller class
 *
 * @author Abdelkader
 */
public class AjouterCadeauxController implements Initializable {
    
    @FXML
    private AnchorPane AnchorPaneAjouterCadeaux;
      @FXML
    private TableView<?> tableViewUtlisateur;

    @FXML
    private TableColumn<?, ?> tableColumnUsername;

    @FXML
    private TableColumn<?, ?> tableColumnNom;

    @FXML
    private TableColumn<?, ?> tableColumnPrenom;

    @FXML
    private JFXComboBox<?> comboBoxSelectionnerUtilisateur;

    @FXML
    private JFXTextField TXCadeaux;

    @FXML
    private JFXComboBox<?> comboBoxSelectionnerCategorieCadeaux;

    @FXML
    private JFXDatePicker datePickerAjoutCadeaux;

    @FXML
    private ImageView PartenaireCheck;

    @FXML
    private ImageView DateCheck;

    @FXML
    private ImageView PrixCheck;

    @FXML
    private ImageView TypeBonCheck;

    @FXML
    private ImageView quantiteCheck;

    @FXML
    private ImageView ValeurCheck;

    @FXML
    private ImageView BonPlanCheck;

    @FXML
    private JFXButton BtAnuller;

    @FXML
    private JFXButton buttonConfirmer;
    
   private static Font taille15B = new Font(Font.FontFamily.TIMES_ROMAN, 15,
            Font.BOLD);
    private static Font taille12NORMALRed = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.NORMAL, BaseColor.RED);
    private static Font taille12NORMA = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.NORMAL);
 
    
    @FXML
    void AfficherCategorieCadeaux(ActionEvent event) {

    }

    @FXML
    void createtableviewUtilisateur(ActionEvent event) {

    }

    @FXML
    void handleButtonAnnuler(ActionEvent event) throws IOException {
        AnchorPaneAjouterCadeaux.toBack();
         setNode(FXMLLoader.load(getClass().getResource("/views/utilisateurs/GestionCadeaux.fxml")));
    }

    @FXML
    void handleButtonConfirmer(ActionEvent event) {

    }

    @FXML
    void verfieDate(ActionEvent event) {

    }
 

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    
    
   
   
       
            private void setNode(Node node) {
        AnchorPaneAjouterCadeaux.getChildren().clear();
        AnchorPaneAjouterCadeaux.getChildren().add((Node) node);
        FadeTransition ft = new FadeTransition(Duration.seconds(1));//dure de la translation
        ft.setNode(node);
        ft.setFromValue(0.10);//dispartion 
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(true);
        ft.play();
    }
    }
    
 
