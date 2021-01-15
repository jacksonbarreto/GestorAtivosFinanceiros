package dao;

import model.Bank;
import model.User;

import java.util.ArrayList;
import java.util.List;

public class DataBase {
    public static final String usersFile = "userData.dat";
    public static final String banksFile = "banksData.dat";
    public static List<User> users;
    public static List<Bank> banks;

    public static List<User> findByUsername(String username) {
        List<User> usersFound = new ArrayList<>();
        for (User user : users) {
            if (user.getUsername().equals(username))
                usersFound.add(user);
        }
        return usersFound;
    }


}
