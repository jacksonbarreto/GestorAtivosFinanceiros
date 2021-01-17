package model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;


public class RentalProperty extends FinancialAsset implements AssetWithInvestedValue {

    private BigDecimal propertyValue;
    private BigDecimal rentAmount;
    private BigDecimal monthlyCostCondominium;
    private BigDecimal annualAmountOtherExpenses;
    private String location;


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
     * builder for cloning.
     * @param rentalProperty Instance to be cloned.
     */
    public RentalProperty(RentalProperty rentalProperty){
        super(rentalProperty.getAssetType(), rentalProperty.getStartDate(), rentalProperty.getDuration(), rentalProperty.getTax(), rentalProperty.getDesignation());
        this.propertyValue = rentalProperty.getPropertyValue();
        this.rentAmount = rentalProperty.getRentAmount();
        this.monthlyCostCondominium = rentalProperty.getMonthlyCostCondominium();
        this.annualAmountOtherExpenses = rentalProperty.getAnnualAmountOtherExpenses();
        this.location = rentalProperty.getLocation();
        this.payments = rentalProperty.getPayments();
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
        this.startDate = LocalDate.parse(startDate.toString());
        this.payments = this.createPayments();
    }

    /**
     * Method that returns the monetary value of the property.
     *
     * @return Monetary value of the property.
     */
    public BigDecimal getPropertyValue() {
        return new BigDecimal(this.propertyValue.toString());
    }

    /**
     * Method that returns the monthly monetary value of the rent.
     *
     * @return Monetary value of the rent.
     */
    public BigDecimal getRentAmount() {
        return new BigDecimal(this.rentAmount.toString());
    }

    /**
     * Method that returns the monthly monetary cost of the condominium.
     *
     * @return Monthly monetary cost of the condominium.
     */
    public BigDecimal getMonthlyCostCondominium() {
        return new BigDecimal(this.monthlyCostCondominium.toString());
    }

    /**
     * Method to obtain the annual cost with other expenses of the property.
     *
     * @return Monetary cost with other expenses.
     */
    public BigDecimal getAnnualAmountOtherExpenses() {
        return new BigDecimal(this.annualAmountOtherExpenses.toString());
    }

    /**
     * Method to obtain the location of the property.
     *
     * @return Property location.
     */
    public String getLocation() {
        return new String(this.location.toString());
    }

    /**
     * Method for changing the property value.
     *
     * @param propertyValue New value, in monetary units, of the property.
     */
    public void setPropertyValue(BigDecimal propertyValue) {
        if (propertyValue == null || propertyValue.compareTo(new BigDecimal("0")) <= 0)
            throw new IllegalArgumentException();
        this.propertyValue = new BigDecimal(propertyValue.toString());
    }


    /**
     * Method to change the monthly rental value of the property.
     *
     * @param rentAmount New monthly value, in monetary units, of the rental of the property.
     */
    public void changeRentAmount(BigDecimal rentAmount) {
        if (rentAmount == null || rentAmount.compareTo(new BigDecimal("0")) <= 0)
            throw new IllegalArgumentException();
        this.rentAmount = new BigDecimal(rentAmount.toString());
        this.payments = createPayments();
    }


    /**
     * Method to change the monthly cost with condominium.
     *
     * @param monthlyCostCondominium New monthly cost, in monetary unit, of the condominium.
     */
    public void setMonthlyCostCondominium(BigDecimal monthlyCostCondominium) {
        if (monthlyCostCondominium == null || monthlyCostCondominium.compareTo(new BigDecimal("0")) < 0)
            throw new IllegalArgumentException();
        this.monthlyCostCondominium = new BigDecimal(monthlyCostCondominium.toString());
    }

    /**
     * Method to change the annual cost with other expenses.
     *
     * @param annualAmountOtherExpenses New annual cost, in monetary unit, with other expenses.
     */
    public void setAnnualAmountOtherExpenses(BigDecimal annualAmountOtherExpenses) {
        if (annualAmountOtherExpenses == null || annualAmountOtherExpenses.compareTo(new BigDecimal("0")) < 0)
            throw new IllegalArgumentException();
        this.annualAmountOtherExpenses = new BigDecimal(annualAmountOtherExpenses.toString());
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
        this.location = new String(location.toString());
    }

    @Override
    public RentalProperty clone(){
        return new RentalProperty(this);
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
        return Objects.hash(super.hashCode(), getPropertyValue(), getRentAmount(), getMonthlyCostCondominium(), getAnnualAmountOtherExpenses(), getLocation());
    }

    @Override
    public BigDecimal getAmountInvested() {
        return this.propertyValue;
    }

    @Override
    public int compareTo(FinancialAsset financialAsset) {
        return this.propertyValue.compareTo(((AssetWithInvestedValue) financialAsset).getAmountInvested());
    }
}
