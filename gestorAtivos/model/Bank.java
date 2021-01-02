package model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.math.BigDecimal.ROUND_HALF_UP;
import static model.Utilities.dateIsInThePeriod;

@Entity
@Table(name = "Banco")
@Access(AccessType.PROPERTY)
public class Bank implements Serializable {

    private Long id;
    private String name;
    private List<TermDeposit> termDeposits;

    private Bank() {

    }

    public Bank(String name, ArrayList<TermDeposit> termDeposits) {
        this.name = name;
        this.termDeposits = termDeposits;
    }

    public Bank(String name) {
        this.name = name;
        termDeposits = new ArrayList<>();
    }

    /**
     * This method returns the amount, in monetary units, deposited with the bank.
     *
     * @return the amount, in monetary units, deposited with the bank
     */
    @Transient
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
    @Transient
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
    @Transient
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
    @Transient
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

    @OneToMany(mappedBy = "bank", fetch = FetchType.EAGER)
    public List<TermDeposit> getTermDeposits() {
        return termDeposits;
    }

    @Id
    @GeneratedValue
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    @Column(name = "Nome", nullable = false)
    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTermDeposits(ArrayList<TermDeposit> termDeposits) {
        this.termDeposits = termDeposits;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTermDeposits(List<TermDeposit> termDeposits) {
        this.termDeposits = termDeposits;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Bank)) return false;
        Bank bank = (Bank) o;
        return Objects.equals(getId(), bank.getId()) && getName().equals(bank.getName()) && getTermDeposits().equals(bank.getTermDeposits());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getTermDeposits());
    }
}
