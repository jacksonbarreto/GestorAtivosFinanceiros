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
@DiscriminatorColumn(name = "TipoAtivo", discriminatorType = DiscriminatorType.STRING)
public abstract class FinancialAsset implements Serializable {

    protected Long id;
    protected AssetType assetType;
    protected LocalDate startDate;
    protected int duration;
    protected BigDecimal tax;
    protected String designation;
    protected List<Payment> payments;

    /**
     * Financial Asset Builder for ORM
     */
    protected FinancialAsset() {
    }

    /**
     * Financial Asset Builder.
     *
     * @param assetType   Type of financial asset.
     * @param startDate   Financial asset start date.
     * @param duration    Duration, in months, of the investment.
     * @param tax         Tax due annually (in percentage).
     * @param designation Designation chosen to represent the financial asset.
     */
    public FinancialAsset(AssetType assetType, LocalDate startDate, int duration, BigDecimal tax, String designation) {
        if (duration <= 0)
            throw new IllegalArgumentException();
        if (tax.compareTo(new BigDecimal("0")) < 0)
            throw new IllegalArgumentException();
        if (designation == null) {
            throw new IllegalArgumentException();
        } else if (designation.isEmpty()) {
            throw new IllegalArgumentException();
        }
        this.startDate = startDate;
        this.duration = duration;
        this.tax = tax;
        this.designation = designation;
        this.assetType = assetType;
    }

    /**
     * This method creates the sequence of investment payments, according to the specifics of each investment.
     *
     * @return A collection of payments.
     */
    protected abstract ArrayList<Payment> createPayments();

    @Id
    @GeneratedValue
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    /**
     * Method for obtaining the start date of the financial asset.
     *
     * @return Financial asset start date.
     */
    @Column(name = "DataInicio", nullable = false)
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * Method allows changing the start date of the financial asset.
     *
     * @param startDate Financial asset start date.
     */
    public abstract void setStartDate(LocalDate startDate);

    /**
     * Method that obtains the duration of the Financial Asset.
     *
     * @return Duration, in months, of the Financial Asset.
     */
    @Column(name = "Duracao", nullable = false)
    public int getDuration() {
        return duration;
    }

    /**
     * The method changes the duration of the financial asset.     *
     *
     * @param duration New duration, in months, of the financial asset.
     */
    public abstract void setDuration(int duration);

    /**
     * Method for obtaining the annual tax index.
     *
     * @return Annual percentage of tax.
     */
    @Column(name = "Imposto", nullable = false)
    public BigDecimal getTax() {
        return tax;
    }

    /**
     * Method to change the annual tax.
     *
     * @param tax percentage annual tax.
     */
    public void setTax(BigDecimal tax) {
        if (tax.compareTo(new BigDecimal("0")) < 0)
            throw new IllegalArgumentException();
        this.tax = tax;
    }

    /**
     * Method for obtaining the designation of the financial asset.
     *
     * @return Designation of the financial asset.
     */
    @Column(name = "Designacao", nullable = false)
    public String getDesignation() {
        return designation;
    }

    /**
     * Method for changing the name of the financial asset.
     *
     * @param designation New name of the financial asset.
     */
    public void setDesignation(String designation) {
        if (designation == null) {
            throw new IllegalArgumentException();
        } else if (designation.isEmpty()) {
            throw new IllegalArgumentException();
        }
        this.designation = designation;
    }

    /**
     * Method for obtaining all payments on the financial asset.
     *
     * @return Financial asset payment collection.
     */
    @OneToMany(mappedBy = "financialAsset", fetch = FetchType.EAGER)
    public List<Payment> getPayments() {
        return payments;
    }

    /**
     * Method for setting an ID for the financial asset. Used exclusively by the ORM.
     *
     * @param id The ID (Primary Key) of the financial asset.
     */
    protected void setId(Long id) {
        this.id = id;
    }

    /**
     * Method for assigning a collection of payments to the financial asset. Used exclusively by the ORM.
     *
     * @param payments Financial asset payment collection.
     */
    protected void setPayments(ArrayList<Payment> payments) {
        this.payments = payments;
    }

    /**
     * Method that returns the type of the financial asset.
     *
     * @return Type of financial asset.
     */
    @Column(name = "TipoAtivo", nullable = false)
    @Enumerated(EnumType.STRING)
    public AssetType getAssetType() {
        return assetType;
    }

    /**
     * Method for assigning a specialization to the financial asset. Used exclusively by the ORM.
     *
     * @param assetType Specialization of the financial asset.
     */
    protected void setAssetType(AssetType assetType) {
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
