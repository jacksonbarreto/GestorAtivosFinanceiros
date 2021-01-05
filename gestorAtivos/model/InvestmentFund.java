package model;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

import static model.AssetType.FOUND;

@Entity
@Table(name = "Fundo")
@Access(AccessType.PROPERTY)
//@DiscriminatorValue(value = "FOUND")
@PrimaryKeyJoinColumn(name = "id")
public class InvestmentFund extends FinancialAsset implements AssetWithInvestedValue {

    private BigDecimal amountInvested;
    private BigDecimal monthlyProfitability;

    /**
     * Investment Fund Builder for ORM
     */
    private InvestmentFund() {
    }

    /**
     * Builder of an investment fund.
     *
     * @param duration             Duration, in months, of the investment.
     * @param tax                  Tax due annually.
     * @param designation          Designation chosen to represent the financial asset.
     * @param amountInvested       Monetary representation of the amount invested.
     * @param monthlyProfitability Monthly return on investment.
     */
    public InvestmentFund(int duration, BigDecimal tax, String designation, BigDecimal amountInvested, BigDecimal monthlyProfitability) {
        super(FOUND, LocalDate.now(), duration, tax, designation);
        if (amountInvested == null || amountInvested.compareTo(new BigDecimal("0")) <= 0)
            throw new IllegalArgumentException();
        if (monthlyProfitability == null)
            throw new IllegalArgumentException();
        this.amountInvested = amountInvested;
        this.monthlyProfitability = monthlyProfitability;
        this.payments = this.createPayments();
    }


    /**
     * This method allows you to change the monthly profitability of a specific month (a given payment).
     * <p>
     * If an invalid date is provided, that is, a date that does not correspond to any payment,
     * the method does not make any changes.
     *
     * @param dateOfPayment           Payment date to be changed.
     * @param newMonthlyProfitability New monthly profitability.
     */
    @Transient
    public void setIndividualMonthlyProfitability(LocalDate dateOfPayment, BigDecimal newMonthlyProfitability) {
        if (dateOfPayment == null || newMonthlyProfitability == null)
            throw new IllegalArgumentException();
        for (Payment payment : this.payments) {
            if (payment.getDateOfPayment().isEqual(dateOfPayment)) {
                payment.setMonthlyProfitability(newMonthlyProfitability);
                recalculatePayments();
                break;
            }
        }
    }

    /**
     * Method to change the default monthly profitability for all payments.
     *
     * @param monthlyProfitability New standard profitability in percentage.
     */
    public void setMonthlyProfitability(BigDecimal monthlyProfitability) {
        if (monthlyProfitability == null)
            throw new IllegalArgumentException();
        this.monthlyProfitability = monthlyProfitability;
    }

    /**
     * This method recalculates payments. Must be used when making a change to the monthly fee for a payment.
     */
    private void recalculatePayments() {
        ArrayList<Payment> payments = new ArrayList<>();
        BigDecimal interestReceived;
        BigDecimal amount = new BigDecimal(amountInvested.toString());
        for (Payment payment : this.payments) {
            interestReceived = amount.multiply(payment.getMonthlyProfitability());
            payments.add(new Payment(this, payment.getDateOfPayment(), payment.getMonthlyProfitability(), interestReceived));
            amount = amount.add(interestReceived);
        }
        this.payments = payments;
    }

    /**
     * This method creates the sequence of investment payments, according to the specifics of each investment.
     *
     * @return A collection of payments.
     */
    @Override
    protected ArrayList<Payment> createPayments() {
        ArrayList<Payment> payments = new ArrayList<>();
        BigDecimal interestReceived;
        BigDecimal amount = new BigDecimal(amountInvested.toString());

        for (int i = 1; i <= this.duration; i++) {
            interestReceived = amount.multiply(this.monthlyProfitability);
            payments.add(new Payment(this, this.startDate.plusMonths(i), monthlyProfitability, interestReceived));
            amount = amount.add(interestReceived);
        }
        return payments;
    }

    /**
     * The method changes the start date of the financial asset, automatically recalculating payments.
     * If there have been changes in monthly earnings individually, these will be lost.
     *
     * @param startDate Financial asset start date.
     */
    public void changeStartDate(LocalDate startDate) {
        if (startDate == null)
            throw new IllegalArgumentException();
        this.startDate = startDate;
        this.payments = this.createPayments();
    }

    /**
     * The method changes the duration of the financial asset, automatically recalculating payments.
     * If there have been changes in monthly earnings individually, these will be lost.
     *
     * @param duration New duration, in months, of the financial asset.
     */
    public void changeDuration(int duration) {
        if (duration <= 0)
            throw new IllegalArgumentException();
        this.duration = duration;
        this.payments = this.createPayments();
    }

    /**
     * Method to obtain the amount invested in the financial asset.
     *
     * @return Amount invested, in monetary units, in the financial asset.
     */
    @Column(name = "ValorInvestido", nullable = false)
    public BigDecimal getAmountInvested() {
        return amountInvested;
    }

    /**
     * Method to obtain the standard monthly profitability of the financial asset.
     *
     * @return Standard monthly return on financial assets.
     */
    @Column(name = "RentabilidadeMensal", nullable = false)
    public BigDecimal getMonthlyProfitability() {
        return monthlyProfitability;
    }

    /**
     * Method returns the monthly profitability of each payment.
     *
     * @return Collection of monthly returns in percentage.
     */
    @Transient
    public ArrayList<BigDecimal> getIndividualMonthlyProfitability() {
        ArrayList<BigDecimal> individualMonthlyReturns = new ArrayList<>();
        for (Payment payment : this.payments) {
            individualMonthlyReturns.add(payment.getMonthlyProfitability());
        }
        return individualMonthlyReturns;
    }

    /**
     * Method to change the amount invested. Automatically recalculates payments.
     * If any monthly profitability has been changed individually, this will be maintained.
     *
     * @param amountInvested New amount invested.
     */
    public void changeAmountInvested(BigDecimal amountInvested) {
        if (amountInvested == null || amountInvested.compareTo(new BigDecimal("0")) <= 0)
            throw new IllegalArgumentException();
        this.amountInvested = amountInvested;
        recalculatePayments();
    }
    /**
     * Method to change the amount invested.  Exclusive use of ORM.
     *
     * @param amountInvested New amount invested.
     */
    private void setAmountInvested(BigDecimal amountInvested) {
        this.amountInvested = amountInvested;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InvestmentFund)) return false;
        if (!super.equals(o)) return false;
        InvestmentFund that = (InvestmentFund) o;
        return getAmountInvested().equals(that.getAmountInvested()) && getMonthlyProfitability().equals(that.getMonthlyProfitability());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getAmountInvested(), getMonthlyProfitability());
    }

    @Override
    public int compareTo(FinancialAsset financialAsset) {
        return this.amountInvested.compareTo(((AssetWithInvestedValue) financialAsset).getAmountInvested());
    }
}
