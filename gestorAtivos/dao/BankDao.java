package dao;

import model.Bank;
import javax.persistence.EntityManager;

public class BankDao {

    public Bank save(Bank bank){
        EntityManager em = new ConnectionFactory().getConnection();
        try {
            em.getTransaction().begin();
            em.persist(bank);
            em.getTransaction().commit();
        }catch (Exception e){
            System.out.println(e.getMessage());
            em.getTransaction().rollback();
        }finally {
            em.close();
        }
        return bank;
    }
}
