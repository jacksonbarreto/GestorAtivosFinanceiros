package model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.math.BigDecimal.ROUND_HALF_UP;

public abstract class FinancialAsset implements Serializable {

    protected AssetType assetType;
    protected LocalDate startDate;
    protected int duration;
    protected BigDecimal tax;
    protected String designation;
    protected List<Payment> payments;

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
        if (startDate == null)
            throw new IllegalArgumentException();
        if (tax == null || tax.compareTo(new BigDecimal("0")) < 0)
            throw new IllegalArgumentException();
        if (designation == null) {
            throw new IllegalArgumentException();
        } else if (designation.isEmpty()) {
            throw new IllegalArgumentException();
        } else if (designation.length() <= 3) {
            throw new IllegalArgumentException();
        }
        this.startDate = startDate;
        this.duration = duration;
        this.tax = tax;
        this.designation = designation;
        this.assetType = assetType;
    }


    /**
     * This method returns the gross profit, that is,
     * the amount invested initially plus interest for the investment period, without considering the tax discount.
     *
     * @return the gross profit
     */
    public BigDecimal getGrossProfit() {
        BigDecimal grossProfit = new BigDecimal("0");
        for (Payment p : this.payments) {
            grossProfit = grossProfit.add(p.getInterestReceived());
        }
        return grossProfit.setScale(2, ROUND_HALF_UP);
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
        return netProfit.setScale(2, ROUND_HALF_UP);
    }

    /**
     * This method returns the average monthly net profit.
     *
     * @return average monthly net profit.
     */
    public BigDecimal getAverageMonthlyNetProfit() {
        return this.getNetProfit().divide(new BigDecimal(String.valueOf(this.duration)), 2, ROUND_HALF_UP);
    }

    /**
     * This method returns the average monthly gross profit.
     *
     * @return average monthly gross profit.
     */
    public BigDecimal getAverageMonthlyGrossProfit() {
        return this.getGrossProfit().divide(new BigDecimal(String.valueOf(this.duration)), 2, ROUND_HALF_UP);
    }


    /**
     * This method creates the sequence of investment payments, according to the specifics of each investment.
     *
     * @return A collection of payments.
     */
    protected abstract ArrayList<Payment> createPayments();

    /**
     * Method for obtaining the start date of the financial asset.
     *
     * @return Financial asset start date.
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * Method allows changing the start date of the financial asset.
     *
     * @param startDate Financial asset start date.
     */
    protected void setStartDate(LocalDate startDate) {
        if (startDate == null)
            throw new IllegalArgumentException();
        this.startDate = startDate;
    }

    /**
     * Method that obtains the duration of the Financial Asset.
     *
     * @return Duration, in months, of the Financial Asset.
     */
    public int getDuration() {
        return duration;
    }

    /**
     * The method changes the duration of the financial asset.     *
     *
     * @param duration New duration, in months, of the financial asset.
     */
    protected void setDuration(int duration) {
        if (duration <= 0)
            throw new IllegalArgumentException();
        this.duration = duration;
    }


    /**
     * Method for obtaining the annual tax index.
     *
     * @return Annual percentage of tax.
     */
    public BigDecimal getTax() {
        return tax;
    }

    /**
     * Method to change the annual tax.
     *
     * @param tax percentage annual tax.
     */
    public void defineNewTax(BigDecimal tax) {
        if (tax == null || tax.compareTo(new BigDecimal("0")) < 0)
            throw new IllegalArgumentException();
        this.tax = tax;
    }


    /**
     * Method for obtaining the designation of the financial asset.
     *
     * @return Designation of the financial asset.
     */
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
        } else if (designation.length() <= 3) {
            throw new IllegalArgumentException();
        }
        this.designation = designation;
    }

    /**
     * Method for obtaining all payments on the financial asset.
     *
     * @return Financial asset payment collection.
     */
    public List<Payment> getPayments() {
        return payments;
    }


    /**
     * Method that returns the type of the financial asset.
     *
     * @return Type of financial asset.
     */
    public AssetType getAssetType() {
        return assetType;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FinancialAsset)) return false;
        FinancialAsset that = (FinancialAsset) o;
        return getDuration() == that.getDuration() && getAssetType() == that.getAssetType() && getStartDate().equals(that.getStartDate()) && getTax().equals(that.getTax()) && getDesignation().equals(that.getDesignation()) && getPayments().equals(that.getPayments());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAssetType(), getStartDate(), getDuration(), getTax(), getDesignation(), getPayments());
    }
}
