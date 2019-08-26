package controllers.template;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
public class TemplateFreelancerController {
	
    @FXML
    private AnchorPane contentAnchor;

    @FXML
    void onGestionCalendarBtn(ActionEvent event) {
    	loadPage("CalendarViews/FXMLDocument.fxml");
    }


    @FXML
    void onGestionProfileBtn(ActionEvent event) {
    	loadPage("DashboardFreelancer.fxml");

    }
    
    @FXML
    void onGestionOffresBtn(ActionEvent event) {
    	loadPage("offres/demande/Home.fxml");
    }

    @FXML
    void onGestionOutilsBtn(ActionEvent event) {
    	loadPage("gestionOutils/home.fxml");
    }


    @FXML
    void onGestionReclamationBtn(ActionEvent event) {
    	loadPage("gestionReclamations/home.fxml");
    }
    
    public void loadPage(String pageName){
    	AnchorPane root = null;
        try {
        	System.out.println("helooo");
            root = FXMLLoader.load(getClass().getResource("/views/"+pageName));
            
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
//        int width = gd.getDisplayMode().getWidth();
//        int height = gd.getDisplayMode().getHeight();
        contentAnchor.getChildren().setAll(root);
        
    }
    




}
