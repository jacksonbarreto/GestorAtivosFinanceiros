package dao;

import model.RentalProperty;

import javax.persistence.EntityManager;

public class RentalPropertyDao {

    public RentalProperty save(RentalProperty rentalProperty){
        EntityManager em = new ConnectionFactory().getConnection();
        try {
            em.getTransaction().begin();
            em.persist(rentalProperty);
            em.getTransaction().commit();
        }catch (Exception e){
            System.out.println(e.getMessage());
            em.getTransaction().rollback();
        }finally {
            em.close();
        }
        return rentalProperty;
    }
}
