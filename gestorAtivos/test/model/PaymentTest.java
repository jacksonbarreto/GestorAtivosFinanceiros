package test.model;

import model.InvestmentFund;
import model.Payment;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentTest {
    InvestmentFund investmentFund1 = new InvestmentFund(2,
            new BigDecimal("0.15"),
            "Fundo Petro4",
            new BigDecimal("1500.78"),
            new BigDecimal("0.12"));

    @Test
    void testCreationComDateOfPaymentNull() {
        LocalDate dateNull = null;
        assertThrows(IllegalArgumentException.class, () -> new Payment(dateNull, new BigDecimal("50.87"), new BigDecimal("0.24")));
    }

    @Test
    void testCreationWithNumberNull() {
        BigDecimal numberNull = null;
        assertThrows(IllegalArgumentException.class, () -> new Payment(LocalDate.now(), numberNull, new BigDecimal("0.24")));
        assertThrows(IllegalArgumentException.class, () -> new Payment(LocalDate.now(), new BigDecimal("50.87"), numberNull));
    }


    @Test
    void testGetTaxDue() {
        BigDecimal taxNull = null;
        assertThrows(IllegalArgumentException.class, () -> investmentFund1.getPayments().get(0).getTaxDue(taxNull));
        assertEquals(0, investmentFund1.getPayments().get(0).getTaxDue(investmentFund1.getTax()).compareTo(new BigDecimal("27.01")));
    }

    @Test
    void testGetMonthlyProfitability() {
        assertEquals(0, investmentFund1.getPayments().get(0).getMonthlyProfitability().compareTo(new BigDecimal("0.12")));
    }

    @Test
    void testSetMonthlyProfitability() {
        BigDecimal monthlyProfitability = null;
        assertThrows(IllegalArgumentException.class, () -> investmentFund1.getPayments().get(0).setMonthlyProfitability(monthlyProfitability));
    }
}
