package view;

import dao.BankDAO;
import model.*;
import dao.UserDAO;

import java.math.BigDecimal;

import static model.UserType.ROOT;

public class testBank {

    public static void main(String[] args) {
        Bank bank = new Bank("Millenium");

        User user1 = new User("Carlos Caetano", "123456", ROOT);

        InvestmentFund investmentFund1 = new InvestmentFund(2,
                new BigDecimal("0.15"),
                "Fundo Petro4",
                new BigDecimal("1500.78"),
                new BigDecimal("0.12"));
        InvestmentFund investmentFund2 = new InvestmentFund(2,
                new BigDecimal("0.15"),
                "Fundo Petro4",
                new BigDecimal("1500.78"),
                new BigDecimal("0.12"));
        RentalProperty rentalProperty1 = new RentalProperty(5,
                new BigDecimal("0.15"),
                "Apartamento em Viana",
                new BigDecimal("158000"),
                new BigDecimal("450.70"),
                new BigDecimal("63.50"),
                new BigDecimal("28.90"),
                "Rua Nogueira de Melo, 10, 3º Direito");
        RentalProperty rentalProperty2 = new RentalProperty(12,
                new BigDecimal("0.20"),
                "Casa de Campo - Barcelos",
                new BigDecimal("450000"),
                new BigDecimal("750"),
                new BigDecimal("0"),
                new BigDecimal("35"),
                "Rua da Capela Grande, 95 - Barcelos");

        RentalProperty rentalProperty3 = new RentalProperty(5,
                new BigDecimal("0.15"),
                "Apartamento em Viana",
                new BigDecimal("158000"),
                new BigDecimal("450.70"),
                new BigDecimal("63.50"),
                new BigDecimal("28.90"),
                "Rua Nogueira de Melo, 10, 3º Direito");
        Bank bank1 = new Bank("Millenum");
        Bank bank2 = new Bank("BCP");
        TermDeposit termDeposit1 = new TermDeposit(5,
                new BigDecimal("0.15"),
                "Casa de Praia",
                new BigDecimal("5000"),
                new BigDecimal("0.24"),
                "049AB7859",
                bank1);
        TermDeposit termDeposit2 = new TermDeposit(2,
                new BigDecimal("0.15"),
                "Reforma em Alemanha",
                new BigDecimal("2000"),
                new BigDecimal("0.14"),
                "0Tx27884",
                bank1);
        TermDeposit termDeposit3 = new TermDeposit(5,
                new BigDecimal("0.15"),
                "Casa de Praia",
                new BigDecimal("5000"),
                new BigDecimal("0.24"),
                "049AB7859",
                bank1);
        UserDAO userDao = new UserDAO();
        user1 = userDao.save(user1);
        user1.addAssetFinancial(termDeposit1);
        user1.addAssetFinancial(termDeposit2);
        user1.addAssetFinancial(termDeposit3);
        user1.addAssetFinancial(investmentFund1);
        user1.addAssetFinancial(investmentFund2);
        user1.addAssetFinancial(rentalProperty1);
        user1.addAssetFinancial(rentalProperty2);
        user1.addAssetFinancial(rentalProperty3);



        user1 = userDao.save(user1);
        System.out.println(user1.getId());

        BankDAO bankDao = new BankDAO();
        /*
        System.out.println(bank.getId());
        bank = bankDao.save(bank);
        System.out.println(bank.getId());


        bank = bankDao.findById(7L);
        System.out.println(bank.getName());
        System.out.println(bank.getId());


        for (Bank b : bankDao.findAll()){
            System.out.println(b.getId() + " - " + b.getName());
        }
    */

    }
}
