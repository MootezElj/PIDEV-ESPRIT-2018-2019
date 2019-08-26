package services.offres.user;



import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.mindrot.jbcrypt.BCrypt;

import dao.offres.user.UserDao;
import entities.offres.User;
import utils.SendMail;
import utils.offre.UserSession;

public class UserService {

    private UserDao dao;

    public UserService() {

        dao=new UserDao();
    }

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
    	 System.out.println(user.getUsername());
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
        /*
    
    
            public double userProfileCompletion(User user){
                double counter=50;
                if( ! user.getBio().equals("")){
                    counter+=10;
                }
                return counter;
            }
        **/

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




}
