/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.utilisateurs.PartageExperience;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
//import controllers.OutilsController.OutildetailController;
import entities.utilisateurs.offre_experience;
import eu.hansolo.enzo.notification.Notification;
import eu.hansolo.enzo.notification.NotificationBuilder;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import org.apache.commons.codec.binary.Hex;
import org.controlsfx.control.Rating;
import services.utilisateurs.PartageExperience;

/**
 * FXML Controller class
 *
 * @author SLIMEN
 */
public class AjoutPartageExperienceController implements Initializable {

    private static final Notification[] NOTIFICATIONS = {
        NotificationBuilder.create().title("Info").message("New Information").image(Notification.INFO_ICON).build(),
        NotificationBuilder.create().title("Warning").message("Attention, somethings wrong").image(Notification.WARNING_ICON).build(),
        NotificationBuilder.create().title("Success").message("Ajouter avec succée").image(Notification.SUCCESS_ICON).build(),
        NotificationBuilder.create().title("Error").message("Erreur ajout").image(Notification.ERROR_ICON).build()
    };

    private Notification.Notifier notifier;
    offre_experience exp = new offre_experience();
      @FXML
    private AnchorPane UserPane;

    @FXML
    private Pane partageexpbtn;

    @FXML
    private AnchorPane partageexppanes;

    @FXML
    private JFXTextField nompx;

    @FXML
    private JFXTextField descripex;
    FileChooser fileChooser;
    @FXML
    private JFXCheckBox administration;

    @FXML
    private Rating raiting;

    @FXML
    private JFXCheckBox experience;

    @FXML
    private JFXCheckBox attractivite;

    @FXML
    private JFXCheckBox security;

    @FXML
    private JFXCheckBox control;

    @FXML
    private JFXCheckBox originalite;

    @FXML
    private JFXCheckBox theme;

    @FXML
    private JFXCheckBox actualite;

    @FXML
    private JFXCheckBox vitesse;

    @FXML
    private JFXCheckBox recherche;

    @FXML
    private JFXButton btnajouterexp;

    @FXML
    private ImageView expimages;

    @FXML
    private JFXButton selectimg;
    
    PartageExperience pexp = new PartageExperience();
    private ObservableList<String> stationsList = FXCollections.observableArrayList();
//    @FXML
//    private AnchorPane partageexppanes;
//   
    private String imgname;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // TODO
        ObservableList RGList = FXCollections.observableList(pexp.ListRegion());
        ObservableList CATList = FXCollections.observableList(pexp.ListCategorie());
//        regionexp.setItems(RGList);
//        catexp.setItems(CATList);
    }

    public void BackHomes(MouseEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/utilisateurs/PartageExperience.fxml"));
        Parent root = loader.load();
//        vbox.getScene().setRoot(root);
        
    }

    private void closeApp(ActionEvent event) {

        // get a handle to the stage
//        Stage stage = (Stage) btnclose.getScene().getWindow();
//        // do what you have to do
//        stage.close();
   }

    public String upload(File file, String fileName) throws IOException {
        BufferedOutputStream stream = null;
        String globalPath = "C:\\wamp64\\www\\MalekLekher\\web\\images\\Upload\\";
        String localPath = "localhost:80/";
        /*fileName = file.getName();
        fileName=fileName.replace(" ", "_");*/
        try {
            Path p = file.toPath();

            byte[] bytes = Files.readAllBytes(p);

            File dir = new File(globalPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            // Create the file on server
            File serverFile = new File(dir.getAbsolutePath() + File.separator + fileName);
            stream = new BufferedOutputStream(new FileOutputStream(serverFile));
            stream.write(bytes);
            stream.close();
            return localPath + "/" + fileName;
        } catch (IOException ex) {
          //  Logger.getLogger(AjoutDemandeController.class.getName()).log(Level.SEVERE, null, ex);
            return "error2";

        }
    }

   
    @FXML
    public void addimage(ActionEvent event) throws MalformedURLException, NoSuchAlgorithmException, IOException {

        System.out.println("Load Image Button Pressed");
        fileChooser = new FileChooser();

        File file = fileChooser.showOpenDialog(partageexppanes.getScene().getWindow());
        if (file != null) {
            System.out.println("File Was Selected");
            URL url = file.toURI().toURL();
            String urlimg = url.getFile().replaceFirst("/", "");
            //  System.out.println("url = "+urlimg);

            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] urlmd5 = md.digest(urlimg.getBytes());
            final String result = new String(Hex.encodeHex(urlmd5)) + ".jpg";
            upload(file, result);
            imgname = result;

            //    System.out.println("url md5 = "+result);
            expimages.setImage(new Image(url.toExternalForm()));

        }
    }
    
    @FXML
    public void AjoutExperience() {

        exp.setNom(nompx.getText());
        exp.setDescription(descripex.getText());
//        exp.setAddrese(adresseexp.getText());
//        exp.setRegion_id(pexp.GetItemId("region", "nom", regionexp.getValue().toString()));
        exp.setUrl_image(imgname);
        exp.setAttractivite(attractivite.isSelected());
        exp.setRecherche(recherche.isSelected());
        exp.setTheme(theme.isSelected());
        exp.setActualite(actualite.isSelected());
        exp.setOriginalite(originalite.isSelected());
        exp.setExperience(experience.isSelected());
        exp.setVitesse(vitesse.isSelected());
        exp.setAdministration(administration.isSelected());
        exp.setControl(control.isSelected());
        exp.setSecurity(security.isSelected());
        //System.out.println("region id "+pexp.GetItemId("region", "nom", regionexp.getValue().toString()));
        //  System.out.println("cat id "+pexp.GetItemId("categorie", "libelle", catexp.getValue().toString()));
//        exp.setCatid(pexp.GetItemId("categorie", "libelle", catexp.getValue().toString()));

        exp.setRating((int) raiting.getRating());

        pexp.ajouterExperience(exp);
        notifier = Notification.Notifier.INSTANCE;
        notifier.notify(NOTIFICATIONS[2]);
         loadGui( "/views/outildetail.fxml",exp);
    }
    
    @FXML
    public void loadGui( String gui,offre_experience exp) {
    	 AnchorPane root = null;
    	try {
    		
    FXMLLoader loader = new FXMLLoader();
			loader.setLocation(DetailExperienceController.class.getResource("/views/utilisateurs/DetailExperience.fxml"));
			root = loader.load();
			DetailExperienceController detailExperienceController = loader.getController();
			detailExperienceController.initData(exp);	
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	UserPane.getChildren().setAll(root);
    }
    

}
