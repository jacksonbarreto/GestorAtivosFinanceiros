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
            System.out.println(e.getMessage());
            em.getTransaction().rollback();
        }finally {
            em.close();
        }
        return rentalProperty;
    }
}
