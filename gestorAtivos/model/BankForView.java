package model;

import javafx.scene.image.ImageView;

public class BankForView {

    private ImageView img;
    private String bankName;
    private String totalCautioned;
    private String interestPaid;
    private Bank bank;

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public ImageView getImg() {
        return img;
    }

    public void setImg(ImageView img) {
        this.img = img;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getTotalCautioned() {
        return totalCautioned;
    }

    public void setTotalCautioned(String totalCautioned) {
        this.totalCautioned = totalCautioned;
    }

    public String getInterestPaid() {
        return interestPaid;
    }

    public void setInterestPaid(String interestPaid) {
        this.interestPaid = interestPaid;
    }
}
