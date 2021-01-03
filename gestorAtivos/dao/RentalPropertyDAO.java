package dao;

import model.RentalProperty;

import javax.persistence.EntityManager;

public class RentalPropertyDAO {

    public RentalProperty save(RentalProperty rentalProperty){
        EntityManager em = new ConnectionFactory().getConnection();
        try {
            em.getTransaction().begin();
            if (rentalProperty.getId() == null) {
                em.persist(rentalProperty);
            }else {
                em.merge(rentalProperty);
            }
            em.getTransaction().commit();
        }catch (Exception e){
            System.err.println(e.getMessage());
            em.getTransaction().rollback();
        }finally {
            em.close();
        }
        return rentalProperty;
    }
    public RentalProperty FindById(Long id){
        EntityManager em = new ConnectionFactory().getConnection();
        RentalProperty rentalProperty = null;
        try {
            rentalProperty = em.find(RentalProperty.class,id);
        }catch (Exception e){
            System.err.println(e.getMessage());
        }finally {
            em.close();
        }
        return rentalProperty;
    }
}
