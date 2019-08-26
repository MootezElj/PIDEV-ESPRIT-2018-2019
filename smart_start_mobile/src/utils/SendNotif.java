/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.Period;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

import entities.planings.Notification;
import entities.planings.NotificationType;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.util.Duration;
import services.planings.ServiceSysdate;

/**
 *
 * @author Mélék-kh
 */
public class SendNotif extends TimerTask {
  ConnexionUtil databaseHandler;
  
    @Override
    public void run() {
       
        ServiceSysdate sys = new ServiceSysdate();
        
           Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), (ActionEvent ev) -> {
                 
      try {
          String query = "SELECT * FROM events";

          Statement ste = ConnexionUtil.getHandler().getConn()
                  .createStatement();
           ResultSet rs = ste.executeQuery(query);
           
           while (rs.next())
      {
        //int id = rs.getInt("id");
        String EventDescription = rs.getString("EventDescription");
          String CalendarName = rs.getString("CalendarName");
          Date EventDate = rs.getDate("EventDate");
        int TermID = rs.getInt("TermID");
        
          LocalDate now = LocalDate.now();
            LocalDate sixDaysBehind = now.minusDays(6);
 
          Period period = Period.between(now, EventDate.toLocalDate());
            int diff = period.getDays();
            if(diff == 5 ){
                 String title = "Rappel évènements";
                String message = "il vous reste 5 jours pour la debut d'evnement:  "+EventDescription ;
            Notification tray = new Notification();
            tray.setTitle(title);
            tray.setMessage(message);
             tray.setNotificationType(NotificationType.NOTICE);
             tray.showAndWait(); 
           
 
            }
            else if (diff == 3 ){
             String title = "Rappel évènements";
                String message = "il vous reste 3 jours pour la debut d'evnement:  "+EventDescription ;
            Notification tray = new Notification();
            tray.setTitle(title);
            tray.setMessage(message);
             tray.setNotificationType(NotificationType.WARNING);
             tray.showAndWait(); 
            
            }
            
             else if (diff == 1 ){
             String title = "Rappel évènements";
                String message = "il vous reste un seul jour pour la debut d'evnement:  "+EventDescription ;
            Notification tray = new Notification();
            tray.setTitle(title);
            tray.setMessage(message);
             tray.setNotificationType(NotificationType.ERROR);
             tray.showAndWait(); 
            
            }
//
//          System.out.println("after  " + EventDate.after(sys.selectDate()) );
//          System.out.println("before  " + EventDate.before(sys.selectDate()) );
//        
      }
      } catch (SQLException ex) {
          Logger.getLogger(SendNotif.class.getName()).log(Level.SEVERE, null, ex);
      }
               
               
               
            
    })); 
           timeline.play();
        
  }
    
}
