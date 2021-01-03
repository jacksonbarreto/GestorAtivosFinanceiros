package dao;

import model.Log;

import javax.persistence.EntityManager;

public class LogDao {

    public Log save(Log log){
        EntityManager em = new ConnectionFactory().getConnection();
        try {
            em.getTransaction().begin();
            em.persist(log);
            em.getTransaction().commit();
        }catch (Exception e){
            System.out.println(e.getMessage());
            em.getTransaction().rollback();
        }finally {
            em.close();
        }
        return log;
    }
}
