package dao;

import model.Bank;

import javax.persistence.EntityManager;
import java.util.List;

public class BankDAO {

    public Bank save(Bank bank) {
        EntityManager em = new ConnectionFactory().getConnection();
        try {
            em.getTransaction().begin();
            if (bank.getId() == null) {
                em.persist(bank);
            } else {
                em.merge(bank);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return bank;
    }

    public Bank findById(Long id) {
        EntityManager em = new ConnectionFactory().getConnection();
        Bank bank = null;
        try {
            bank = em.find(Bank.class, id);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            em.close();
        }
        return bank;
    }

    public List<Bank> findAll() {
        EntityManager em = new ConnectionFactory().getConnection();
        List<Bank> banks = null;
        try {
            banks = em.createQuery("from Bank").getResultList();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            em.close();
        }
        return banks;
    }
}
