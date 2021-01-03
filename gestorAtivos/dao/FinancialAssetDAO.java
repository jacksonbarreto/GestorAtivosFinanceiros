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
            System.out.println(e.getMessage());
            em.getTransaction().rollback();
        }finally {
            em.close();
        }
        return financialAsset;
    }
}
