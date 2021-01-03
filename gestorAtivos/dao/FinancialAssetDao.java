package dao;

import model.FinancialAsset;

import javax.persistence.EntityManager;

public class FinancialAssetDao {

    public FinancialAsset save(FinancialAsset financialAsset){
        EntityManager em = new ConnectionFactory().getConnection();
        try {
            em.getTransaction().begin();
            em.persist(financialAsset);
            em.getTransaction().commit();
        }catch (Exception e){
            System.out.println(e.getMessage());
            em.getTransaction().rollback();
        }finally {
            em.close();
        }
        return financialAsset;
    }
}
