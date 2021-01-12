package test.model;

import model.AssetType;
import model.InvestmentFund;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class InvestmentFundTest {
    InvestmentFund investmentFund1 = new InvestmentFund(2,
            new BigDecimal("0.15"),
            "Fundo Petro4",
            new BigDecimal("1500.78"),
            new BigDecimal("0.12"));
    InvestmentFund investmentFund2 = new InvestmentFund(2,
            new BigDecimal("0.15"),
            "Fundo Petro4",
            new BigDecimal("1500.78"),
            new BigDecimal("0.12"));

    @Test
    void equalsTest() {
        assertEquals(investmentFund2, investmentFund1);
        investmentFund2.changeStartDate(LocalDate.now().plusMonths(1L));
        assertNotSame(investmentFund1, investmentFund2);
    }

    @Test
    void hashCodeTest() {
        assertEquals(investmentFund1.hashCode(), investmentFund2.hashCode());
        investmentFund1.setMonthlyProfitability(new BigDecimal("0.13"));
        assertNotEquals(investmentFund1.hashCode(), investmentFund2.hashCode());
    }

    @Test
    void compareToTest() {
        assertEquals(0, investmentFund2.compareTo(investmentFund1));
        investmentFund2.changeAmountInvested(new BigDecimal("1500.79"));
        assertEquals(-1, investmentFund1.compareTo(investmentFund2));
        assertEquals(1, investmentFund2.compareTo(investmentFund1));
    }

    @Test
    void attributeVerification() {
        assertEquals(2, investmentFund1.getDuration());
        assertEquals(0, investmentFund1.getTax().compareTo(new BigDecimal("0.15")));
        assertEquals("Fundo Petro4", investmentFund1.getDesignation());
        assertEquals(0, investmentFund1.getAmountInvested().compareTo(new BigDecimal("1500.78")));
        assertEquals(0, investmentFund1.getMonthlyProfitability().compareTo(new BigDecimal("0.12")));
        assertEquals(LocalDate.now(), investmentFund1.getStartDate());
        assertEquals(AssetType.FOUND, investmentFund1.getAssetType());
        assertThrows(IllegalArgumentException.class, () -> investmentFund1.setDesignation(""));
        String string = null;
        assertThrows(IllegalArgumentException.class, () -> investmentFund1.setDesignation(string));
        investmentFund1.defineNewTax(new BigDecimal("0"));
        assertEquals(0, investmentFund1.getTax().compareTo(new BigDecimal("0")));
        assertThrows(IllegalArgumentException.class, () -> investmentFund1.defineNewTax(new BigDecimal("-234.98")));
    }

    @Test
    void amountInvestedNullTest() {
        BigDecimal amountInvestedNull = null;
        assertThrows(IllegalArgumentException.class, () -> new InvestmentFund(2,
                new BigDecimal("0.15"),
                "Fundo Petro4",
                amountInvestedNull,
                new BigDecimal("0.12")));
    }

    @Test
    void monthlyProfitabilityNullTest() {
        BigDecimal monthlyProfitabilityNull = null;
        assertThrows(IllegalArgumentException.class, () -> new InvestmentFund(2,
                new BigDecimal("0.15"),
                "Fundo Petro4",
                new BigDecimal("150000"),
                monthlyProfitabilityNull));
    }


    @Test
    void setmonthlyProfitabilityNullTest() {
        BigDecimal monthlyProfitabilityNull = null;
        assertThrows(IllegalArgumentException.class, () -> investmentFund1.setMonthlyProfitability(monthlyProfitabilityNull));
    }

    @Test
    void setStartDateTest() {
        LocalDate dateNull = null;
        assertThrows(IllegalArgumentException.class, () -> investmentFund1.changeStartDate(dateNull));
    }

    @Test
    void GrossProfitTest() {
        assertEquals(0, investmentFund1.getGrossProfit().compareTo(new BigDecimal("381.80")));
    }

    @Test
    void getNetProfitTest() {
        assertEquals(0, investmentFund1.getNetProfit().compareTo(new BigDecimal("324.53")));
    }

    @Test
    void getAverageMonthlyNetProfitTest() {
        assertEquals(0, investmentFund1.getAverageMonthlyNetProfit().compareTo(new BigDecimal("162.27")));
    }

    @Test
    void getAverageMonthlyGrossProfitTest() {
        assertEquals(0, investmentFund1.getAverageMonthlyGrossProfit().compareTo(new BigDecimal("190.90")));
    }

    @Test
    void setDurationTest() {
        investmentFund1.changeDuration(3);
        assertEquals(3, investmentFund1.getDuration());
        assertEquals(0, investmentFund1.getGrossProfit().compareTo(new BigDecimal("607.71")));
        assertEquals(0, investmentFund1.getNetProfit().compareTo(new BigDecimal("516.55")));
        assertThrows(IllegalArgumentException.class, () -> investmentFund1.changeDuration(-1));
        assertThrows(IllegalArgumentException.class, () -> investmentFund1.changeDuration(-1));
    }

    @Test
    void setIndividualMonthlyProfitabilityTest() {
        BigDecimal monthlyProfitabilityNull = null;
        LocalDate dateNull = null;

        investmentFund1.changeDuration(3);
        investmentFund1.setIndividualMonthlyProfitability(LocalDate.now().plusMonths(2), new BigDecimal("0.15"));
        assertEquals(0, investmentFund1.getGrossProfit().compareTo(new BigDecimal("664.19")));
        assertEquals(0, investmentFund1.getPayments().get(1).getMonthlyProfitability().compareTo(new BigDecimal("0.15")));
        assertThrows(IllegalArgumentException.class, () -> investmentFund1.setIndividualMonthlyProfitability(dateNull, new BigDecimal("0.15")));
        assertThrows(IllegalArgumentException.class, () -> investmentFund1.setIndividualMonthlyProfitability(LocalDate.now().plusMonths(2), monthlyProfitabilityNull));
        assertThrows(IllegalArgumentException.class, () -> investmentFund1.setIndividualMonthlyProfitability(dateNull, monthlyProfitabilityNull));
    }

    @Test
    void setAmountInvestedTest() {
        investmentFund1.changeDuration(3);
        investmentFund1.setIndividualMonthlyProfitability(LocalDate.now().plusMonths(2L), new BigDecimal("0.15"));
        assertEquals(0, investmentFund1.getGrossProfit().compareTo(new BigDecimal("664.19")));
        investmentFund1.changeAmountInvested(new BigDecimal("1800"));
        assertEquals(0, investmentFund1.getGrossProfit().compareTo(new BigDecimal("796.61")));
        assertEquals(0, investmentFund1.getPayments().get(1).getMonthlyProfitability().compareTo(new BigDecimal("0.15")));
        assertThrows(IllegalArgumentException.class, () -> investmentFund1.changeAmountInvested(new BigDecimal("0")));
        assertThrows(IllegalArgumentException.class, () -> investmentFund1.changeAmountInvested(new BigDecimal("-1")));
        BigDecimal amountNull = null;
        assertThrows(IllegalArgumentException.class, () -> investmentFund1.changeAmountInvested(amountNull));
    }

    @Test
    void createPaymentsTest() {
        assertEquals(2, investmentFund1.getPayments().size());
        assertEquals(LocalDate.now().plusMonths(2L), investmentFund1.getPayments().get(1).getDateOfPayment());
    }

}
