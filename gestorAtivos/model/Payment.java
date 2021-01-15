package model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import static java.math.BigDecimal.ROUND_HALF_UP;

public class Payment implements Serializable {
    private static final long serialVersionUID = 1L;
    private final LocalDate dateOfPayment;
    private BigDecimal monthlyProfitability;
    private final BigDecimal interestReceived;

    /**
     * Payment builder.
     *
     * @param dateOfPayment        Date of payment.
     * @param monthlyProfitability Monthly profitability, in percentage.
     * @param interestReceived     Interest received (the payment itself).
     */
    public Payment(LocalDate dateOfPayment, BigDecimal monthlyProfitability, BigDecimal interestReceived) {
        if (dateOfPayment == null || monthlyProfitability == null || interestReceived == null)
            throw new IllegalArgumentException();
        this.dateOfPayment = dateOfPayment;
        this.monthlyProfitability = monthlyProfitability;
        this.interestReceived = interestReceived;
    }


    /**
     * Method that returns the monetary amount due in taxes.
     *
     * @param tax Percent tax.
     * @return Monetary value payable in tax.
     */

    public BigDecimal getTaxDue(BigDecimal tax) {
        if (tax == null)
            throw new IllegalArgumentException();
        return this.interestReceived.multiply(tax).setScale(2, ROUND_HALF_UP);
    }

    /**
     * Method to obtain the profitability applied to the payment.
     *
     * @return Profitability applied to payment.
     */
    public BigDecimal getMonthlyProfitability() {
        return monthlyProfitability;
    }

    /**
     * Method for changing the profitability of a payment.
     *
     * @param monthlyProfitability New profitability of a payment, in percentage.
     */
    public void setMonthlyProfitability(BigDecimal monthlyProfitability) {
        if (monthlyProfitability == null)
            throw new IllegalArgumentException();
        this.monthlyProfitability = monthlyProfitability;
    }


    /**
     * Method for obtaining the date on which a financial asset was paid, or will be paid.
     *
     * @return Date on which the financial asset was paid, or will be paid.
     */
    public LocalDate getDateOfPayment() {
        return dateOfPayment;
    }


    /**
     * Method for obtaining the amount received as payment, ie the interest received.
     *
     * @return Amount received as payment.
     */
    public BigDecimal getInterestReceived() {
        return interestReceived;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Payment)) return false;
        Payment payment = (Payment) o;
        return getDateOfPayment().equals(payment.getDateOfPayment()) && getMonthlyProfitability().equals(payment.getMonthlyProfitability()) && getInterestReceived().equals(payment.getInterestReceived());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDateOfPayment(), getMonthlyProfitability(), getInterestReceived());
    }
}
