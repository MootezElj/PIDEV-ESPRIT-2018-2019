package services.planings;

import entities.planings.User;

public final class UserSession {

    private static UserSession instance;

    private User user;

    private UserSession(User user) {
        this.user=user;
    }

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
