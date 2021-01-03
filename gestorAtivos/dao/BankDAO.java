package dao;

import model.Bank;
import javax.persistence.EntityManager;

public class BankDAO {

    public Bank save(Bank bank){
        EntityManager em = new ConnectionFactory().getConnection();
        try {
            em.getTransaction().begin();
            if (bank.getId() == null){
                em.persist(bank);
            }else {
                em.merge(bank);
            }
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
