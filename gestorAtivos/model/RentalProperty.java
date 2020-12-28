package model;

import java.time.LocalDate;
import java.util.ArrayList;

public class RentalProperty extends FinancialAsset{
    private double propertyValue;
    private double rentAmount;
    private double monthlyCostCondominium;
    private double annualAmountOtherExpenses;
    private String location;

    public RentalProperty(LocalDate startDate, int duration, float tax, String designation, ArrayList<Payment> payments, double propertyValue, double rentAmount, double monthlyCostCondominium, double annualAmountOtherExpenses, String location) {
        super(startDate, duration, tax, designation, payments);
        this.propertyValue = propertyValue;
        this.rentAmount = rentAmount;
        this.monthlyCostCondominium = monthlyCostCondominium;
        this.annualAmountOtherExpenses = annualAmountOtherExpenses;
        this.location = location;
    }

    public RentalProperty(int duration, float tax, String designation, double propertyValue, double rentAmount, double monthlyCostCondominium, double annualAmountOtherExpenses, String location) {
        super(LocalDate.now(), duration, tax, designation, new ArrayList<Payment>());
        this.propertyValue = propertyValue;
        this.rentAmount = rentAmount;
        this.monthlyCostCondominium = monthlyCostCondominium;
        this.annualAmountOtherExpenses = annualAmountOtherExpenses;
        this.location = location;
        this.payments = this.createPayments(0);
    }

    @Override
    public void setDuration(int duration) {
        this.duration = duration;
        this.payments = this.createPayments(0);
    }
    @Override
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
        this.payments = this.createPayments(0);
    }

    public double getPropertyValue() {
        return propertyValue;
    }

    public double getRentAmount() {
        return rentAmount;
    }

    public double getMonthlyCostCondominium() {
        return monthlyCostCondominium;
    }

    public double getAnnualAmountOtherExpenses() {
        return annualAmountOtherExpenses;
    }

    public String getLocation() {
        return location;
    }
}
