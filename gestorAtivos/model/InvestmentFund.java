package model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

@Entity
@Table(name = "Fundo")
@Access(AccessType.PROPERTY)
@DiscriminatorValue(value = "1")
@PrimaryKeyJoinColumn(name = "id")
public class InvestmentFund extends FinancialAsset {

    private double amountInvested;
    private double monthlyProfitability;

    public InvestmentFund(LocalDate startDate, int duration, float tax, String designation, ArrayList<Payment> payments, double amountInvested, double monthlyProfitability) {
        super(AssetType.FOUND, startDate, duration, tax, designation, payments);
        this.amountInvested = amountInvested;
        this.monthlyProfitability = monthlyProfitability;
    }
    public InvestmentFund( int duration, float tax, String designation, double amountInvested, double monthlyProfitability) {
        super(AssetType.FOUND, LocalDate.now(), duration, tax, designation, new ArrayList<>());
        this.amountInvested = amountInvested;
        this.monthlyProfitability = monthlyProfitability;
        this.payments = this.createPayments(this.monthlyProfitability);
    }


    @Override
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
        this.payments = this.createPayments(this.monthlyProfitability);
    }

    @Override
    public void setDuration(int duration) {
        this.duration = duration;
        this.payments = this.createPayments(this.monthlyProfitability);
    }

    @Column(name = "ValorInvestido", nullable = false)
    public double getAmountInvested() {
        return amountInvested;
    }

    @Column(name = "RentabilidadeMensal", nullable = false)
    public double getMonthlyProfitability() {
        return monthlyProfitability;
    }

    public void setAmountInvested(double amountInvested) {
        this.amountInvested = amountInvested;
    }

    public void setMonthlyProfitability(double monthlyProfitability) {
        this.monthlyProfitability = monthlyProfitability;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InvestmentFund)) return false;
        InvestmentFund that = (InvestmentFund) o;
        return Double.compare(that.getAmountInvested(), getAmountInvested()) == 0 && Double.compare(that.getMonthlyProfitability(), getMonthlyProfitability()) == 0 && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getAmountInvested(), getMonthlyProfitability());
    }
}
