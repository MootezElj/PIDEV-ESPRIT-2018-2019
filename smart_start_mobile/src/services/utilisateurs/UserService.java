package services.utilisateurs;



import dao.utilisateurs.UserDao;
import entities.utilisateurs.Badge;
import entities.utilisateurs.Cadeaux;
import entities.utilisateurs.Categoris;
import entities.utilisateurs.Compte;
import entities.utilisateurs.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.mindrot.jbcrypt.BCrypt;
import utils.UserSession;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import utils.ConnectionUtil;

import static utils.SendMail.sendMail;

public class UserService {

    private UserDao dao;

    public UserService() {

        dao=new UserDao();
    }
/////  
    public int authentification (String username,String passwd) throws SQLException {
        List<User> userList=dao.selectBy("username",username);
        if  (userList.size() !=0){
            User user = userList.get(0);
            System.out.println(user);
            if(user.isEnabled()== true){
                if (checkPasswd(passwd,user.getPassword())){
                    UserSession userSession=UserSession.getInstace(user);
                    //System.out.println(user.getRoleJv());
                    if(user.getRoleJv().equals("Freelancer")){
                        
                        return 4;
                    }else if(user.getRoleJv().equals("Client")){
                     return 3;}
                    else return 5;
                }
                else return 2;
            }
            else return 1;
        }
        else {
            return 0;
        }

    }

    public void inscription( User user) throws SQLException {
        user.setLastLogin(new Date());
        user.setPasswordRequestedAt(new Date());
        user.setDateNaissance(new Date());
        user.setPassword(hashPasswd(user.getPlainPassword()));
        user.setEnabled(true);
        dao.add(user);
        UserSession userSession=UserSession.getInstace(user);
    }

    public int checkForUser(User user) throws SQLException {
        //Check if username already used
        if (dao.selectBy("username",user.getUsername()).size() != 0) return 0;
        //Check if email already used
        else if (dao.selectBy("email",user.getEmail()).size() != 0) return 1;
        //generate confirmation token
        else{
            user.setConfirmationToken(generateConfirmationToken());
            return 2;
        }
    }

    public void updateUserProfile(User user) throws SQLException {
        dao.update(user);
    }
     
            public double userProfileCompletion(User user){
                double counter=50;
                if( ! user.getCompetance().equals("")){
                    counter+=10;
                }
                return counter;
            }
     

    private String hashPasswd(String plainTextPassword){

        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }


    public boolean checkPasswd(String plainPassword, String hashedPassword) {
        return (BCrypt.checkpw(plainPassword, hashedPassword)) ? true : false;
    }

    private String generateConfirmationToken(){
        Random random=new Random();
        return  Integer.toString(random.ints(10000,(99998+1)).findFirst().getAsInt());
    }

    public String sendConfirmationMailToUser(User user){
        Runnable runnable =
                new Runnable(){
                    public void run(){
                        String emailAdress=user.getEmail();
                        String emailSubject="Confirmer votre compte SmartStarts";
                        String emailBody="Votre code de confirmation est :" + user.getConfirmationToken();
                        sendMail(emailAdress,emailSubject,emailBody);
                    }
                };
        Thread thread = new Thread(runnable);
        thread.start();
        return user.getConfirmationToken();
    }

    public boolean usernameIsAvailable(String username) throws SQLException {
        return (dao.selectBy("username",username).size() == 0) ? true : false;
    }

    public User sendPasswordRequestConfirmation(String email) throws SQLException {
        List<User> userList=dao.selectBy("email",email);
         User  user = new User();
         user = userList.get(0);
        if (userList.size() != 0){
           
            System.out.println("utilisateur "+user.getEmail());
            sendConfirmationMailToUser(user);
        }
        return user;
    }

    public void editUserPassword(User user) throws SQLException {
        user.setPassword(hashPasswd(user.getPlainPassword()));
        dao.updatePassword(user);
    }
    
