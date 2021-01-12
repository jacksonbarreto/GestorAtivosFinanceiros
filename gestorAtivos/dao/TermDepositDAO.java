package dao;

import model.TermDeposit;

import javax.persistence.EntityManager;
import java.util.List;

import static model.LogSystem.registerOccurrence;

public class TermDepositDAO {

    public TermDeposit save(TermDeposit termDeposit) {
        EntityManager em = new ConnectionFactory().getConnection();
        try {
            em.getTransaction().begin();
            if (termDeposit.getId() == null) {
                em.persist(termDeposit);
            } else {
                em.merge(termDeposit);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            registerOccurrence(e.getMessage() + " in save TermDepositDAO");
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return termDeposit;
    }

    public TermDeposit findById(Long id) {
        EntityManager em = new ConnectionFactory().getConnection();
        TermDeposit termDeposit = null;
        try {
            termDeposit = em.find(TermDeposit.class, id);
        } catch (Exception e) {
            registerOccurrence(e.getMessage() + " in findById TermDepositDAO");
        } finally {
            em.close();
        }
        return termDeposit;
    }

    public List<TermDeposit> findAll() {
        EntityManager em = new ConnectionFactory().getConnection();
        List<TermDeposit> termDeposits = null;
        try {
            termDeposits = em.createQuery("from TermDeposit", TermDeposit.class).getResultList();
        } catch (Exception e) {
            registerOccurrence(e.getMessage() + " in findAll TermDepositDAO");
        } finally {
            em.close();
        }
        return termDeposits;
    }

    public TermDeposit remove(Long id){
        EntityManager em = new ConnectionFactory().getConnection();
        TermDeposit termDeposit = null;
        try {
            termDeposit = em.find(TermDeposit.class, id);
            em.getTransaction().begin();
            em.remove(termDeposit);
            em.getTransaction().commit();
        }catch (Exception e){
            registerOccurrence(e.getMessage() + " in remove TermDepositDAO");
            em.getTransaction().rollback();
        }finally {
            em.close();
        }
        return termDeposit;
    }
}
