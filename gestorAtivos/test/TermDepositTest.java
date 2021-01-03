package test;

import model.Bank;
import model.TermDeposit;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class TermDepositTest {
    Bank bank1 = new Bank("Millenum");
    Bank bank2 = new Bank("BCP");
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
            bank1);

    @Test
    void equalsTest() {
        assertEquals(termDeposit3, termDeposit1);
        termDeposit1.setStartDate(LocalDate.now().plusMonths(1L));
        assertNotSame(termDeposit3, termDeposit1);
    }

    @Test
    void hashCodeTest() {
        assertEquals(termDeposit1.hashCode(), termDeposit3.hashCode());
        termDeposit1.setBank(bank2);
        assertNotEquals(termDeposit1.hashCode(), termDeposit3.hashCode());
    }

    @Test
    void compareToTest() {
        assertEquals(0, termDeposit3.compareTo(termDeposit1));
        termDeposit3.setDepositedAmount(new BigDecimal("6500.79"));
        assertEquals(-1, termDeposit1.compareTo(termDeposit3));
        assertEquals(1, termDeposit3.compareTo(termDeposit1));
    }

    @Test
    void setDurationTest() {
        assertThrows(IllegalArgumentException.class, () -> termDeposit1.setDuration(0));
        assertThrows(IllegalArgumentException.class, () -> termDeposit1.setDuration(-3));
        termDeposit1.setDuration(9);
        assertEquals(9, termDeposit1.getPayments().size());
        assertEquals(9, termDeposit1.getDuration());
    }

    @Test
    void createPaymentsTest() {
        termDeposit1.setDuration(2);
        assertEquals(2, termDeposit1.getPayments().size());
        assertEquals(LocalDate.now().plusMonths(2L), termDeposit1.getPayments().get(1).getDateOfPayment());
    }

    @Test
    void setDepositedAmountTest() {
        BigDecimal depositeAmountNull = null;
        termDeposit1.setDuration(3);
        termDeposit1.setDepositedAmount(new BigDecimal("1800"));
        assertEquals(0, termDeposit1.getGrossProfit().compareTo(new BigDecimal("110.17")));
        assertEquals(0, termDeposit1.getPayments().get(1).getMonthlyProfitability().compareTo(new BigDecimal("0.02")));
        assertThrows(IllegalArgumentException.class, () -> termDeposit1.setDepositedAmount(new BigDecimal("0")));
        assertThrows(IllegalArgumentException.class, () -> termDeposit1.setDepositedAmount(new BigDecimal("-1")));
        assertThrows(IllegalArgumentException.class, () -> termDeposit1.setDepositedAmount(depositeAmountNull));
    }

    @Test
    void setAnnualProfitabilityTest() {
        BigDecimal annualProfitability = null;
        termDeposit1.setDuration(3);
        termDeposit1.setAnnualProfitability(new BigDecimal("0.15"));
        assertEquals(0, termDeposit1.getGrossProfit().compareTo(new BigDecimal("189.85")));
        assertEquals(0, termDeposit1.getPayments().get(1).getMonthlyProfitability().compareTo(new BigDecimal("0.0125")));
        assertEquals(0, termDeposit1.getAnnualProfitability().compareTo(new BigDecimal("0.15")));
        assertThrows(IllegalArgumentException.class, () -> termDeposit1.setAnnualProfitability(annualProfitability));
    }

    @Test
    void setAccountTest() {
        String stringNull = null;
        assertThrows(IllegalArgumentException.class, () -> termDeposit1.setAccount(stringNull));
        assertThrows(IllegalArgumentException.class, () -> termDeposit1.setAccount(""));
        assertThrows(IllegalArgumentException.class, () -> termDeposit1.setAccount("0D,"));
        termDeposit1.setAccount("ALPHA371");
        assertEquals(termDeposit1.getAccount(), "ALPHA371");
    }

    @Test
    void setBankTest() {
        Bank bankNull = null;
        assertThrows(IllegalArgumentException.class, () -> termDeposit1.setBank(bankNull));
        termDeposit1.setBank(bank2);
        assertEquals(bank2, termDeposit1.getBank());
    }

    @Test
    void setStartDateTest() {
        LocalDate dateNull = null;
        assertThrows(IllegalArgumentException.class, () -> termDeposit1.setStartDate(dateNull));
    }

    @Test
    void creationWithAmountNull() {
        BigDecimal amountNull = null;
        assertThrows(IllegalArgumentException.class, () -> new TermDeposit(5,
                new BigDecimal("0.15"),
                "Casa de Praia",
                amountNull,
                new BigDecimal("0.24"),
                "adfgg",
                bank1));
    }

    @Test
    void creationAnnualProfitabilityNull() {
        BigDecimal amountNull = null;
        assertThrows(IllegalArgumentException.class, () -> new TermDeposit(5,
                new BigDecimal("0.15"),
                "Casa de Praia",
                new BigDecimal("19805"),
                amountNull,
                "adfgg",
                bank1));
    }

    @Test
    void CreationTest() {
        String stringNull = null;
        assertThrows(IllegalArgumentException.class, () -> new TermDeposit(5,
                new BigDecimal("0.15"),
                "Casa de Praia",
                new BigDecimal("5000"),
                new BigDecimal("0.24"),
                "",
                bank1));
        assertThrows(IllegalArgumentException.class, () -> new TermDeposit(5,
                new BigDecimal("0.15"),
                "Casa de Praia",
                new BigDecimal("5000"),
                new BigDecimal("0.24"),
                "x#P",
                bank1));
        assertThrows(IllegalArgumentException.class, () -> new TermDeposit(5,
                new BigDecimal("0.15"),
                "Casa de Praia",
                new BigDecimal("5000"),
                new BigDecimal("0.24"),
                stringNull,
                bank1));
        assertThrows(IllegalArgumentException.class, () -> new TermDeposit(5,
                new BigDecimal("0.15"),
                "Casa de Praia",
                new BigDecimal("00000"),
                new BigDecimal("0.24"),
                "049AB7859",
                bank1));
        assertThrows(IllegalArgumentException.class, () -> new TermDeposit(5,
                new BigDecimal("0.15"),
                "Casa de Praia",
                new BigDecimal("-80000"),
                new BigDecimal("0.24"),
                "049AB7859",
                bank1));
    }


}
