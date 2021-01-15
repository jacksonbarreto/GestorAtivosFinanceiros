package model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.math.BigDecimal.ROUND_HALF_UP;
import static model.Utilities.dateIsInThePeriod;

public class Bank implements Serializable {

    private static final long serialVersionUID = 1L;
    private String name;
    private final List<TermDeposit> termDeposits;


    /**
     * Bank Builder
     *
     * @param name Bank name
     */
    public Bank(String name) {
        if (name == null) {
            throw new IllegalArgumentException();
        } else if (name.isEmpty()) {
            throw new IllegalArgumentException();
        } else if (name.length() < 3) {
            throw new IllegalArgumentException();
        }
        this.name = name;
        termDeposits = new ArrayList<>();
    }

    /**
     * This method returns the amount, in monetary units, deposited with the bank.
     *
     * @return the amount, in monetary units, deposited with the bank
     */
    public BigDecimal getEquityInDeposits() {
        BigDecimal totalDeposited = new BigDecimal("0");

        for (TermDeposit termDeposit : this.termDeposits) {
            totalDeposited = totalDeposited.add(termDeposit.getDepositedAmount());
        }

        return totalDeposited.setScale(2, ROUND_HALF_UP);
    }

    /**
     * This method returns the amount, in monetary units, deposited with the bank in a given period.
     *
     * @param initialDate period start date.
     * @param finalDate   period end date.
     * @return the amount, in monetary units, deposited with the bank
     */
    public BigDecimal getEquityInDeposits(LocalDate initialDate, LocalDate finalDate) {
        BigDecimal totalDeposited = new BigDecimal("0");

        for (TermDeposit termDeposit : this.termDeposits) {
            if (dateIsInThePeriod(initialDate, finalDate, termDeposit.getStartDate()))
                totalDeposited = totalDeposited.add(termDeposit.getDepositedAmount());
        }

        return totalDeposited.setScale(2, ROUND_HALF_UP);
    }

    /**
     * This method returns the total, in monetary units, paid by the bank to customers, as interest on the amounts invested.
     *
     * @return the total, in monetary units, paid by the bank to customers.
     */
    public BigDecimal getTotalInterestPaid() {
        BigDecimal totalInterestPaid = new BigDecimal("0");
        for (TermDeposit termDeposit : this.termDeposits) {
            for (Payment payment : termDeposit.getPayments()) {
                totalInterestPaid = totalInterestPaid.add(payment.getInterestReceived());
            }
        }
        return totalInterestPaid.setScale(2, ROUND_HALF_UP);
    }

    /**
     * This method returns the total, in monetary units, paid by the bank to customers,
     * as interest on the amounts invested, over a given period.
     *
     * @param initialDate period start date.
     * @param finalDate   period end date.
     * @return the total, in monetary units, paid by the bank to customers.
     */
    public BigDecimal getTotalInterestPaid(LocalDate initialDate, LocalDate finalDate) {

        BigDecimal totalInterestPaid = new BigDecimal("0");
        for (TermDeposit termDeposit : this.termDeposits) {
            for (Payment payment : termDeposit.getPayments()) {
                if (dateIsInThePeriod(initialDate, finalDate, payment.getDateOfPayment()))
                    totalInterestPaid = totalInterestPaid.add(payment.getInterestReceived());
            }
        }
        return totalInterestPaid.setScale(2, ROUND_HALF_UP);
    }

    /**
     * Method for adding a deposit in the bank's caution.
     *
     * @param termDeposit A term deposit.
     */
    public void addDeposit(TermDeposit termDeposit) {
        if (termDeposit == null)
            throw new IllegalArgumentException();
        this.termDeposits.add(termDeposit);
    }

    /**
     * Method for obtaining all deposits secured in the bank.
     *
     * @return Collection of cautioned deposits at the bank.
     */
    public List<TermDeposit> getTermDeposits() {
        return termDeposits;
    }


    /**
     * Method to obtain the bank name.
     *
     * @return The name of the bank.
     */
    public String getName() {
        return name;
    }


    /**
     * Method for changing the bank name.
     *
     * @param name New bank name.
     */
    public void setName(String name) {
        if (name == null) {
            throw new IllegalArgumentException();
        } else if (name.isEmpty()) {
            throw new IllegalArgumentException();
        } else if (name.length() < 3) {
            throw new IllegalArgumentException();
        }
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Bank)) return false;
        Bank bank = (Bank) o;
        return getName().equals(bank.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
