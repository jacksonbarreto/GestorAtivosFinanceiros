package view;

import dao.BankDAO;
import model.Bank;
import model.User;

import static model.UserType.ROOT;

public class testBank {

    public static void main(String[] args) {
        Bank bank = new Bank("Millenium");
        User user1 = new User("Carlos Caetano", "123456", ROOT);

        //System.out.println(user1.getId());
        //UserDao userDao = new UserDao();
        //user1 = userDao.save(user1);
        //System.out.println(user1.getId());


        BankDAO bankDao = new BankDAO();
        System.out.println(bank.getId());
        bank = bankDao.save(bank);
        System.out.println(bank.getId());




    }
}
