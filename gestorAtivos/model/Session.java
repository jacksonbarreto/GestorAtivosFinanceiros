package model;


public class Session {
    private static Session INSTANCE;
    private User currentUser;

    private Session(){}

    private static Session getInstance(){
        if (INSTANCE == null) {
            synchronized (Session.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Session();
                }
            }
        }
        return INSTANCE;
    }

    public static void addUserInSession(User currentUser){
        getInstance().currentUser = currentUser;
    }

    public static void logout(){
        getInstance().currentUser = null;
    }
}
