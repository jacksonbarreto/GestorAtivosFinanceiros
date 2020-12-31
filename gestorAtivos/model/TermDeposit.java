package model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

@Entity
@Table(name = "Deposito")
@Access(AccessType.PROPERTY)
@DiscriminatorValue(value = "2")
@PrimaryKeyJoinColumn(name = "id")
public class TermDeposit extends FinancialAsset{

    private double depositedAmount;
    private double annualProfitability;
    private String account;

    private Bank bank;


    public TermDeposit(LocalDate startDate, int duration, float tax, String designation, ArrayList<Payment> payments, double depositedAmount, double annualProfitability, String account, Bank bank) {
        super(AssetType.DEPOSIT, startDate, duration, tax, designation, payments);
        this.depositedAmount = depositedAmount;
        this.annualProfitability = annualProfitability;
        this.account = account;
        this.bank = bank;
    }

    public TermDeposit( int duration, float tax, String designation, double depositedAmount, double annualProfitability, String account, Bank bank) {
        super(AssetType.DEPOSIT, LocalDate.now(), duration, tax, designation, new ArrayList<>());
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

    @Column(name = "ValorDepositado", nullable = false)
    public double getDepositedAmount() {
        return depositedAmount;
    }

    @Column(name = "TaxaRendimentoAnual", nullable = false)
    public double getAnnualProfitability() {
        return annualProfitability;
    }

    @Column(name = "Conta", nullable = false)
    public String getAccount() {
        return account;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Banco", referencedColumnName = "id", nullable = false)
    public Bank getBank() {
        return bank;
    }

    public void setDepositedAmount(double depositedAmount) {
        this.depositedAmount = depositedAmount;
    }

    public void setAnnualProfitability(double annualProfitability) {
        this.annualProfitability = annualProfitability;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TermDeposit)) return false;
        TermDeposit that = (TermDeposit) o;
        return Double.compare(that.getDepositedAmount(), getDepositedAmount()) == 0 && Double.compare(that.getAnnualProfitability(), getAnnualProfitability()) == 0 && Objects.equals(getId(), that.getId()) && getAccount().equals(that.getAccount()) && getBank().equals(that.getBank());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDepositedAmount(), getAnnualProfitability(), getAccount(), getBank());
    }
}
