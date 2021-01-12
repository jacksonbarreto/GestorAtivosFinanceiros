package dao;

import model.SystemOperation;

import javax.persistence.EntityManager;
import java.util.List;

import static model.LogSystem.registerOccurrence;

public class SystemOperationDAO {

    public SystemOperation save(SystemOperation systemOperation) {
        EntityManager em = new ConnectionFactory().getConnection();
        try {
            em.getTransaction().begin();
            if (systemOperation.getId() == null) {
                em.persist(systemOperation);
            } else {
                em.merge(systemOperation);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            registerOccurrence(e.getMessage() + " in save SystemOperationDAO");
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return systemOperation;
    }

    public SystemOperation findById(Long id) {
        EntityManager em = new ConnectionFactory().getConnection();
        SystemOperation systemOperation = null;
        try {
            systemOperation = em.find(SystemOperation.class, id);
        } catch (Exception e) {
            registerOccurrence(e.getMessage() + " in findById SystemOperationDAO");
        } finally {
            em.close();
        }
        return systemOperation;
    }

    public List<SystemOperation> findAll() {
        EntityManager em = new ConnectionFactory().getConnection();
        List<SystemOperation> systemOperations = null;
        try {
            systemOperations = em.createQuery("from SystemOperation", SystemOperation.class).getResultList();
        } catch (Exception e) {
            registerOccurrence(e.getMessage() + " in findAll SystemOperationDAO");
        } finally {
            em.close();
        }
        return systemOperations;
    }

    public SystemOperation remove(Long id) {
        EntityManager em = new ConnectionFactory().getConnection();
        SystemOperation systemOperation = null;
        try {
            systemOperation = em.find(SystemOperation.class, id);
            em.getTransaction().begin();
            em.remove(systemOperation);
            em.getTransaction().commit();
        } catch (Exception e) {
            registerOccurrence(e.getMessage() + " in remove SystemOperationDAO");
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return systemOperation;
    }
}
