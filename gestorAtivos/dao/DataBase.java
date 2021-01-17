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

    /**
     * Method that retrieves a collection of users who have the requested username.
     *
     * @param username username to be searched.
     * @return Collection of users with the searched username.
     */
    public static List<User> findUserByUsername(String username) {
        List<User> usersFound = new ArrayList<>();
        for (User user : users) {
            if (user.getUsername().equals(username))
                usersFound.add(user);
        }
        return usersFound;
    }

    /**
     * Method that finds a bank by its name.
     *
     * @param name bank name to be found.
     * @return Bank that matches the name searched.
     */
    public static Bank findBankByName(String name) {
        for (Bank bank : banks) {
            if (bank.getName().equals(name))
                return bank;
        }
        return null;
    }

    /**
     * Method that keeps all application data in file.
     */
    public static void saveAll() {
        record(users, usersFile);
        record(banks, banksFile);
    }

    /**
     * Method that loads term deposits in their respective bank.
     */
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

    /**
     * Method that writes an object to a specified file.
     *
     * @param objectToRecord  Object to be persisted.
     * @param destinationFile File path where the object is to be persisted.
     */
    public static void record(Object objectToRecord, String destinationFile) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(destinationFile))) {
            oos.writeObject(objectToRecord);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method that retrieves an object from a file.
     *
     * @param sourceFile File path where the object is saved.
     * @return Object retrieved from the file.
     */
    public static Object loadDataToMemory(String sourceFile) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(sourceFile))) {
            return ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


}