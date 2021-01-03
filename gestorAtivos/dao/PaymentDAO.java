package dao;

import model.Payment;

import javax.persistence.EntityManager;

public class PaymentDAO {

    public Payment save(Payment payment){
        EntityManager em = new ConnectionFactory().getConnection();
        try {
            em.getTransaction().begin();
            if (payment.getId() == null){
                em.persist(payment);
            }else {
                em.merge(payment);
            }
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
