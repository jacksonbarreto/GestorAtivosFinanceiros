package dao;

import model.Bank;
import model.FinancialAsset;
import model.TermDeposit;
import model.User;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

public class DataBase {
    private static final String usersFile = "userData.dat";
    private static final String banksFile = "banksData.dat";
    public static final String logsFile = "logs.txt";
    public static Path source = Paths.get("data");
    public static final Path sourceLogs = Paths.get(source.toString(),"logsFile");
    public static final Path pathUser = Paths.get(source.toString(),usersFile);
    public static final Path pathBanks = Paths.get(source.toString(),banksFile);
    public static final Set<User> users = new HashSet<>();
    public static final Set<Bank> banks = new HashSet<>();


    public static int getTotalBanks(){
        return banks.size();
    }
    public static int getTotalUsers(){
        return users.size();
    }


    /**
     * Method that keeps all application data in file.
     */
    public static void saveAll() {
        record(users, pathUser);
        record(banks, pathBanks);
    }

    /**
     * Method to load all application data, from files to memory.
     */
    public static void initializesData() {
        banks.addAll((Set<Bank>) loadDataToMemory(pathBanks));
        users.addAll ((Set<User>) loadDataToMemory(pathUser));
        loadBankDeposits();
    }

    /**
     * Method that loads term deposits in their respective bank.
     */
    private static void loadBankDeposits() {
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
    private static void record(Object objectToRecord, Path destinationFile) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(destinationFile.toString()))) {
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
    private static Object loadDataToMemory(Path sourceFile) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(sourceFile.toString()))) {
            return ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


}
