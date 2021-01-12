package model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import static java.math.BigDecimal.ROUND_HALF_UP;

// , uniqueConstraints = @UniqueConstraint(columnNames = {"AtivoFinanceiro", "DataPagamento"})
@Entity
@Table(name = "Pagamento", uniqueConstraints = @UniqueConstraint(columnNames = {"AtivoFinanceiro", "DataPagamento"}))
@Access(AccessType.PROPERTY)
public class Payment implements Serializable {

    private Long id;
    private FinancialAsset financialAsset;
    private LocalDate dateOfPayment;
    private BigDecimal monthlyProfitability;
    private BigDecimal interestReceived;

    /**
     * Payment builder. Exclusive for ORM use.
     */
    private Payment() {
    }

    /**
     * Payment builder.
     *
     * @param financialAsset       Financial asset.
     * @param dateOfPayment        Date of payment.
     * @param monthlyProfitability Monthly profitability, in percentage.
     * @param interestReceived     Interest received (the payment itself).
     */
    public Payment(FinancialAsset financialAsset, LocalDate dateOfPayment, BigDecimal monthlyProfitability, BigDecimal interestReceived) {
        if (financialAsset == null || dateOfPayment == null || monthlyProfitability == null || interestReceived == null)
            throw new IllegalArgumentException();
        this.financialAsset = financialAsset;
        this.dateOfPayment = dateOfPayment;
        this.monthlyProfitability = monthlyProfitability;
        this.interestReceived = interestReceived;
    }

    /**
     * Payment builder.
     *
     * @param id                   id from database.
     * @param financialAsset       Financial asset.
     * @param dateOfPayment        Date of payment.
     * @param monthlyProfitability Monthly profitability, in percentage.
     * @param interestReceived     Interest received (the payment itself).
     */
    public Payment(Long id, FinancialAsset financialAsset, LocalDate dateOfPayment, BigDecimal monthlyProfitability, BigDecimal interestReceived) {
        if (id == null || financialAsset == null || dateOfPayment == null || monthlyProfitability == null || interestReceived == null)
            throw new IllegalArgumentException();
        this.id = id;
        this.financialAsset = financialAsset;
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
    @Transient
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
    @Column(name = "RentabilidadeMensal", nullable = false)
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
     * Method for obtaining the financial asset to which the payment belongs.
     *
     * @return The financial asset to which the payment belongs.
     */
    @ManyToOne
    @JoinColumn(name = "AtivoFinanceiro", referencedColumnName = "id", nullable = false)
    public FinancialAsset getFinancialAsset() {
        return financialAsset;
    }

    /**
     * Method for changing the financial asset of a payment. Exclusive use of ORM.
     *
     * @param financialAsset Financial asset of a payment.
     */
    private void setFinancialAsset(FinancialAsset financialAsset) {
        if (financialAsset == null)
            throw new IllegalArgumentException();
        this.financialAsset = financialAsset;
    }

    /**
     * Method for obtaining the date on which a financial asset was paid, or will be paid.
     *
     * @return Date on which the financial asset was paid, or will be paid.
     */
    @Column(name = "DataPagamento", nullable = false)
    public LocalDate getDateOfPayment() {
        return dateOfPayment;
    }

    /**
     * Method for changing the payment date. Exclusive use by ORM.
     *
     * @param dateOfPayment New payment date.
     */
    private void setDateOfPayment(LocalDate dateOfPayment) {
        if (dateOfPayment == null)
            throw new IllegalArgumentException();
        this.dateOfPayment = dateOfPayment;
    }

    /**
     * Method for obtaining the payment (database) ID.
     *
     * @return the payment (database) ID.
     */
    @Id
    @GeneratedValue
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    /**
     * Method for changing the payment (database) ID. Exclusive use by ORM.
     *
     * @param id New ID.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Method for obtaining the amount received as payment, ie the interest received.
     *
     * @return Amount received as payment.
     */
    @Column(name = "JurosRecebido", nullable = false)
    public BigDecimal getInterestReceived() {
        return interestReceived;
    }

    /**
     * Method for changing the amount received as a payment, ie the interest received. Exclusive use by ORM.
     *
     * @param interestReceived New amount received as payment.
     */
    private void setInterestReceived(BigDecimal interestReceived) {
        if (interestReceived == null)
            throw new IllegalArgumentException();
        this.interestReceived = interestReceived;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Payment)) return false;
        Payment payment = (Payment) o;
        return Objects.equals(getId(), payment.getId()) && Objects.equals(getFinancialAsset(), payment.getFinancialAsset()) && Objects.equals(getDateOfPayment(), payment.getDateOfPayment()) && Objects.equals(getMonthlyProfitability(), payment.getMonthlyProfitability()) && Objects.equals(getInterestReceived(), payment.getInterestReceived());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDateOfPayment(), getMonthlyProfitability(), getInterestReceived());
    }
}
