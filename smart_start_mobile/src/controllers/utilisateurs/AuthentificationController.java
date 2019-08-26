package controllers.utilisateurs;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import services.utilisateurs.UserService;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.StageStyle;

public class AuthentificationController implements Initializable {

    
    @FXML
    private BorderPane loginBorderPane;
    
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtPasswd;
    @FXML
    private Label lblWarning;

    UserService userService=null;

    public AuthentificationController() {


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userService=new UserService();
    }

    @FXML
    public void handleBtnInscription(MouseEvent event) {
        loadPage(event, "/views/utilisateurs/Inscription.fxml");
    }

    @FXML
    public void handleBtnAuth(MouseEvent event) throws SQLException {
        String username=txtUsername.getText();
        String passwd=txtPasswd.getText();
        switch (userService.authentification(username,passwd)){
            case 0: lblWarning.setText("Utilisateur n'existe pas."); break;
            case 1: lblWarning.setText("Ce compte n'est pas disponible pour le moment."); break;
            case 2: lblWarning.setText("Mot de passe est incorrect."); break;
            case 3: loadGui("DashboardClient.fxml"); break;                  
            case 4: loadGui("/base/baseFreelancer.fxml");break;
            case 5: loadGui("AdminDashboard.fxml"); break;
        }
    }
    
    @FXML
    public void handleBtnPasswordForgotten(MouseEvent event){
        loadPage(event,"/views/utilisateurs/PasswordRequest.fxml");
    }
   

  @FXML
    public void loadGui( String gui) {
    	 AnchorPane root = null;
    	try {
    		root = FXMLLoader.load(getClass().getResource("/views/"+gui));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	loginBorderPane.setCenter(root);
    }

   private void loadPage(MouseEvent event,String pageName){
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
