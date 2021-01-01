package test;

import model.AssetType;
import model.InvestmentFund;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InvestmentFundTest {
    InvestmentFund investmentFund = new InvestmentFund(2, new BigDecimal("0.1"), "Fundo Petro4", new BigDecimal("1000"), new BigDecimal("0.1"));

    @Test
    void attributeVerification() {
        assertEquals(2, investmentFund.getDuration());
        assertEquals(0, investmentFund.getTax().compareTo(new BigDecimal("0.1")));
        assertEquals("Fundo Petro4", investmentFund.getDesignation());
        assertEquals(0, investmentFund.getAmountInvested().compareTo(new BigDecimal("1000")));
        assertEquals(0, investmentFund.getMonthlyProfitability().compareTo(new BigDecimal("0.1")));
        assertEquals(LocalDate.now(), investmentFund.getStartDate());
        assertEquals(AssetType.FOUND, investmentFund.getAssetType());
    }

    @Test
    void GrossProfitTest() {
        assertEquals(0, investmentFund.getGrossProfit().compareTo(new BigDecimal("210")));
    }

    @Test
    void getNetProfitTest(){
        assertEquals(0, investmentFund.getNetProfit().compareTo(new BigDecimal("189")));
    }
}
