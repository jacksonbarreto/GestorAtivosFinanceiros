package dao;

import model.User;

import javax.persistence.EntityManager;
import java.util.List;

public class UserDAO {

    public User save(User user) {
        EntityManager em = new ConnectionFactory().getConnection();

        try {
            em.getTransaction().begin();
            if (user.getId() == null) {
                em.persist(user);
            } else {
                em.merge(user);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            System.err.println(e.getMessage());
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
            users = em.createQuery("from User").getResultList();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            em.close();
        }
        return users;
    }
}
