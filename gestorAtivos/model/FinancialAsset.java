package model;

import java.time.LocalDate;
import java.util.ArrayList;

public abstract class FinancialAsset {
    protected LocalDate startDate;
    protected int duration;
    protected float tax;
    protected String designation;
    protected ArrayList<Payment> payments;

    public FinancialAsset(LocalDate startDate, int duration, float tax, String designation, ArrayList<Payment> payments) {
        this.startDate = startDate;
        this.duration = duration;
        this.tax = tax;
        this.designation = designation;
        this.payments = payments;
    }

    protected ArrayList<Payment> createPayments(double monthlyProfitability) {
        ArrayList<Payment> payments = new ArrayList<>();
        for (int i=0; i < this.duration; i++){
            payments.add(new Payment(this.startDate.plusMonths(i),monthlyProfitability));
        }
        return payments;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public abstract void setStartDate(LocalDate startDate);

    public int getDuration() {
        return duration;
    }

    public abstract void setDuration(int duration);

    public float getTax() {
        return tax;
    }

    public void setTax(float tax) {
        this.tax = tax;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public ArrayList<Payment> getPayments() {
        return payments;
    }

}
