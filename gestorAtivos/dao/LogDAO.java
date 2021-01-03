package dao;

import model.Log;

import javax.persistence.EntityManager;

public class LogDAO {

    public Log save(Log log){
        EntityManager em = new ConnectionFactory().getConnection();
        try {
            em.getTransaction().begin();
            if (log.getId() == null){
                em.persist(log);
            }else {
                em.merge(log);
            }
            em.getTransaction().commit();
        }catch (Exception e){
            System.err.println(e.getMessage());
            em.getTransaction().rollback();
        }finally {
            em.close();
        }
        return log;
    }

    public Log findById(Long id){
        EntityManager em = new ConnectionFactory().getConnection();
        Log log = null;
        try {
            log = em.find(Log.class, id);
        }catch (Exception e){
            System.err.println(e.getMessage());
        }finally {
            em.close();
        }
        return log;
    }
}
