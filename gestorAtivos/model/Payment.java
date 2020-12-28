package model;

import java.time.LocalDate;

public class Payment {
    LocalDate dateOfPayment;
    private double monthlyProfitability;

    public Payment(LocalDate dateOfPayment, double monthlyProfitability) {
        this.dateOfPayment = dateOfPayment;
        this.monthlyProfitability = monthlyProfitability;
    }

    public LocalDate getDateOfPayment() {
        return dateOfPayment;
    }

    public void setDateOfPayment(LocalDate dateOfPayment) {
        this.dateOfPayment = dateOfPayment;
    }

    public double getMonthlyProfitability() {
        return monthlyProfitability;
    }

    public void setMonthlyProfitability(double monthlyProfitability) {
        this.monthlyProfitability = monthlyProfitability;
    }
}
