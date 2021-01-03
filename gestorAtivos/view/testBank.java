package view;

import dao.ConnectionFactory;
import dao.UserDao;
import model.Bank;
import model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static model.UserType.ROOT;

public class testBank {

    public static void main(String[] args) {
        Bank bank = new Bank("Millenium");
        User user1 = new User("Carlos Caetano", "123456", ROOT);

        System.out.println(user1.getId());
        UserDao userDao = new UserDao();
        user1 = userDao.save(user1);

        System.out.println(user1.getId());



    }
}
