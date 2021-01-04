package dao;

import model.Payment;

import javax.persistence.EntityManager;
import java.util.List;

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
            System.err.println(e.getMessage());
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
            System.err.println(e.getMessage());
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
            System.err.println(e.getMessage());
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
            System.err.println(e.getMessage());
            em.getTransaction().rollback();
        }finally {
            em.close();
        }
        return payment;
    }
}
