package dao;

import model.User;

import java.util.ArrayList;
import java.util.List;

import static dao.DataBase.users;

public class UserDAO {

    /**
     * Method that retrieves a collection of users who have the requested username.
     *
     * @param username username to be searched.
     * @return Collection of users with the searched username.
     */
    public static List<User> findUserByUsername(String username) {
        List<User> usersFound = new ArrayList<>();
        for (User user : users) {
            if (user.getUsername().equals(username))
                usersFound.add(user);
        }
        return usersFound;
    }

    public static void saveUser(User user) {
        users.add(user);
    }

    public static void deleteUser(User user) {
        users.remove(user);
    }
}
