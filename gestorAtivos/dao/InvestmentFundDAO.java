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
            System.out.println(e.getMessage());
            em.getTransaction().rollback();
        }finally {
            em.close();
        }
        return investmentFund;
    }
}
