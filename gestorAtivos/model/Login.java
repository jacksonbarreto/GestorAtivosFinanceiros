package model;

import dao.UserDAO;


import java.util.List;

import static model.Utilities.getHashedPassword;

public class Login {

    public static User userIsValid(String username, String password) {
        User user = null;
        List<User> users = null;
        UserDAO userDAO = new UserDAO();

        users = userDAO.findByUsername(username);
        if (users != null) {
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
