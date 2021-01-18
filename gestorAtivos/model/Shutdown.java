package model;

import static dao.DataBase.saveAll;

public class Shutdown {

    public static void shutdown(){
        saveAll();
    }
}
