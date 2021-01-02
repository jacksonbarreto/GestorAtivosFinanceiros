package model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

import static java.math.BigDecimal.ROUND_HALF_UP;

@Entity
@Table(name = "Imovel")
@Access(AccessType.PROPERTY)
@DiscriminatorValue(value = "3")
@PrimaryKeyJoinColumn(name = "id")
public class RentalProperty extends FinancialAsset{

    private BigDecimal propertyValue;
    private BigDecimal rentAmount;
    private BigDecimal monthlyCostCondominium;
    private BigDecimal annualAmountOtherExpenses;
    private String location;

    public RentalProperty(LocalDate startDate, int duration, BigDecimal tax, String designation, ArrayList<Payment> payments, BigDecimal propertyValue, BigDecimal rentAmount, BigDecimal monthlyCostCondominium, BigDecimal annualAmountOtherExpenses, String location) {
        super(AssetType.PROPERTY, startDate, duration, tax, designation, payments);
        this.propertyValue = propertyValue;
        this.rentAmount = rentAmount;
        this.monthlyCostCondominium = monthlyCostCondominium;
        this.annualAmountOtherExpenses = annualAmountOtherExpenses;
        this.location = location;
    }

    public RentalProperty(int duration, BigDecimal tax, String designation, BigDecimal propertyValue, BigDecimal rentAmount, BigDecimal monthlyCostCondominium, BigDecimal annualAmountOtherExpenses, String location) {
        super(AssetType.PROPERTY, LocalDate.now(), duration, tax, designation, new ArrayList<>());
        this.propertyValue = propertyValue;
        this.rentAmount = rentAmount;
        this.monthlyCostCondominium = monthlyCostCondominium;
        this.annualAmountOtherExpenses = annualAmountOtherExpenses;
        this.location = location;
        this.payments = this.createPayments();
    }

    @Override
    public void setDuration(int duration) {
        this.duration = duration;
        this.payments = this.createPayments();
    }

    @Override
    protected ArrayList<Payment> createPayments() {
        ArrayList<Payment> payments = new ArrayList<>();
        for (int i = 1; i <= this.duration; i++) {
            payments.add(new Payment(this, this.startDate.plusMonths(i), new BigDecimal("0"), this.rentAmount));
        }
        return payments;
    }

    @Override
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
        this.payments = this.createPayments();
    }

    @Column(name = "ValorImovel", nullable = false)
    public BigDecimal getPropertyValue() {
        return propertyValue;
    }

    @Column(name = "renda", nullable = false)
    public BigDecimal getRentAmount() {
        return rentAmount;
    }

    @Column(name = "CustoMensalCondominio", nullable = false)
    public BigDecimal getMonthlyCostCondominium() {
        return monthlyCostCondominium;
    }

    @Column(name = "CustoAnualOutrasDespesas", nullable = false)
    public BigDecimal getAnnualAmountOtherExpenses() {
        return annualAmountOtherExpenses;
    }

    @Column(name = "Localizacao", nullable = false)
    public String getLocation() {
        return location;
    }

    public void setPropertyValue(BigDecimal propertyValue) {
        this.propertyValue = propertyValue;
    }

    public void setRentAmount(BigDecimal rentAmount) {
        this.rentAmount = rentAmount;
    }

    public void setMonthlyCostCondominium(BigDecimal monthlyCostCondominium) {
        this.monthlyCostCondominium = monthlyCostCondominium;
    }

    public void setAnnualAmountOtherExpenses(BigDecimal annualAmountOtherExpenses) {
        this.annualAmountOtherExpenses = annualAmountOtherExpenses;
    }

    public void setLocation(String location) {
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
}
