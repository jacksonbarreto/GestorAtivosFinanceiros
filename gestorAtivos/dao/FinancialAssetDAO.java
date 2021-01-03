package dao;

import model.FinancialAsset;

import javax.persistence.EntityManager;

public class FinancialAssetDAO {

    public FinancialAsset save(FinancialAsset financialAsset){
        EntityManager em = new ConnectionFactory().getConnection();
        try {
            em.getTransaction().begin();
            if (financialAsset.getId() == null){
                em.persist(financialAsset);
            }else {
                em.merge(financialAsset);
            }
            em.getTransaction().commit();
        }catch (Exception e){
            System.err.println(e.getMessage());
            em.getTransaction().rollback();
        }finally {
            em.close();
        }
        return financialAsset;
    }

    public FinancialAsset findById(Long id){
        EntityManager em = new ConnectionFactory().getConnection();
        FinancialAsset financialAsset = null;
        try {
            financialAsset = em.find(FinancialAsset.class, id);
        }catch (Exception e){
            System.err.println(e.getMessage());
        }finally {
            em.close();
        }
        return financialAsset;
    }
}
