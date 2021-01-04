package dao;

import model.RentalProperty;

import javax.persistence.EntityManager;
import java.util.List;

public class RentalPropertyDAO {

    public RentalProperty save(RentalProperty rentalProperty) {
        EntityManager em = new ConnectionFactory().getConnection();
        try {
            em.getTransaction().begin();
            if (rentalProperty.getId() == null) {
                em.persist(rentalProperty);
            } else {
                em.merge(rentalProperty);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return rentalProperty;
    }

    public RentalProperty FindById(Long id) {
        EntityManager em = new ConnectionFactory().getConnection();
        RentalProperty rentalProperty = null;
        try {
            rentalProperty = em.find(RentalProperty.class, id);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            em.close();
        }
        return rentalProperty;
    }

    public List<RentalProperty> findAll() {
        EntityManager em = new ConnectionFactory().getConnection();
        List<RentalProperty> rentalProperties = null;
        try {
            rentalProperties = em.createQuery("from RentalProperty", RentalProperty.class).getResultList();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            em.close();
        }
        return rentalProperties;
    }

    public RentalProperty remove(Long id){
        EntityManager em = new ConnectionFactory().getConnection();
        RentalProperty rentalProperty = null;
        try {
            rentalProperty = em.find(RentalProperty.class, id);
            em.getTransaction().begin();
            em.remove(rentalProperty);
            em.getTransaction().commit();
        }catch (Exception e){
            System.err.println(e.getMessage());
            em.getTransaction().rollback();
        }finally {
            em.close();
        }
        return rentalProperty;
    }
}