package dao;

import model.Bank;

import javax.persistence.EntityManager;
import java.util.List;

import static model.LogSystem.registerOccurrence;

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
            registerOccurrence(e.getMessage() + " in save within BankDAO");
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
            registerOccurrence(e.getMessage() + " in findById within BankDAO");
        } finally {
            em.close();
        }
        return bank;
    }

    public List<Bank> findAll() {
        EntityManager em = new ConnectionFactory().getConnection();
        List<Bank> banks = null;
        try {
            banks = em.createQuery("from Bank", Bank.class).getResultList();
        } catch (Exception e) {
            registerOccurrence(e.getMessage() + " in findAll within BankDAO");
        } finally {
            em.close();
        }
        return banks;
    }

    public Bank remove(Long id){
        EntityManager em = new ConnectionFactory().getConnection();
        Bank bank = null;
        try{
            bank = em.find(Bank.class,id);
            em.getTransaction().begin();
            em.remove(bank);
            em.getTransaction().commit();
        }catch (Exception e){
            registerOccurrence(e.getMessage() + " in remove within BankDAO");
            em.getTransaction().rollback();
        }finally {
            em.close();
        }
        return bank;
    }
}
