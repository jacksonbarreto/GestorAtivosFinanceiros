package dao;

import model.Bank;

import static dao.DataBase.banks;

public class BankDAO {
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
    public static void saveBank(Bank bank) {
        banks.add(bank);
    }

    public static void deleteBank(Bank bank) {
        banks.remove(bank);
    }
}
