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
            System.out.println(e.getMessage());
            em.getTransaction().rollback();
        }finally {
            em.close();
        }
        return log;
    }
}
