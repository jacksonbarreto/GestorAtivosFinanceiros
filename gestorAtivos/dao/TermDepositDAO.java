package dao;

import model.TermDeposit;

import javax.persistence.EntityManager;

public class TermDepositDAO {

    public TermDeposit save(TermDeposit termDeposit){
        EntityManager em = new ConnectionFactory().getConnection();
        try {
            em.getTransaction().begin();
            if (termDeposit.getId() == null) {
                em.persist(termDeposit);
            }else {
                em.merge(termDeposit);
            }
            em.getTransaction().commit();
        }catch (Exception e){
            System.err.println(e.getMessage());
            em.getTransaction().rollback();
        }finally {
            em.close();
        }
        return termDeposit;
    }

    public TermDeposit findById(Long id){
        EntityManager em = new ConnectionFactory().getConnection();
        TermDeposit termDeposit = null;
        try {
            termDeposit = em.find(TermDeposit.class, id);
        }catch (Exception e){
            System.err.println(e.getMessage());
        }finally {
            em.close();
        }
        return termDeposit;
    }
}
