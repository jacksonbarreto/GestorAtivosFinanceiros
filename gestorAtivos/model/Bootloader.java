package model;

import java.io.IOException;
import java.nio.file.Files;

import static dao.BankDAO.saveBank;
import static dao.DataBase.*;
import static dao.UserDAO.saveUser;
import static model.UserType.ROOT;

public class Bootloader {

    public static void boot() {
        if (Files.notExists(pathUser) || Files.notExists(pathBanks) || Files.notExists(sourceLogs)) {
            try {
                Files.createDirectories(sourceLogs);
                setup();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            initializesData();
        }
    }



    private static void setup() {
        User root = new User("root", "root", ROOT);
        Bank bank1 = new Bank("Millennium BCP");
        Bank bank2 = new Bank("BPI");
        Bank bank3 = new Bank("Novo Banco");
        Bank bank4 = new Bank("Caixa Geral de Depósito");
        Bank bank5 = new Bank("Montepio");
        saveBank(bank1);
        saveBank(bank2);
        saveBank(bank3);
        saveBank(bank4);
        saveBank(bank5);
        saveUser(root);
        saveAll();
        initializesData();
    }


}
