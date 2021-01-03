package dao;

import model.TermDeposit;

import javax.persistence.EntityManager;

public class TermDepositDao {

    public TermDeposit save(TermDeposit termDeposit){
        EntityManager em = new ConnectionFactory().getConnection();
        try {
            em.getTransaction().begin();
            em.persist(termDeposit);
            em.getTransaction().commit();
        }catch (Exception e){
            System.out.println(e.getMessage());
            em.getTransaction().rollback();
        }finally {
            em.close();
        }
        return termDeposit;
    }
}
