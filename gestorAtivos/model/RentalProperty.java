package model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

@Entity
@Table(name = "Imovel")
@Access(AccessType.PROPERTY)
@DiscriminatorValue(value = "3")
@PrimaryKeyJoinColumn(name = "id")
public class RentalProperty extends FinancialAsset{

    private double propertyValue;
    private double rentAmount;
    private double monthlyCostCondominium;
    private double annualAmountOtherExpenses;
    private String location;

    public RentalProperty(LocalDate startDate, int duration, float tax, String designation, ArrayList<Payment> payments, double propertyValue, double rentAmount, double monthlyCostCondominium, double annualAmountOtherExpenses, String location) {
        super(AssetType.PROPERTY, startDate, duration, tax, designation, payments);
        this.propertyValue = propertyValue;
        this.rentAmount = rentAmount;
        this.monthlyCostCondominium = monthlyCostCondominium;
        this.annualAmountOtherExpenses = annualAmountOtherExpenses;
        this.location = location;
    }

    public RentalProperty(int duration, float tax, String designation, double propertyValue, double rentAmount, double monthlyCostCondominium, double annualAmountOtherExpenses, String location) {
        super(AssetType.PROPERTY, LocalDate.now(), duration, tax, designation, new ArrayList<>());
        this.propertyValue = propertyValue;
        this.rentAmount = rentAmount;
        this.monthlyCostCondominium = monthlyCostCondominium;
        this.annualAmountOtherExpenses = annualAmountOtherExpenses;
        this.location = location;
        this.payments = this.createPayments(0);
    }

    @Override
    public void setDuration(int duration) {
        this.duration = duration;
        this.payments = this.createPayments(0);
    }
    @Override
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
        this.payments = this.createPayments(0);
    }

    @Column(name = "ValorImovel", nullable = false)
    public double getPropertyValue() {
        return propertyValue;
    }

    @Column(name = "renda", nullable = false)
    public double getRentAmount() {
        return rentAmount;
    }

    @Column(name = "CustoMensalCondominio", nullable = false)
    public double getMonthlyCostCondominium() {
        return monthlyCostCondominium;
    }

    @Column(name = "CustoAnualOutrasDespesas", nullable = false)
    public double getAnnualAmountOtherExpenses() {
        return annualAmountOtherExpenses;
    }

    @Column(name = "Localizacao", nullable = false)
    public String getLocation() {
        return location;
    }

    public void setPropertyValue(double propertyValue) {
        this.propertyValue = propertyValue;
    }

    public void setRentAmount(double rentAmount) {
        this.rentAmount = rentAmount;
    }

    public void setMonthlyCostCondominium(double monthlyCostCondominium) {
        this.monthlyCostCondominium = monthlyCostCondominium;
    }

    public void setAnnualAmountOtherExpenses(double annualAmountOtherExpenses) {
        this.annualAmountOtherExpenses = annualAmountOtherExpenses;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RentalProperty)) return false;
        RentalProperty that = (RentalProperty) o;
        return Double.compare(that.getPropertyValue(), getPropertyValue()) == 0 && Double.compare(that.getRentAmount(), getRentAmount()) == 0 && Double.compare(that.getMonthlyCostCondominium(), getMonthlyCostCondominium()) == 0 && Double.compare(that.getAnnualAmountOtherExpenses(), getAnnualAmountOtherExpenses()) == 0 && Objects.equals(getId(), that.getId()) && getLocation().equals(that.getLocation());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPropertyValue(), getRentAmount(), getMonthlyCostCondominium(), getAnnualAmountOtherExpenses(), getLocation());
    }
}
