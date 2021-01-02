package model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

import static java.math.BigDecimal.ROUND_HALF_UP;
import static model.AssetType.FOUND;

@Entity
@Table(name = "Fundo")
@Access(AccessType.PROPERTY)
@DiscriminatorValue(value = "FOUND")
@PrimaryKeyJoinColumn(name = "id")
public class InvestmentFund extends FinancialAsset implements AssetWithInvestedValue {

    private BigDecimal amountInvested;
    private BigDecimal monthlyProfitability;

    public InvestmentFund(LocalDate startDate, int duration, BigDecimal tax, String designation, ArrayList<Payment> payments, BigDecimal amountInvested, BigDecimal monthlyProfitability) {
        super(FOUND, startDate, duration, tax, designation, payments);
        this.amountInvested = amountInvested;
        this.monthlyProfitability = monthlyProfitability;
    }

    public InvestmentFund(int duration, BigDecimal tax, String designation, BigDecimal amountInvested, BigDecimal monthlyProfitability) {
        super(FOUND, LocalDate.now(), duration, tax, designation, new ArrayList<>());
        this.amountInvested = amountInvested;
        this.monthlyProfitability = monthlyProfitability;
        this.payments = this.createPayments();
    }

    /**
     * This method returns the gross profit, that is,
     * the amount invested initially plus interest for the investment period, without considering the tax discount.
     *
     * @return the gross profit
     */
    public BigDecimal getGrossProfit() {
        BigDecimal grossProfit = new BigDecimal(String.valueOf(this.amountInvested));
        for (Payment p : this.payments) {
            grossProfit = grossProfit.add(grossProfit.multiply(p.getMonthlyProfitability()));
        }
        return grossProfit.subtract(this.amountInvested).setScale(2,ROUND_HALF_UP);
    }

    /**
     * This method returns net income, that is, the amount invested initially plus interest for the investment period, after tax.
     *
     * @return net profit from financial asset
     */
    public BigDecimal getNetProfit() {
        BigDecimal grossProfit = this.getGrossProfit();
        BigDecimal netProfit = new BigDecimal(String.valueOf(grossProfit));

        if (grossProfit.compareTo(new BigDecimal("0")) > 0) {
            netProfit = netProfit.subtract(grossProfit.multiply(this.tax));
        }
        return netProfit.setScale(2,ROUND_HALF_UP);
    }

    /**
     * This method returns the average monthly net profit.
     *
     * @return average monthly net profit.
     */
    public BigDecimal getAverageMonthlyNetProfit(){
        return this.getNetProfit().divide(new BigDecimal(String.valueOf(this.duration)), 2, ROUND_HALF_UP);
    }

    /**
     * This method returns the average monthly gross profit.
     *
     * @return average monthly gross profit.
     */
    public BigDecimal getAverageMonthlyGrossProfit(){
        return this.getGrossProfit().divide(new BigDecimal(String.valueOf(this.duration)), 2, ROUND_HALF_UP);
    }

    @Override
    protected ArrayList<Payment> createPayments() {
        ArrayList<Payment> payments = new ArrayList<>();
        BigDecimal amountPaid;
        BigDecimal amount = new BigDecimal(amountInvested.toString());

        for (int i = 1; i <= this.duration; i++) {
            amountPaid = amount.multiply(this.monthlyProfitability);
            payments.add(new Payment(this, this.startDate.plusMonths(i), monthlyProfitability, amountPaid));
            amount = amount.add(amountPaid);
        }
        return payments;
    }

    @Override
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
        this.payments = this.createPayments();
    }

    @Override
    public void setDuration(int duration) {
        this.duration = duration;
        this.payments = this.createPayments();
    }

    @Column(name = "ValorInvestido", nullable = false)
    public BigDecimal getAmountInvested() {
        return amountInvested;
    }

    @Column(name = "RentabilidadeMensal", nullable = false)
    public BigDecimal getMonthlyProfitability() {
        return monthlyProfitability;
    }

    public void setAmountInvested(BigDecimal amountInvested) {
        this.amountInvested = amountInvested;
    }

    public void setMonthlyProfitability(BigDecimal monthlyProfitability) {
        this.monthlyProfitability = monthlyProfitability;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InvestmentFund)) return false;
        if (!super.equals(o)) return false;
        InvestmentFund that = (InvestmentFund) o;
        return getAmountInvested().equals(that.getAmountInvested()) && getMonthlyProfitability().equals(that.getMonthlyProfitability());
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
