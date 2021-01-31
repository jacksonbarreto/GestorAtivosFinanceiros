package model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PaymentForList {

    private String dateOfPayment;
    private String monthlyProfitability;
    private String interestReceived;
    private String taxDue;

    public PaymentForList() {
    }

    public PaymentForList(String dateOfPayment, String monthlyProfitability, String interestReceived) {
        this.dateOfPayment = dateOfPayment;
        this.monthlyProfitability = monthlyProfitability;
        this.interestReceived = interestReceived;
    }

    public String getTaxDue() {
        return taxDue;
    }

    public void setTaxDue(String taxDue) {
        this.taxDue = taxDue;
    }

    public String getDateOfPayment() {
        return dateOfPayment;
    }

    public void setDateOfPayment(String dateOfPayment) {
        this.dateOfPayment = dateOfPayment;
    }

    public String getMonthlyProfitability() {
        return monthlyProfitability;
    }

    public void setMonthlyProfitability(String monthlyProfitability) {
        this.monthlyProfitability = monthlyProfitability;
    }

    public String getInterestReceived() {
        return interestReceived;
    }

    public void setInterestReceived(String interestReceived) {
        this.interestReceived = interestReceived;
    }
}
