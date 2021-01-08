package view;

import dao.*;
import model.*;

import java.math.BigDecimal;
import java.util.List;

import static model.LogSystem.registerOccurrence;
import static model.UserType.ROOT;

public class testBank {

    public static void main(String[] args) {
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

        TermDepositDAO termDepositDAO = new TermDepositDAO();
        RentalPropertyDAO rentalPropertyDAO = new RentalPropertyDAO();
        InvestmentFundDAO investmentFundDAO = new InvestmentFundDAO();
        BankDAO bankDao = new BankDAO();
        UserDAO userDao = new UserDAO();
/*
        bank1 = bankDao.save(bank1);
        bank2 = bankDao.save(bank2);
        termDeposit1 = termDepositDAO.save(termDeposit1);
        termDeposit2 = termDepositDAO.save(termDeposit2);
        termDeposit3 = termDepositDAO.save(termDeposit3);
        rentalProperty1 = rentalPropertyDAO.save(rentalProperty1);
        rentalProperty2 = rentalPropertyDAO.save(rentalProperty2);
        rentalProperty3 = rentalPropertyDAO.save(rentalProperty3);
        investmentFund1 = investmentFundDAO.save(investmentFund1);
        investmentFund2 = investmentFundDAO.save(investmentFund2);

        System.out.println("deposito 1: "+termDeposit1.getId());

        user1.addAssetFinancial(termDeposit1);
        user1.addAssetFinancial(termDeposit2);
        user1.addAssetFinancial(termDeposit3);
        user1.addAssetFinancial(investmentFund1);
        user1.addAssetFinancial(investmentFund2);
        user1.addAssetFinancial(rentalProperty1);
        user1.addAssetFinancial(rentalProperty2);
        user1.addAssetFinancial(rentalProperty3);


        //registerOccurrence("e.getMessage()");
        user1 = userDao.save(user1);
        System.out.println(user1.getId());
*/
     BankDAO mileDao = new BankDAO();
     Bank millenium = mileDao.findById(410L);
     User carlos = userDao.findById(408L);
     //carlos.removeAssetFinancial(425L);
     System.out.println("==================================");
     System.out.println("Depositos milenium : "+millenium.getTermDeposits().size());
     System.out.println(carlos);
     System.out.println("===================================");
        List<FinancialAsset> ativos = carlos.findFinancialAsset(AssetType.DEPOSIT);
        System.out.println("Tamanho: " +ativos.size());
        for (FinancialAsset fa : ativos){

            System.out.println(fa.getAssetType() + " - " + fa.getDesignation());
            System.out.println("----pagamentos--------");
            for (Payment p : fa.getPayments()){
                System.out.println(p);
            }
            System.out.println("-----------------");
        }


        LogSystem.record();

    }
}
