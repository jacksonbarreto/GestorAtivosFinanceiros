package model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "AtivoFinanceiro")
@Access(AccessType.PROPERTY)
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "TipoAtivo", discriminatorType = DiscriminatorType.INTEGER)
public abstract class FinancialAsset implements Serializable {

    protected Long id;
    protected AssetType assetType;
    protected LocalDate startDate;
    protected int duration;
    protected BigDecimal tax;
    protected String designation;
    protected List<Payment> payments;

    protected FinancialAsset() {
    }

    public FinancialAsset(AssetType assetType, LocalDate startDate, int duration, BigDecimal tax, String designation, ArrayList<Payment> payments) {
        this.startDate = startDate;
        this.duration = duration;
        this.tax = tax;
        this.designation = designation;
        this.payments = payments;
        this.assetType = assetType;
    }

    protected ArrayList<Payment> createPayments(BigDecimal monthlyProfitability) {
        ArrayList<Payment> payments = new ArrayList<>();
        for (int i = 0; i < this.duration; i++) {
            payments.add(new Payment(this, this.startDate.plusMonths(i), monthlyProfitability));
        }
        return payments;
    }

    @Id
    @GeneratedValue
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    @Column(name = "DataInicio", nullable = false)
    public LocalDate getStartDate() {
        return startDate;
    }

    public abstract void setStartDate(LocalDate startDate);

    @Column(name = "Duracao", nullable = false)
    public int getDuration() {
        return duration;
    }

    public abstract void setDuration(int duration);

    @Column(name = "Imposto", nullable = false)
    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    @Column(name = "Designacao", nullable = false)
    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    @OneToMany(mappedBy = "financialAsset", fetch = FetchType.EAGER)
    public List<Payment> getPayments() {
        return payments;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPayments(ArrayList<Payment> payments) {
        this.payments = payments;
    }

    @Column(name = "TipoAtivo", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    public AssetType getAssetType() {
        return assetType;
    }

    public void setAssetType(AssetType assetType) {
        this.assetType = assetType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FinancialAsset)) return false;
        FinancialAsset that = (FinancialAsset) o;
        return getDuration() == that.getDuration() && Objects.equals(getId(), that.getId()) && getAssetType() == that.getAssetType() && getStartDate().equals(that.getStartDate()) && getTax().equals(that.getTax()) && getDesignation().equals(that.getDesignation()) && getPayments().equals(that.getPayments());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getAssetType(), getStartDate(), getDuration(), getTax(), getDesignation(), getPayments());
    }
}
