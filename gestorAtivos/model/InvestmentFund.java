package model;

import java.time.LocalDate;
import java.util.ArrayList;

public class InvestmentFund extends FinancialAsset {
    private double amountInvested;
    private double monthlyProfitability;

    public InvestmentFund(LocalDate startDate, int duration, float tax, String designation, ArrayList<Payment> payments, double amountInvested, double monthlyProfitability) {
        super(startDate, duration, tax, designation, payments);
        this.amountInvested = amountInvested;
        this.monthlyProfitability = monthlyProfitability;
    }
    public InvestmentFund( int duration, float tax, String designation, double amountInvested, double monthlyProfitability) {
        super(LocalDate.now(), duration, tax, designation, new ArrayList<Payment>());
        this.amountInvested = amountInvested;
        this.monthlyProfitability = monthlyProfitability;
        this.payments = this.createPayments(this.monthlyProfitability);
    }


    @Override
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
        this.payments = this.createPayments(this.monthlyProfitability);
    }

    @Override
    public void setDuration(int duration) {
        this.duration = duration;
        this.payments = this.createPayments(this.monthlyProfitability);
    }

    public double getAmountInvested() {
        return amountInvested;
    }

    public double getMonthlyProfitability() {
        return monthlyProfitability;
    }
}
