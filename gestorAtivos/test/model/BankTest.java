package test.model;


import model.Bank;
import model.TermDeposit;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class BankTest {
    Bank bank1 = new Bank("Millenium");
    Bank bank2 = new Bank("BCP");
    Bank bank3 = new Bank("Millenium");

    TermDeposit termDeposit1 = new TermDeposit(5,
            new BigDecimal("0.15"),
            "Casa de Praia",
            new BigDecimal("5000"),
            new BigDecimal("0.24"),
            "049AB7859",
            bank1);
    TermDeposit termDeposit2 = new TermDeposit(2,
            new BigDecimal("0.15"),
            "Reforma em Alemanha",
            new BigDecimal("2000"),
            new BigDecimal("0.14"),
            "0Tx27884",
            bank1);
    TermDeposit termDeposit3 = new TermDeposit(5,
            new BigDecimal("0.15"),
            "Casa de Praia",
            new BigDecimal("5000"),
            new BigDecimal("0.24"),
            "049AB7859",
            bank3);

    @Test
    void attributeVerification() {
        Bank bank = new Bank("Millenium");
        assertEquals("Millenium", bank.getName());
    }

    @Test
    void nameShortCreationTest() {
        assertThrows(IllegalArgumentException.class, () -> new Bank("mn"));
    }

    @Test
    void nameEmptyCreationTest() {
        assertThrows(IllegalArgumentException.class, () -> new Bank(""));
    }

    @Test
    void nameNullCreationTest() {
        String nameNull = null;
        assertThrows(IllegalArgumentException.class, () -> new Bank(nameNull));
    }


    @Test
    void testGetEquityInDeposits() {
        assertEquals(0, bank1.getEquityInDeposits().compareTo(new BigDecimal("7000")));
        termDeposit1.changeStartDate(LocalDate.now().plusMonths(3L));
        assertEquals(0, bank1.getEquityInDeposits(LocalDate.now(), LocalDate.now().plusMonths(2L)).compareTo(new BigDecimal("2000")));
        assertEquals(0, bank3.getEquityInDeposits().compareTo(new BigDecimal("5000")));
    }

    @Test
    void testGetTermDeposits() {
        assertEquals(2, bank1.getTermDeposits().size());
        assertEquals(1, bank3.getTermDeposits().size());
    }

    @Test
    void testGetTotalInterestPaid() {
        assertEquals(0, bank1.getTotalInterestPaid().compareTo(new BigDecimal("567.34")));
        assertEquals(0, bank3.getTotalInterestPaid().compareTo(new BigDecimal("520.40")));
        assertEquals(0, bank3.getTotalInterestPaid(LocalDate.now().plusMonths(6L), LocalDate.now().plusMonths(8L)).compareTo(new BigDecimal("0")));
        assertEquals(0, bank3.getTotalInterestPaid(LocalDate.now(), LocalDate.now().plusMonths(1L)).compareTo(new BigDecimal("100")));
    }


    @Test
    void getName() {
        assertEquals("Millenium", bank1.getName());
    }


    @Test
    void setName() {
        String nameNull = null;
        assertThrows(IllegalArgumentException.class, () -> bank1.setName(nameNull));
        assertThrows(IllegalArgumentException.class, () -> bank1.setName(""));
        assertThrows(IllegalArgumentException.class, () -> bank1.setName("Bh"));
    }


    @Test
    void testEquals() {
        assertEquals(bank1, bank3);
        assertNotEquals(bank1, bank2);
    }

    @Test
    void testHashCode() {
        assertEquals(bank1.hashCode(), bank3.hashCode());
    }
}
