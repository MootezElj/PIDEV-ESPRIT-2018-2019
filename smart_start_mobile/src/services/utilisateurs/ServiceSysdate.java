/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.utilisateurs;

//import crudclient.CrudClient;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.ConnectionUtil;

/**
 *
 * @author mahmo
 */
public class ServiceSysdate {
 
     public Date selectDate()
             
     {
         Date datee=null;
         try {       
         Statement ste = ConnectionUtil.getInstance().getConnection().createStatement();
         String req = "select sysdate()";
         ResultSet rs =ste.executeQuery(req);
           while (rs.next()) {
              Date datenow = rs.getDate(1);
              //id=result.getInt("id");
             datee=datenow;
               // list.add( new Personne (rs.getString"nom",rs.getString"prenom))
           }
            } catch (SQLException ex) {
             System.out.println(ex.toString());
        }
       
       return  datee;
       
       }
     }

