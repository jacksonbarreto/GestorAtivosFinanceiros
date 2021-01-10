package dao;

import model.LogSystem;
import model.User;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;


import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

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

    public List<User> findByUsername(String username){
        EntityManager em = new ConnectionFactory().getConnection();
        List<User> users = null;
        try {
            TypedQuery<User> query = em.createQuery("SELECT u from User u where u.username = :username", User.class);
            users = query.setParameter("username",username).getResultList();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }


        return users;
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
