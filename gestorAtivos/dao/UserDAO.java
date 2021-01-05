package dao;

import model.LogSystem;
import model.User;

import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import java.util.List;

import static model.LogSystem.*;

public class UserDAO {

    public User save(User user) {
        EntityManager em = new ConnectionFactory().getConnection();

        try {
            em.getTransaction().begin();
            if (user.getId() == null || user.getId() == 0) {
                em.persist(user);
            } else {
                em.merge(user);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            registerOccurrence(e.getMessage() + " in UserDAO");
            //System.err.println(e.getMessage());
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return user;
    }

    public User findById(Long id) {
        EntityManager em = new ConnectionFactory().getConnection();
        User user = null;
        try {
            user = em.find(User.class, id);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            em.close();
        }
        return user;
    }

    public List<User> findAll() {
        EntityManager em = new ConnectionFactory().getConnection();
        List<User> users = null;
        try {
            users = em.createQuery("from User", User.class).getResultList();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            em.close();
        }
        return users;
    }

    public User remove(Long id){
        EntityManager em = new ConnectionFactory().getConnection();
        User user = null;
        try {
            user = em.find(User.class, id);
            em.getTransaction().begin();
            em.remove(user);
            em.getTransaction().commit();
        }catch (Exception e){
            System.err.println(e.getMessage());
            em.getTransaction().rollback();
        }finally {
            em.close();
        }
        return user;
    }
}
