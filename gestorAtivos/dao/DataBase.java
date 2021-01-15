package dao;

import model.Bank;
import model.FinancialAsset;
import model.TermDeposit;
import model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DataBase {
    public static final String usersFile = "userData.dat";
    public static final String banksFile = "banksData.dat";
    public static List<User> users;
    public static List<Bank> banks;

    public static List<User> findUserByUsername(String username) {
        List<User> usersFound = new ArrayList<>();
        for (User user : users) {
            if (user.getUsername().equals(username))
                usersFound.add(user);
        }
        return usersFound;
    }

    public static Bank findBankByName(String name) {
        for (Bank bank : banks) {
            if (bank.getName().equals(name))
                return bank;
        }
        return null;
    }

    public static void saveAll() {
        record(users, usersFile);
        record(banks, banksFile);
    }

    public static void loadBankDeposits() {
        for (Bank bank : banks) {
            bank.resetDepositList();
            for (User user : users) {
                for (FinancialAsset financialAsset : user.getFinancialAssets()) {
                    if (financialAsset instanceof TermDeposit && ((TermDeposit) financialAsset).getBank().equals(bank))
                        bank.addDeposit((TermDeposit) financialAsset);
                }
            }
        }
    }

    public static void record(Object objectToRecord, String destinationFile) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(destinationFile))) {
            oos.writeObject(objectToRecord);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object loadDataToMemory(String sourceFile) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(sourceFile))) {
            return ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


}
