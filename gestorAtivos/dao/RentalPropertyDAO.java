package dao;

import model.RentalProperty;

import javax.persistence.EntityManager;
import java.util.List;

import static model.LogSystem.registerOccurrence;

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
            registerOccurrence(e.getMessage() + " in save within RentalPropertyDAO");
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
            registerOccurrence(e.getMessage() + " in save FindById RentalPropertyDAO");
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
            registerOccurrence(e.getMessage() + " in save findAll RentalPropertyDAO");
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
            registerOccurrence(e.getMessage() + " in save remove RentalPropertyDAO");
            em.getTransaction().rollback();
        }finally {
            em.close();
        }
        return rentalProperty;
    }
}
