package model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

import static java.math.BigDecimal.ROUND_HALF_UP;

@Entity
@Table(name = "Deposito")
@Access(AccessType.PROPERTY)
@DiscriminatorValue(value = "DEPOSIT")
@PrimaryKeyJoinColumn(name = "id")
public class TermDeposit extends FinancialAsset implements AssetWithInvestedValue{

    private BigDecimal depositedAmount;
    private BigDecimal annualProfitability;
    private String account;

    private Bank bank;

    private TermDeposit(){}



    public TermDeposit(int duration, BigDecimal tax, String designation, BigDecimal depositedAmount, BigDecimal annualProfitability, String account, Bank bank) {
        super(AssetType.DEPOSIT, LocalDate.now(), duration, tax, designation, new ArrayList<>());
        this.depositedAmount = depositedAmount;
        this.annualProfitability = annualProfitability;
        this.account = account;
        this.bank = bank;
        this.payments = this.createPayments();
    }

    @Override
    public void setDuration(int duration) {
        this.duration = duration;
        this.payments = this.createPayments();
    }

    @Override
    protected ArrayList<Payment> createPayments() {
        ArrayList<Payment> payments = new ArrayList<>();
        BigDecimal amountPaid;
        BigDecimal amount = new BigDecimal(depositedAmount.toString());

        for (int i = 1; i <= this.duration; i++) {
            amountPaid = amount.multiply(this.annualProfitability.divide(new BigDecimal(String.valueOf(12)),2, ROUND_HALF_UP));
            payments.add(new Payment(this, this.startDate.plusMonths(i), annualProfitability.divide(new BigDecimal(String.valueOf(12)),2, ROUND_HALF_UP), amountPaid));
            amount = amount.add(amountPaid);
        }
        return payments;
    }

    @Override
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
        this.payments = this.createPayments();
    }

    @Column(name = "ValorDepositado", nullable = false)
    public BigDecimal getDepositedAmount() {
        return depositedAmount;
    }

    @Column(name = "TaxaRendimentoAnual", nullable = false)
    public BigDecimal getAnnualProfitability() {
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

    public void setDepositedAmount(BigDecimal depositedAmount) {
        this.depositedAmount = depositedAmount;
    }

    public void setAnnualProfitability(BigDecimal annualProfitability) {
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
        if (!super.equals(o)) return false;
        TermDeposit that = (TermDeposit) o;
        return getDepositedAmount().equals(that.getDepositedAmount()) && getAnnualProfitability().equals(that.getAnnualProfitability()) && getAccount().equals(that.getAccount()) && getBank().equals(that.getBank());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDepositedAmount(), getAnnualProfitability(), getAccount(), getBank());
    }

    @Override
    @Transient
    public BigDecimal getAmountInvested() {
        return this.getDepositedAmount();
    }

    @Override
    public int compareTo(FinancialAsset financialAsset) {
        return this.depositedAmount.compareTo(((InvestmentFund) financialAsset).getAmountInvested());
    }
}
