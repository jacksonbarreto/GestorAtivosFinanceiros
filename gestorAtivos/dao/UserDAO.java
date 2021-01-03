package dao;

import model.User;

import javax.persistence.EntityManager;

public class UserDAO {

    public User save(User user){
        EntityManager em = new ConnectionFactory().getConnection();

        try {
            em.getTransaction().begin();
            if (user.getId() == null){
                em.persist(user);
            }else {
                em.merge(user);
            }
            em.getTransaction().commit();
        }catch (Exception e){
            System.out.println(e.getMessage());
            em.getTransaction().rollback();
        }finally {
            em.close();
        }
        return user;
    }
}
