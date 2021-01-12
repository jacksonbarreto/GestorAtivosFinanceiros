package dao;

import model.InvestmentFund;

import javax.persistence.EntityManager;
import java.util.List;

import static model.LogSystem.registerOccurrence;

public class InvestmentFundDAO {

    public InvestmentFund save(InvestmentFund investmentFund) {
        EntityManager em = new ConnectionFactory().getConnection();
        try {
            em.getTransaction().begin();
            if (investmentFund.getId() == null) {
                em.persist(investmentFund);
            } else {
                em.merge(investmentFund);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            registerOccurrence(e.getMessage() + " in save within InvestmentFundDAO");
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return investmentFund;
    }

    public InvestmentFund findById(Long id) {
        EntityManager em = new ConnectionFactory().getConnection();
        InvestmentFund investmentFund = null;
        try {
            investmentFund = em.find(InvestmentFund.class, id);
        } catch (Exception e) {
            registerOccurrence(e.getMessage() + " in findById within InvestmentFundDAO");
        } finally {
            em.close();
        }
        return investmentFund;
    }

    public List<InvestmentFund> findAll() {
        EntityManager em = new ConnectionFactory().getConnection();
        List<InvestmentFund> investmentFunds = null;
        try {
            investmentFunds = em.createQuery("from InvestmentFund", InvestmentFund.class).getResultList();
        } catch (Exception e) {
            registerOccurrence(e.getMessage() + " in findAll within InvestmentFundDAO");
        } finally {
            em.close();
        }
        return investmentFunds;
    }

    public InvestmentFund remove(Long id){
        EntityManager em = new ConnectionFactory().getConnection();
        InvestmentFund investmentFund = null;
        try {
            investmentFund = em.find(InvestmentFund.class, id);
            em.getTransaction().begin();
            em.remove(investmentFund);
            em.getTransaction().commit();
        }catch (Exception e){
            registerOccurrence(e.getMessage() + " in remove within InvestmentFundDAO");
            em.getTransaction().rollback();
        }finally {
            em.close();
        }
        return investmentFund;
    }
}
