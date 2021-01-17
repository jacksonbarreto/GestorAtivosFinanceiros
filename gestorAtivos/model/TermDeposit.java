package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

import static dao.DataBase.findBankByName;
import static java.math.BigDecimal.ROUND_HALF_UP;

public class TermDeposit extends FinancialAsset implements AssetWithInvestedValue {

    private BigDecimal depositedAmount;
    private BigDecimal annualProfitability;
    private String account;
    private transient Bank bank;


    /**
     * term deposit builder.
     *
     * @param duration            Duration, in months, of the investment.
     * @param tax                 Annual tax on term deposit.
     * @param designation         Designation chosen to represent the financial asset.
     * @param depositedAmount     Monetary representation of the amount deposited.
     * @param annualProfitability Annual return on investment.
     * @param account             bank account identifying the term deposit.
     * @param bank                Banking institution where the term deposit was made.
     */
    public TermDeposit(int duration, BigDecimal tax, String designation, BigDecimal depositedAmount, BigDecimal annualProfitability, String account, Bank bank) {
        super(AssetType.DEPOSIT, LocalDate.now(), duration, tax, designation);
        if (depositedAmount == null || depositedAmount.compareTo(new BigDecimal("0")) <= 0)
            throw new IllegalArgumentException();
        if (annualProfitability == null)
            throw new IllegalArgumentException();
        if (account == null) {
            throw new IllegalArgumentException();
        } else if (account.isEmpty()) {
            throw new IllegalArgumentException();
        } else if (account.length() <= 3) {
            throw new IllegalArgumentException();
        }
        if (bank == null)
            throw new IllegalArgumentException();
        this.depositedAmount = depositedAmount;
        this.annualProfitability = annualProfitability;
        this.account = account;
        this.bank = bank;
        bank.addDeposit(this);
        this.payments = this.createPayments();
    }

    /**
     * builder for cloning.
     * @param termDeposit Instance to be cloned.
     */
    public TermDeposit(TermDeposit termDeposit){
        super(termDeposit.getAssetType(), termDeposit.getStartDate(), termDeposit.getDuration(), termDeposit.getTax(), termDeposit.getDesignation());
        this.depositedAmount = termDeposit.getDepositedAmount();
        this.annualProfitability = termDeposit.getAnnualProfitability();
        this.account = termDeposit.getAccount();
        this.bank = termDeposit.getBank();
        this.payments = termDeposit.getPayments();
    }

    /**
     * Method for changing the duration of the investment.
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
     * This method recalculates payments. Must be used when making a change to the monthly fee for a payment.
     */
    private void recalculatePayments() {
        ArrayList<Payment> payments = new ArrayList<>();
        BigDecimal interestReceived;
        BigDecimal amount = new BigDecimal(depositedAmount.toString());
        for (Payment payment : this.payments) {
            interestReceived = amount.multiply(payment.getMonthlyProfitability());
            payments.add(new Payment(payment.getDateOfPayment(), payment.getMonthlyProfitability(), interestReceived));
            amount = amount.add(interestReceived);
        }
        this.payments = payments;
    }

