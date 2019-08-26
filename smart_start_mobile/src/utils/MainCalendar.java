package utils;



import java.util.Timer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *

 */
public class MainCalendar extends Application {

        
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/views/UserConnexion/Authentification.fxml"));
        
        // Set main window icon
        stage.getIcons().add(
        new Image( "/icons/app_icon.png" ));
        stage.setTitle("Smart start Calendar By DEEPDEV");
        
        // Maximize window at launch
        stage.setMaximized(true);
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
     
   
    }
  
   
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
            Timer t1 = new Timer();
        t1.schedule(new SendNotif(), 0,60000);
        launch(args);
    }
    
}
