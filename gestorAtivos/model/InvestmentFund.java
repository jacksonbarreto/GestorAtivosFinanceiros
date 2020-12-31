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
public class InvestmentFund extends FinancialAsset implements AssetWithInvestedValue {

    private Double amountInvested;
    private Double monthlyProfitability;

    public InvestmentFund(LocalDate startDate, int duration, float tax, String designation, ArrayList<Payment> payments, Double amountInvested, Double monthlyProfitability) {
        super(AssetType.FOUND, startDate, duration, tax, designation, payments);
        this.amountInvested = amountInvested;
        this.monthlyProfitability = monthlyProfitability;
    }

    public InvestmentFund(int duration, float tax, String designation, Double amountInvested, Double monthlyProfitability) {
        super(AssetType.FOUND, LocalDate.now(), duration, tax, designation, new ArrayList<>());
        this.amountInvested = amountInvested;
        this.monthlyProfitability = monthlyProfitability;
        this.payments = this.createPayments(this.monthlyProfitability);
    }

    /**
     * This method returns the gross profit, that is,
     * the amount invested initially plus interest for the investment period, without considering the tax discount.
     *
     * @return the gross profit
     */
    public Double getGrossProfit() {
        Double grossProfit = this.amountInvested;
        for (Payment p : this.payments) {
            grossProfit += grossProfit * p.getMonthlyProfitability();
        }
        return this.amountInvested - grossProfit;
    }

    /**
     * This method returns net income, that is, the amount invested initially plus interest for the investment period, after tax.
     *
     * @return net profit from financial asset
     */
    public Double getNetProfit() {
        Double grossProfit = this.getGrossProfit();
        Double netProfit = grossProfit;

        if (grossProfit > 0D) {
            netProfit -= grossProfit * this.tax;
        }
        return netProfit;
    }

    /**
     * This method returns the average monthly net profit.
     *
     * @return average monthly net profit.
     */
    public Double getAverageMonthlyNetProfit(){
        return this.getNetProfit()/this.duration;
    }

    /**
     * This method returns the average monthly gross profit.
     *
     * @return average monthly gross profit.
     */
    public Double getAverageMonthlyGrossProfit(){
        return this.getGrossProfit()/this.duration;
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
    public Double getAmountInvested() {
        return amountInvested;
    }

    @Column(name = "RentabilidadeMensal", nullable = false)
    public Double getMonthlyProfitability() {
        return monthlyProfitability;
    }

    public void setAmountInvested(Double amountInvested) {
        this.amountInvested = amountInvested;
    }

    public void setMonthlyProfitability(Double monthlyProfitability) {
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

    @Override
    public int compareTo(FinancialAsset financialAsset) {
        return this.amountInvested.compareTo(((InvestmentFund) financialAsset).getAmountInvested());
    }
}
