package utils;

import entities.utilisateurs.User;

public final class UserSession {

    private static UserSession instance;

    private User user;

    public UserSession(User user) {
        this.user=user;
    }
 
    
    public UserSession() {}

    public static UserSession getInstace(User user) {
        if(instance == null) {
            instance = new UserSession(user);
        }
        return instance;
    }

    public User getUser() {
        return user;
    }

    public void cleanUserSession() {
        instance=null;
    }
    
  

}
