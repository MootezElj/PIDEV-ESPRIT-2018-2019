package utils;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.util.Timer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception{
		Parent root = FXMLLoader.load(getClass().getResource("/views/utilisateurs/Authentification.fxml"));
		//Parent root = FXMLLoader.load(getClass().getResource("/views/planings/FXMLDocument.fxml"));
		//        Parent root = FXMLLoader.load(getClass().getResource("/views/gestionOffres/demande/Home.fxml"));
		primaryStage.setTitle("SmartStart");
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
      int width = gd.getDisplayMode().getWidth();
      int height = gd.getDisplayMode().getHeight();
		primaryStage.setScene(new Scene(root, width, height-50));
		primaryStage.setResizable(true);
		primaryStage.show();
	}

	public static void main(String[] args) {
		Timer t1 = new Timer();
//		t1.schedule(new SendNotif(), 0,60000);
		launch(args);
	}
}
