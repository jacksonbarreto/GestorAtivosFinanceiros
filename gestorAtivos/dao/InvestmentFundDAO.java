package dao;

import model.InvestmentFund;

import javax.persistence.EntityManager;

public class InvestmentFundDAO {

    public InvestmentFund save (InvestmentFund investmentFund){
        EntityManager em = new ConnectionFactory().getConnection();
        try {
            em.getTransaction().begin();
            if (investmentFund.getId() == null){
                em.persist(investmentFund);
            }else {
                em.merge(investmentFund);
            }
            em.getTransaction().commit();
        }catch (Exception e){
            System.err.println(e.getMessage());
            em.getTransaction().rollback();
        }finally {
            em.close();
        }
        return investmentFund;
    }

    public InvestmentFund findById(Long id){
        EntityManager em = new ConnectionFactory().getConnection();
        InvestmentFund investmentFund = null;
        try {
            investmentFund = em.find(InvestmentFund.class, id);
        }catch (Exception e){
            System.err.println(e.getMessage());
        }finally {
            em.close();
        }
        return investmentFund;
    }
}
