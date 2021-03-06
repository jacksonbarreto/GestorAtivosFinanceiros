package model;


import java.util.List;

import static dao.UserDAO.findUserByUsername;
import static model.Utilities.getHashedPassword;

public class Login {

    public static User userIsValid(String username, String password) {
        User user = null;
        List<User> users = null;

        users = findUserByUsername(username);
        if (!users.isEmpty()) {
            for (User us : users) {
                String passwordInputHash = getHashedPassword(password, us.getSalt());
                if (us.getPassword().equals(passwordInputHash)) {
                    user = us;
                    return user;
                }
            }
        }
        return user;
    }
}
