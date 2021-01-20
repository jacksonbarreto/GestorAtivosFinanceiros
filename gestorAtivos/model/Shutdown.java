package model;

import static dao.DataBase.saveAll;
import static model.Session.logout;

public class Shutdown {

    public static void shutdown(){
        logout();
        saveAll();
    }
}
