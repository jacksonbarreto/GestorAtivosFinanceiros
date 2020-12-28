package model;

import java.time.LocalDate;
import java.util.ArrayList;

public class TermDeposit extends FinancialAsset{
    private double depositedAmount;
    private double annualProfitability;
    private String account;
    private Bank bank;

    public TermDeposit(LocalDate startDate, int duration, float tax, String designation, ArrayList<Payment> payments, double depositedAmount, double annualProfitability, String account, Bank bank) {
        super(startDate, duration, tax, designation, payments);
        this.depositedAmount = depositedAmount;
        this.annualProfitability = annualProfitability;
        this.account = account;
        this.bank = bank;
    }

    public TermDeposit( int duration, float tax, String designation, double depositedAmount, double annualProfitability, String account, Bank bank) {
        super(LocalDate.now(), duration, tax, designation, new ArrayList<Payment>());
        this.depositedAmount = depositedAmount;
        this.annualProfitability = annualProfitability;
        this.account = account;
        this.bank = bank;
        this.payments = this.createPayments(this.annualProfitability/12);
    }

    @Override
    public void setDuration(int duration) {
        this.duration = duration;
        this.payments = this.createPayments(this.annualProfitability/12);
    }
    @Override
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
        this.payments = this.createPayments(this.annualProfitability/12);
    }

    public double getDepositedAmount() {
        return depositedAmount;
    }

    public double getAnnualProfitability() {
        return annualProfitability;
    }

    public String getAccount() {
        return account;
    }

    public Bank getBank() {
        return bank;
    }
}