    /**
     * This method creates the sequence of investment payments, according to the specifics of each investment.
     *
     * @return A collection of payments.
     */
    @Override
    protected ArrayList<Payment> createPayments() {
        ArrayList<Payment> payments = new ArrayList<>();
        BigDecimal amountPaid;
        BigDecimal amount = new BigDecimal(depositedAmount.toString());

        for (int i = 1; i <= this.duration; i++) {
            amountPaid = amount.multiply(this.annualProfitability.divide(new BigDecimal(String.valueOf(12)), 8, ROUND_HALF_UP));
            payments.add(new Payment(this.startDate.plusMonths(i), annualProfitability.divide(new BigDecimal(String.valueOf(12)), 8, ROUND_HALF_UP), amountPaid));
            amount = amount.add(amountPaid);
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
        //apagar tudo no banco antes
        this.payments = this.createPayments();
    }

    /**
     * Method to obtain the amount deposited in the financial asset.
     *
     * @return Amount deposited, in monetary units, in the financial asset.
     */
    public BigDecimal getDepositedAmount() {
        return new BigDecimal(this.depositedAmount.toString());
    }

    /**
     * Method to obtain the amount deposited in the financial asset.
     * Method implemented by virtue of the interface, for compatibility of functions.
     *
     * @return Amount deposited, in monetary units, in the financial asset.
     */
    @Override
    public BigDecimal getAmountInvested() {
        return this.getDepositedAmount();
    }

    /**
     * Method to obtain the standard annual profitability of the financial asset.
     *
     * @return Standard annual return on financial assets.
     */
    public BigDecimal getAnnualProfitability() {
        return new BigDecimal(this.annualProfitability.toString());
    }

    /**
     * Method to obtain the term deposit account.
     *
     * @return The term deposit account.
     */
    public String getAccount() {
        return new String(account.toString());
    }

    /**
     * Method to obtain the bank responsible for the term deposit.
     *
     * @return The bank responsible for the term deposit.
     */
    public Bank getBank() {
        return bank.clone();
    }

    /**
     * Method to change the amount deposited. Automatically recalculates payments.
     * If any monthly profitability has been changed individually, this will be maintained.
     *
     * @param depositedAmount New amount deposited.
     */
    public void changeDepositedAmount(BigDecimal depositedAmount) {
        if (depositedAmount == null || depositedAmount.compareTo(new BigDecimal("0")) <= 0)
            throw new IllegalArgumentException();
        this.depositedAmount = new BigDecimal(depositedAmount.toString());
        this.payments = createPayments();
    }

    /**
     * Method to change the annual return on the term deposit.
     *
     * @param annualProfitability New annual income, in percentage.
     */
    public void changeAnnualProfitability(BigDecimal annualProfitability) {
        if (annualProfitability == null)
            throw new IllegalArgumentException();
        this.annualProfitability = new BigDecimal(annualProfitability.toString());
        this.payments = createPayments();
    }


    /**
     * Method for changing the account that identifies the term deposit.
     *
     * @param account New account.
     */
    public void setAccount(String account) {
        if (account == null) {
            throw new IllegalArgumentException();
        } else if (account.isEmpty()) {
            throw new IllegalArgumentException();
        } else if (account.length() <= 3) {
            throw new IllegalArgumentException();
        }
        this.account = new String(account.toString());
    }

    /**
     * Method to change the bank responsible for the term deposit.
     *
     * @param bank New bank responsible for term deposit.
     */
    public void setBank(Bank bank) {
        if (bank == null)
            throw new IllegalArgumentException();
        this.bank = bank;
    }

    @Override
    public TermDeposit clone(){
        return new TermDeposit(this);
    }

    /**
     * Method to customize the recording of the object,
     * ensuring that the bank name is saved as a string, for future recovery, avoiding circular dependency.
     *
     * @param oos ObjectOutputStream.
     */
    private void writeObject(ObjectOutputStream oos) {
        try {
            oos.defaultWriteObject();
            oos.writeUTF(bank.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to customize the recovery of an object from the file,
     * ensuring that the correct bank can be assigned, using the bank name saved as a string.
     *
     * @param ois ObjectInputStream.
     */
    private void readObject(ObjectInputStream ois) {
        try {
            ois.defaultReadObject();
            this.bank = findBankByName(ois.readUTF());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TermDeposit)) return false;
        if (!super.equals(o)) return false;
        TermDeposit that = (TermDeposit) o;
        return getDepositedAmount().equals(that.getDepositedAmount()) && getAnnualProfitability().equals(that.getAnnualProfitability()) && getAccount().equals(that.getAccount()) && getBank().equals(that.getBank());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getDepositedAmount(), getAnnualProfitability(), getAccount(), getBank());
    }

    @Override
    public int compareTo(FinancialAsset financialAsset) {
        return this.depositedAmount.compareTo(((AssetWithInvestedValue) financialAsset).getAmountInvested());
    }
}
