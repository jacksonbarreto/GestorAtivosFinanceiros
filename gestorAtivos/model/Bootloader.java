package model;

import java.util.List;

import static dao.DataBase.*;

public class Bootloader {

    /**
     * Method to load all application data, from files to memory.
     */
    private static void initializesData() {
        banks = (List<Bank>) loadDataToMemory(banksFile);
        users = (List<User>) loadDataToMemory(usersFile);
        loadBankDeposits();
    }

}
