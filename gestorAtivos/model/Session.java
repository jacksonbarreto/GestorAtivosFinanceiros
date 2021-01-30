package model;


public class Session {
    private static Session INSTANCE;
    private User currentUser;

    private Session() {
    }

    private static Session getInstance() {
        if (INSTANCE == null) {
            synchronized (Session.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Session();
                }
            }
        }
        return INSTANCE;
    }

    private void setUser(User currentUser){
        this.currentUser = currentUser;
    }
    public static void addUserInSession(User currentUser) {
        if (currentUser == null)
            throw new IllegalArgumentException();
        getInstance().setUser(currentUser);
    }

    private User getUser(){
        return this.currentUser;
    }

    public static User getCurrentUser(){
        return getInstance().getUser();
    }

    public static void logout() {
        getInstance().currentUser = null;
    }
}
