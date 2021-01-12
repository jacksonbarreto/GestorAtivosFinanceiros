package dao;

import model.Bank;
import model.FinancialAsset;

import javax.persistence.EntityManager;
import java.util.List;

import static model.LogSystem.registerOccurrence;

public class FinancialAssetDAO {

    public FinancialAsset save(FinancialAsset financialAsset) {
        EntityManager em = new ConnectionFactory().getConnection();
        try {
            em.getTransaction().begin();
            if (financialAsset.getId() == null) {
                em.persist(financialAsset);
            } else {
                em.merge(financialAsset);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            registerOccurrence(e.getMessage() + " in save within FinancialAssetDAO");
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return financialAsset;
    }

    public FinancialAsset findById(Long id) {
        EntityManager em = new ConnectionFactory().getConnection();
        FinancialAsset financialAsset = null;
        try {
            financialAsset = em.find(FinancialAsset.class, id);
        } catch (Exception e) {
            registerOccurrence(e.getMessage() + " in findById within FinancialAssetDAO");
        } finally {
            em.close();
        }
        return financialAsset;
    }

    public List<FinancialAsset> findAll() {
        EntityManager em = new ConnectionFactory().getConnection();
        List<FinancialAsset> financialAssets = null;
        try {
            financialAssets = em.createQuery("from FinancialAsset", FinancialAsset.class).getResultList();
        } catch (Exception e) {
            registerOccurrence(e.getMessage() + " in findAll within FinancialAssetDAO");
        } finally {
            em.close();
        }
        return financialAssets;
    }

    public FinancialAsset remove(Long id){
        EntityManager em = new ConnectionFactory().getConnection();
        FinancialAsset financialAsset = null;
        try {
            financialAsset = em.find(FinancialAsset.class, id);
            em.getTransaction().begin();
            em.remove(financialAsset);
            em.getTransaction().commit();
        }catch (Exception e){
            registerOccurrence(e.getMessage() + " in remove within FinancialAssetDAO");
            em.getTransaction().rollback();
        }finally {
            em.close();
        }
        return financialAsset;
    }
}
