package dao;

import model.Payment;

import javax.persistence.EntityManager;
import java.util.List;

import static model.LogSystem.registerOccurrence;

public class PaymentDAO {

    public Payment save(Payment payment) {
        EntityManager em = new ConnectionFactory().getConnection();
        try {
            em.getTransaction().begin();
            if (payment.getId() == null) {
                em.persist(payment);
            } else {
                em.merge(payment);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            registerOccurrence(e.getMessage() + " in save within PaymentDAO");
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return payment;
    }

    public Payment findById(Long id) {
        EntityManager em = new ConnectionFactory().getConnection();
        Payment payment = null;
        try {
            payment = em.find(Payment.class, id);
        } catch (Exception e) {
            registerOccurrence(e.getMessage() + " in findById within PaymentDAO");
        } finally {
            em.close();
        }
        return payment;
    }

    public List<Payment> findAll() {
        EntityManager em = new ConnectionFactory().getConnection();
        List<Payment> payments = null;
        try {
            payments = em.createQuery("from Payment", Payment.class).getResultList();
        } catch (Exception e) {
            registerOccurrence(e.getMessage() + " in findAll within PaymentDAO");
        } finally {
            em.close();
        }
        return payments;
    }
    public Payment remove(Long id){
        EntityManager em = new ConnectionFactory().getConnection();
        Payment payment = null;
        try {
            payment = em.find(Payment.class, id);
            em.getTransaction().begin();
            em.remove(payment);
            em.getTransaction().commit();
        }catch (Exception e){
            registerOccurrence(e.getMessage() + " in remove within PaymentDAO");
            em.getTransaction().rollback();
        }finally {
            em.close();
        }
        return payment;
    }
    public void removeAll(List<Payment> payments){
        EntityManager em = new ConnectionFactory().getConnection();
        try {
            em.getTransaction().begin();
            for (Payment p : payments){
                em.remove(p);
            }
            em.getTransaction().commit();
        }catch (Exception e){
            registerOccurrence(e.getMessage() + " in removeAll within PaymentDAO");
            em.getTransaction().rollback();
        }finally {
            em.close();
        }
    }
}
