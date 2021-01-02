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
public class TermDeposit extends FinancialAsset implements AssetWithInvestedValue {

    private BigDecimal depositedAmount;
    private BigDecimal annualProfitability;
    private String account;
    private Bank bank;

    /**
     * Term Deposit Builder for ORM.
     */
    private TermDeposit() {
    }

    /**
     * term deposit builder.
     *
     * @param duration            Duration, in months, of the investment.
     * @param tax                 Annual tax on term deposit.
     * @param designation         Designation chosen to represent the financial asset.
     * @param depositedAmount     Monetary representation of the amount deposited.
     * @param annualProfitability Annual return on investment.
     * @param account             bank account identifying the term deposit.
     * @param bank                Banking institution where the term deposit was made.
     */
    public TermDeposit(int duration, BigDecimal tax, String designation, BigDecimal depositedAmount, BigDecimal annualProfitability, String account, Bank bank) {
        super(AssetType.DEPOSIT, LocalDate.now(), duration, tax, designation);
        if (depositedAmount.compareTo(new BigDecimal("0")) <= 0)
            throw new IllegalArgumentException();
        if (account == null) {
            throw new IllegalArgumentException();
        } else if (account.isEmpty()) {
            throw new IllegalArgumentException();
        } else if (account.length() <= 3) {
            throw new IllegalArgumentException();
        }
        this.depositedAmount = depositedAmount;
        this.annualProfitability = annualProfitability;
        this.account = account;
        this.bank = bank;
        this.payments = this.createPayments();
    }

    /**
     * Method for changing the duration of the investment.
     *
     * @param duration New duration, in months, of the financial asset.
     */
    @Override
    public void setDuration(int duration) {
        if (duration <= 0)
            throw new IllegalArgumentException();
        this.duration = duration;
        this.payments = this.createPayments();
    }

    /**
     * This method creates the sequence of investment payments, according to the specifics of each investment.
     *
     * @return A collection of payments.
     */
    @Override
    protected ArrayList<Payment> createPayments() {
        ArrayList<Payment> payments = new ArrayList<>();
        BigDecimal amountPaid;
        BigDecimal amount = new BigDecimal(depositedAmount.toString());

        for (int i = 1; i <= this.duration; i++) {
            amountPaid = amount.multiply(this.annualProfitability.divide(new BigDecimal(String.valueOf(12)), 8, ROUND_HALF_UP));
            payments.add(new Payment(this, this.startDate.plusMonths(i), annualProfitability.divide(new BigDecimal(String.valueOf(12)), 8, ROUND_HALF_UP), amountPaid));
            amount = amount.add(amountPaid);
        }
        return payments;
    }

    /**
     * The method changes the start date of the financial asset, automatically recalculating payments.
     * If there have been changes in monthly earnings individually, these will be lost.
     *
     * @param startDate Financial asset start date.
     */
    @Override
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
        this.payments = this.createPayments();
    }

    /**
     * Method to obtain the amount deposited in the financial asset.
     *
     * @return Amount deposited, in monetary units, in the financial asset.
     */
    @Column(name = "ValorDepositado", nullable = false)
    public BigDecimal getDepositedAmount() {
        return depositedAmount;
    }

    /**
     * Method to obtain the amount deposited in the financial asset.
     * Method implemented by virtue of the interface, for compatibility of functions.
     *
     * @return Amount deposited, in monetary units, in the financial asset.
     */
    @Override
    @Transient
    public BigDecimal getAmountInvested() {
        return this.getDepositedAmount();
    }

    /**
     * Method to obtain the standard annual profitability of the financial asset.
     *
     * @return Standard annual return on financial assets.
     */
    @Column(name = "TaxaRendimentoAnual", nullable = false)
    public BigDecimal getAnnualProfitability() {
        return annualProfitability;
    }

    /**
     * Method to obtain the term deposit account.
     *
     * @return The term deposit account.
     */
    @Column(name = "Conta", nullable = false)
    public String getAccount() {
        return account;
    }

    /**
     * Method to obtain the bank responsible for the term deposit.
     *
     * @return The bank responsible for the term deposit.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Banco", referencedColumnName = "id", nullable = false)
    public Bank getBank() {
        return bank;
    }

    /**
     * Method to change the amount deposited. Automatically recalculates payments.
     * If any monthly profitability has been changed individually, this will be maintained.
     *
     * @param depositedAmount New amount deposited.
     */
    public void setDepositedAmount(BigDecimal depositedAmount) {
        if (depositedAmount.compareTo(new BigDecimal("0")) <= 0)
            throw new IllegalArgumentException();
        this.depositedAmount = depositedAmount;
        this.payments = createPayments();
    }

    /**
     * Method to change the annual return on the term deposit.
     *
     * @param annualProfitability New annual income, in percentage.
     */
    public void setAnnualProfitability(BigDecimal annualProfitability) {
        this.annualProfitability = annualProfitability;
        this.payments = createPayments();
    }

    /**
     * Method for changing the account that identifies the term deposit.
     *
     * @param account New account.
     */
    public void setAccount(String account) {
        if (account == null) {
            throw new IllegalArgumentException();
        } else if (account.isEmpty()) {
            throw new IllegalArgumentException();
        } else if (account.length() <= 3) {
            throw new IllegalArgumentException();
        }
        this.account = account;
    }

    /**
     * Method to change the bank responsible for the term deposit.
     *
     * @param bank New bank responsible for term deposit.
     */
    public void setBank(Bank bank) {
        if (bank == null)
            throw new IllegalArgumentException();
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
    public int compareTo(FinancialAsset financialAsset) {
        return this.depositedAmount.compareTo(((TermDeposit) financialAsset).getAmountInvested());
    }
}
