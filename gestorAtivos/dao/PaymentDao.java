package dao;

import model.Payment;

import javax.persistence.EntityManager;

public class PaymentDao {

    public Payment save(Payment payment){
        EntityManager em = new ConnectionFactory().getConnection();
        try {
            em.getTransaction().begin();
            em.persist(payment);
            em.getTransaction().commit();
        }catch (Exception e){
            System.out.println(e.getMessage());
            em.getTransaction().rollback();
        }finally {
            em.close();
        }
        return payment;
    }
}