       public User chercherUtilisateurByUsername(String s) {
        User user = null;
     
        PreparedStatement preparedStatement;
        try {
         preparedStatement = ConnectionUtil.getInstance().getConnection().prepareStatement("select * from fos_user where username =?");
            preparedStatement.setString(1, s);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                  user = new User(
                       
                        resultSet.getInt("id"),
                        resultSet.getString("username"),
                        resultSet.getString("username_canonical"),
                        resultSet.getString("email"),
                        resultSet.getString("email_canonical"),
                        resultSet.getString("password"),
                         
                        
                        
                        resultSet.getString("roleJv"),
                        resultSet.getString("FirstName"),
                        resultSet.getString("LastName"),
                        //resultSet.getString("phone"),
                        resultSet.getString("image_name"),
                        resultSet.getString("date_naissance"));
                    
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        if (user == null) {
            return null;
        }
        return user;
    }
       /////////////////////////////////////////////////////////////////////////
    public User chercherUtilisateurByid(Integer r) {
        User user = null;
        String req = "select * from fos_user where id =?";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = ConnectionUtil.getInstance().getConnection().prepareStatement(req);
            preparedStatement.setInt(1, r);
            ResultSet resultSet = preparedStatement.executeQuery();
        
            while (resultSet.next()) {
                user = new User( 
                        resultSet.getInt("id"),
                        resultSet.getString("username"),
                        resultSet.getString("username_canonical"),
                        resultSet.getString("email"),
                        resultSet.getString("email_canonical"),
                        resultSet.getString("password"),
                         
                        
                        
                        resultSet.getString("roleJv"),
                        resultSet.getString("nom"),
                        resultSet.getString("prenom"),
                        //resultSet.getString("phone"),
                        resultSet.getString("image_name"),
                        resultSet.getString("date_naissance"));
                        //resultSet.getString("date_inscription")
                        //resultSet.getString("genre"));
                    
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return user;
    }
    
    
    //////Partie Cadeaux
    
       public Cadeaux  chercherCadeauxById(int id) {
        String sql = "SELECT * FROM cadeaux WHERE id=?";
        
        Cadeaux  cadeaux = new Cadeaux();
        try {
            PreparedStatement stmt = ConnectionUtil.getInstance().getConnection().prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet Res = stmt.executeQuery();
            if (Res.next()) {
                
               
                cadeaux.setId(Res.getInt(1));
                cadeaux.setLibelle(Res.getString(2));
                cadeaux.setDescription(Res.getString(3));
                cadeaux.setPrix_reel(Res.getFloat(4));
                cadeaux.setValeur_point(Res.getFloat(5));
                cadeaux.setQuantite_initial(Res.getInt(6));
                cadeaux.setQuantite_actuel(Res.getInt(7));
                cadeaux.setImage(Res.getString(8));
                Categoris categoris=new Categoris();
                categoris.setId(Res.getInt(9));
                cadeaux.setCategorisCadeaux(categoris);
            
                
            }
        }       catch (SQLException ex) {

            System.out.println(ex.getMessage());
        }
        return cadeaux;
    }
       
       public Cadeaux  chercherCadeauxByLibelle(String Libelle) {
    
        
           Cadeaux  cadeaux = new Cadeaux();
        try {
            PreparedStatement stmt = ConnectionUtil.getInstance().getConnection().prepareStatement("SELECT * FROM cadeaux WHERE libelle=?");
            stmt.setString(1, Libelle);
            ResultSet Res = stmt.executeQuery();
            if (Res.next()) {
                
               
                cadeaux.setId(Res.getInt(1));
                cadeaux.setLibelle(Res.getString(2));
                cadeaux.setDescription(Res.getString(3));
                cadeaux.setPrix_reel(Res.getFloat(4));
                cadeaux.setValeur_point(Res.getFloat(5));
                cadeaux.setQuantite_initial(Res.getInt(6));
                cadeaux.setQuantite_actuel(Res.getInt(7));
                cadeaux.setImage(Res.getString(8));
                Categoris categoris=new Categoris();
                categoris.setId(Res.getInt(9));
                cadeaux.setCategorisCadeaux(categoris);
            
                
            }
        }       catch (SQLException ex) {

            System.out.println(ex.getMessage());
        }
        return cadeaux;
    }
       
   public List<String> afficherlisteMembreAPointMerciSuffisant(String r,float  quantite) {
        List<String> list = new ArrayList<String>();
        String req = "select f.username from fos_user f,compte c where c.id_user=f.id and roles =? and c.point_merci > ?";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = ConnectionUtil.getInstance().getConnection().prepareStatement(req);
            preparedStatement.setString(1, r);
             preparedStatement.setInt(2, Math.round(quantite));
            ResultSet resultSet = preparedStatement.executeQuery();
        
            while (resultSet.next()) {
             User   user = new User();
             
      
             user.setUsername( resultSet.getString("username"));
                       
                       
              
             list.add(user.getUsername());
                    
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }
 public Compte chercherUtilisateurByUsernameDansLecompte(String s) {
        Compte compte = null;
        Badge badge = new Badge();
        User user = new User();
        Cadeaux cadeaux = new Cadeaux();
 
         
   
        try {
        PreparedStatement preparedStatement = ConnectionUtil.getInstance().getConnection().prepareStatement("select c.* from fos_user f,compte c where c.id_user=f.id and f.username =? ");
            preparedStatement.setString(1, s);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                  compte = new Compte();
                       compte.setIdCompte(resultSet.getInt("id"));
                       
                       /*****************On Prend Le  badge et compte*************************/
                       badge=chercherBadgeById(resultSet.getInt("id_badge"));
                       cadeaux=chercherCadeauxById(resultSet.getInt("id_cadeau"));
                       user=chercherUtilisateurByid(resultSet.getInt("id_user"));
                       compte.setId_badge(badge);
                       compte.setId_cadeau(cadeaux);
                       compte.setId_user(user);
                  
                        compte.setPoint_merci(resultSet.getInt("point_merci")
                       );
                    
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        if (compte == null) {
            return null;
        }
        return compte;
    }
    /**
     * **************************Partie
     * badge***********************************
     */
     
      public Badge chercherBadgeById(int s) {
        Badge badge = null;
        String req = "select * from badge where id =?";
        PreparedStatement preparedStatement;
        try {
         preparedStatement = ConnectionUtil.getInstance().getConnection().prepareStatement(req);
            preparedStatement.setInt(1, s);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                  badge = new Badge(
                        resultSet.getInt("id"),
                        resultSet.getString("level")
                  );
                    
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        if (badge == null) {
            return null;
        }
        return badge;
    }
      
      
      public List<String> afficherlistePartenaire(String r) {
        List<String> list = new ArrayList<String>();
        String req = "select username from fos_user where roles =?";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = ConnectionUtil.getInstance().getConnection().prepareStatement(req);
            preparedStatement.setString(1, r);
            ResultSet resultSet = preparedStatement.executeQuery();
        
            while (resultSet.next()) {
             User   user = new User();
             
      
             user.setUsername( resultSet.getString("username"));
                       
                       
              
             list.add(user.getUsername());
                    
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

}
