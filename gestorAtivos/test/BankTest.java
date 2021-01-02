package test;


import model.Bank;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BankTest {

    @Test
    void attributeVerification(){
        Bank bank = new Bank("Millenium");
        bank.setId(2L);
        assertEquals("Millenium", bank.getName());
        assertEquals(2L, bank.getId());
    }


    @Test
    void getEquityInDeposits() {
    }

    @Test
    void testGetEquityInDeposits() {
    }

    @Test
    void getTotalInterestPaid() {
    }

    @Test
    void testGetTotalInterestPaid() {
    }

    @Test
    void getTermDeposits() {
    }

    @Test
    void getId() {
    }

    @Test
    void getName() {
    }

    @Test
    void setId() {
    }

    @Test
    void setTermDeposits() {
    }

    @Test
    void setName() {
    }

    @Test
    void testSetTermDeposits() {
    }

    @Test
    void testEquals() {
    }

    @Test
    void testHashCode() {
    }
}
