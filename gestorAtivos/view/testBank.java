package view;

import dao.*;
import model.*;

import java.math.BigDecimal;
import java.util.List;

import static model.UserType.ROOT;

public class testBank {


    public static void main(String[] args) {


        UserDAO userDao = new UserDAO();

        startDB();

        /*
        User carlos = userDao.findByUsername("carlos").get(0);
        //carlos.removeAssetFinancial(1158L);
        System.out.println("id pego: " + carlos.getFinancialAssets().get(1).getId());
        RentalProperty propertyAA = (RentalProperty) carlos.getFinancialAssets().get(1);
        propertyAA.changeRentAmount(new BigDecimal("155.22"));
        System.out.println("==================================");
        System.out.println(carlos);
        System.out.println("===================================");
        List<FinancialAsset> ativos = carlos.findFinancialAsset(AssetType.PROPERTY);
        System.out.println("Total de Imóveis (3): " + ativos.size());
        ativos = carlos.findFinancialAsset(AssetType.FOUND);
        System.out.println("Total de Fundos (2): " + ativos.size());
        for (FinancialAsset fa : ativos) {
            System.out.println("#########pagamentos do Ativo###########");
            for (Payment p : fa.getPayments()) {
                System.out.println("Id do PAgamento: " + p.getId());
            }
            System.out.println("########################");
        }
        ativos = carlos.findFinancialAsset(AssetType.DEPOSIT);
        System.out.println("Total de Depósitos (0): " + ativos.size());
        System.out.println("Total de Logs (6): " + carlos.getLogs().size());
        System.out.println("-----Logs-----------");
        for (Log l : carlos.getLogs()) {
            System.out.println(l);
        }
        System.out.println("------fimLog-----");
        for (FinancialAsset fa : ativos) {

            System.out.println(fa.getAssetType() + " - " + fa.getDesignation());
            System.out.println("----pagamentos--------");
            for (Payment p : fa.getPayments()) {
                System.out.println(p);
            }
            System.out.println("-----------------");
        }


        //userDao.save(carlos);

         */
        LogSystem.record();

    }

    public static void startDB() {
        User user1 = new User("carlos", "123456", ROOT);

        InvestmentFund investmentFund1 = new InvestmentFund(2,
                new BigDecimal("0.15"),
                "Fundo Eletrobras",
                new BigDecimal("6500.78"),
                new BigDecimal("0.07"));
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
                "Apartamento em Caminha",
                new BigDecimal("748000"),
                new BigDecimal("250.70"),
                new BigDecimal("83.50"),
                new BigDecimal("18.90"),
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
                "Casa de Campo",
                new BigDecimal("8000"),
                new BigDecimal("0.34"),
                "239AI/752",
                bank2);

        BankDAO bankDao = new BankDAO();
        UserDAO userDao = new UserDAO();

        bankDao.save(bank1);
        bankDao.save(bank2);
        user1.addAssetFinancial(termDeposit1);
        user1.addAssetFinancial(termDeposit2);
        user1.addAssetFinancial(termDeposit3);
        user1.addAssetFinancial(investmentFund1);
        user1.addAssetFinancial(investmentFund2);
        user1.addAssetFinancial(rentalProperty1);
        user1.addAssetFinancial(rentalProperty2);
        user1.addAssetFinancial(rentalProperty3);

        userDao.save(user1);

    }
}
