package model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "Pagamento", uniqueConstraints = @UniqueConstraint(columnNames = {"AtivoFinanceiro","DataPagamento"}))
@Access(AccessType.PROPERTY)
public class Payment implements Serializable {

    private Long id;
    private FinancialAsset financialAsset;
    private LocalDate dateOfPayment;
    private BigDecimal monthlyProfitability;
    private BigDecimal amountPaid;

    protected Payment() {
    }


    public Payment(FinancialAsset financialAsset, LocalDate dateOfPayment, BigDecimal monthlyProfitability, BigDecimal amountPaid) {
        this.financialAsset = financialAsset;
        this.dateOfPayment = dateOfPayment;
        this.monthlyProfitability = monthlyProfitability;
        this.amountPaid = amountPaid;
    }

    @Column(name = "RentabilidadeMensal", nullable = false)
    public BigDecimal getMonthlyProfitability() {
        return monthlyProfitability;
    }

    public void setMonthlyProfitability(BigDecimal monthlyProfitability) {
        this.monthlyProfitability = monthlyProfitability;
    }

    @ManyToOne
    @JoinColumn(name = "AtivoFinanceiro", referencedColumnName = "id", nullable = false)
    public FinancialAsset getFinancialAsset() {
        return financialAsset;
    }

    public void setFinancialAsset(FinancialAsset financialAsset) {
        this.financialAsset = financialAsset;
    }

    @Column(name = "DataPagamento", nullable = false)
    public LocalDate getDateOfPayment() {
        return dateOfPayment;
    }

    public void setDateOfPayment(LocalDate dateOfPayment) {
        this.dateOfPayment = dateOfPayment;
    }

    @Id
    @GeneratedValue
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "ValorPago", nullable = false)
    public BigDecimal getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(BigDecimal amountPaid) {
        this.amountPaid = amountPaid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Payment)) return false;
        Payment payment = (Payment) o;
        return Objects.equals(getId(), payment.getId()) && getDateOfPayment().equals(payment.getDateOfPayment()) && getMonthlyProfitability().equals(payment.getMonthlyProfitability()) && getAmountPaid().equals(payment.getAmountPaid());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDateOfPayment(), getMonthlyProfitability(), getAmountPaid());
    }
}
