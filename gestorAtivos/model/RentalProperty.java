package model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

@Entity
@Table(name = "Imovel")
@Access(AccessType.PROPERTY)
//@DiscriminatorValue(value = "PROPERTY")
@PrimaryKeyJoinColumn(name = "id")
public class RentalProperty extends FinancialAsset implements AssetWithInvestedValue {

    private BigDecimal propertyValue;
    private BigDecimal rentAmount;
    private BigDecimal monthlyCostCondominium;
    private BigDecimal annualAmountOtherExpenses;
    private String location;

    /**
     * Rental property builder. Exclusively for ORM.
     */
    private RentalProperty() {
    }

    /**
     * Rental property builder.
     *
     * @param duration                  Duration, in months, of the lease of the property.
     * @param tax                       Tax due annually.
     * @param designation               Designation chosen to represent the financial asset.
     * @param propertyValue             Property value, in currency units.
     * @param rentAmount                Property rental amount.
     * @param monthlyCostCondominium    Monthly condominium cost.
     * @param annualAmountOtherExpenses Annual cost with other property expenses.
     * @param location                  Property location.
     */
    public RentalProperty(int duration, BigDecimal tax, String designation, BigDecimal propertyValue, BigDecimal rentAmount, BigDecimal monthlyCostCondominium, BigDecimal annualAmountOtherExpenses, String location) {
        super(AssetType.PROPERTY, LocalDate.now(), duration, tax, designation);
        if (propertyValue == null || propertyValue.compareTo(new BigDecimal("0")) <= 0)
            throw new IllegalArgumentException();
        if (rentAmount == null || rentAmount.compareTo(new BigDecimal("0")) <= 0)
            throw new IllegalArgumentException();
        if (monthlyCostCondominium == null || monthlyCostCondominium.compareTo(new BigDecimal("0")) < 0)
            throw new IllegalArgumentException();
        if (annualAmountOtherExpenses == null || annualAmountOtherExpenses.compareTo(new BigDecimal("0")) < 0)
            throw new IllegalArgumentException();
        if (location == null) {
            throw new IllegalArgumentException();
        } else if (location.isEmpty()) {
            throw new IllegalArgumentException();
        } else if (location.length() <= 3) {
            throw new IllegalArgumentException();
        }
        this.propertyValue = propertyValue;
        this.rentAmount = rentAmount;
        this.monthlyCostCondominium = monthlyCostCondominium;
        this.annualAmountOtherExpenses = annualAmountOtherExpenses;
        this.location = location;
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
     * This method creates the sequence of investment payments, according to the specifics of each investment.
     *
     * @return A collection of payments.
     */
    @Override
    protected ArrayList<Payment> createPayments() {
        ArrayList<Payment> payments = new ArrayList<>();
        for (int i = 1; i <= this.duration; i++) {
            payments.add(new Payment(this.startDate.plusMonths(i), new BigDecimal("0"), this.rentAmount));
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
     * Method that returns the monetary value of the property.
     *
     * @return Monetary value of the property.
     */
    @Column(name = "ValorImovel", nullable = false)
    public BigDecimal getPropertyValue() {
        return propertyValue;
    }

    /**
     * Method that returns the monthly monetary value of the rent.
     *
     * @return Monetary value of the rent.
     */
    @Column(name = "renda", nullable = false)
    public BigDecimal getRentAmount() {
        return rentAmount;
    }

    /**
     * Method that returns the monthly monetary cost of the condominium.
     *
     * @return Monthly monetary cost of the condominium.
     */
    @Column(name = "CustoMensalCondominio", nullable = false)
    public BigDecimal getMonthlyCostCondominium() {
        return monthlyCostCondominium;
    }

    /**
     * Method to obtain the annual cost with other expenses of the property.
     *
     * @return Monetary cost with other expenses.
     */
    @Column(name = "CustoAnualOutrasDespesas", nullable = false)
    public BigDecimal getAnnualAmountOtherExpenses() {
        return annualAmountOtherExpenses;
    }

    /**
     * Method to obtain the location of the property.
     *
     * @return Property location.
     */
    @Column(name = "Localizacao", nullable = false)
    public String getLocation() {
        return location;
    }

    /**
     * Method for changing the property value.
     *
     * @param propertyValue New value, in monetary units, of the property.
     */
    public void setPropertyValue(BigDecimal propertyValue) {
        if (propertyValue == null || propertyValue.compareTo(new BigDecimal("0")) <= 0)
            throw new IllegalArgumentException();
        this.propertyValue = propertyValue;
    }


    /**
     * Method to change the monthly rental value of the property.
     *
     * @param rentAmount New monthly value, in monetary units, of the rental of the property.
     */
    public void changeRentAmount(BigDecimal rentAmount) {
        if (rentAmount == null || rentAmount.compareTo(new BigDecimal("0")) <= 0)
            throw new IllegalArgumentException();
        this.rentAmount = rentAmount;
        this.payments = createPayments();
    }

    /**
     * Method to change the monthly rental value of the property. exclusive use of ORM.
     *
     * @param rentAmount New monthly value, in monetary units, of the rental of the property.
     */
    private void setRentAmount(BigDecimal rentAmount){
        this.rentAmount = rentAmount;
    }

    /**
     * Method to change the monthly cost with condominium.
     *
     * @param monthlyCostCondominium New monthly cost, in monetary unit, of the condominium.
     */
    public void setMonthlyCostCondominium(BigDecimal monthlyCostCondominium) {
        if (monthlyCostCondominium == null || monthlyCostCondominium.compareTo(new BigDecimal("0")) < 0)
            throw new IllegalArgumentException();
        this.monthlyCostCondominium = monthlyCostCondominium;
    }

    /**
     * Method to change the annual cost with other expenses.
     *
     * @param annualAmountOtherExpenses New annual cost, in monetary unit, with other expenses.
     */
    public void setAnnualAmountOtherExpenses(BigDecimal annualAmountOtherExpenses) {
        if (annualAmountOtherExpenses == null || annualAmountOtherExpenses.compareTo(new BigDecimal("0")) < 0)
            throw new IllegalArgumentException();
        this.annualAmountOtherExpenses = annualAmountOtherExpenses;
    }

    /**
     * Method to change the location of a property.
     *
     * @param location New location.
     */
    public void setLocation(String location) {
        if (location == null) {
            throw new IllegalArgumentException();
        } else if (location.isEmpty()) {
            throw new IllegalArgumentException();
        } else if (location.length() <= 3) {
            throw new IllegalArgumentException();
        }
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RentalProperty)) return false;
        if (!super.equals(o)) return false;
        RentalProperty that = (RentalProperty) o;
        return getPropertyValue().equals(that.getPropertyValue()) && getRentAmount().equals(that.getRentAmount()) && getMonthlyCostCondominium().equals(that.getMonthlyCostCondominium()) && getAnnualAmountOtherExpenses().equals(that.getAnnualAmountOtherExpenses()) && getLocation().equals(that.getLocation());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPropertyValue(), getRentAmount(), getMonthlyCostCondominium(), getAnnualAmountOtherExpenses(), getLocation());
    }

    @Transient
    @Override
    public BigDecimal getAmountInvested() {
        return this.propertyValue;
    }

    @Override
    public int compareTo(FinancialAsset financialAsset) {
        return this.propertyValue.compareTo(((AssetWithInvestedValue) financialAsset).getAmountInvested());
    }
}
