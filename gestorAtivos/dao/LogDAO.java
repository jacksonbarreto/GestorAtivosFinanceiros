package dao;

import model.Log;

import javax.persistence.EntityManager;
import java.util.List;

import static model.LogSystem.registerOccurrence;

public class LogDAO {

    public Log save(Log log) {
        EntityManager em = new ConnectionFactory().getConnection();
        try {
            em.getTransaction().begin();
            if (log.getId() == null) {
                em.persist(log);
            } else {
                em.merge(log);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            registerOccurrence(e.getMessage() + " in save within LogDAO");
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return log;
    }

    public Log findById(Long id) {
        EntityManager em = new ConnectionFactory().getConnection();
        Log log = null;
        try {
            log = em.find(Log.class, id);
        } catch (Exception e) {
            registerOccurrence(e.getMessage() + " in findById within LogDAO");
        } finally {
            em.close();
        }
        return log;
    }

    public List<Log> findAll() {
        EntityManager em = new ConnectionFactory().getConnection();
        List<Log> logs = null;
        try {
            logs = em.createQuery("from Log",Log.class).getResultList();
        } catch (Exception e) {
            registerOccurrence(e.getMessage() + " in findAll within LogDAO");
        } finally {
            em.close();
        }
        return logs;
    }
    public Log remove(Long id){
        EntityManager em = new ConnectionFactory().getConnection();
        Log log = null;
        try {
            log = em.find(Log.class, id);
            em.getTransaction().begin();
            em.remove(log);
            em.getTransaction().commit();
        }catch (Exception e){
            registerOccurrence(e.getMessage() + " in remove within LogDAO");
            em.getTransaction().rollback();
        }finally {
            em.close();
        }
        return log;
    }
}
