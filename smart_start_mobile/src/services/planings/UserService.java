package services.planings;





import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.mindrot.jbcrypt.BCrypt;

import dao.planings.UserDao;
import entities.planings.User;
import utils.ConnexionUtil;


public class UserService {

    private UserDao dao=new UserDao();


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
        System.out.println(user.toString());
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
                    @Override
			public void run(){
                        String emailAdress=user.getEmail();
                        String emailSubject="Confirmer votre compte SmartStarts";
                        String emailBody="Votre code de confirmation est :"+user.getConfirmationToken();
                       SendMail.sendMail(emailAdress,emailSubject,emailBody);
                    }

           };
        Thread thread = new Thread(runnable);
        thread.start();
        return user.getConfirmationToken();
    }

    public boolean usernameIsAvailable(String username) throws SQLException {
        return (dao.selectBy("username",username).size() == 0) ? true : false;
    }

    public User sendPasswordRequestConfirmation( User user) throws SQLException {
        List<User> userList=dao.selectBy("email",user.getEmail());
        if (userList.size() != 0){
            user=userList.get(0);
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
         preparedStatement = ConnexionUtil.getHandler().getConn()
                 .prepareStatement("select * from fos_user where username =?");
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
            preparedStatement = ConnexionUtil.getHandler().getConn()
                 .prepareStatement(req);
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
    
   
}
