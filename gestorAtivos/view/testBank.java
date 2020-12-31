package view;

import dao.ConnectionFactory;
import model.Bank;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class testBank {

    public static void main(String[] args) {
        Bank bank = new Bank("Millenium");


        EntityManager em = new ConnectionFactory().getConnection();
        em.getTransaction().begin();
        em.persist(bank);
        em.getTransaction().commit();
        em.close();

    }
}
