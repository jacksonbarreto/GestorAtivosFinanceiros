package model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.math.BigDecimal;


public class AssetForList {
    private ImageView icon;
    private String name;
    private String amount;
    private String netProfit;
    private String avgNetProfit;
    private String grossProfit;
    private String avgGrossProfit;
    private String taxPaid;
    private FinancialAsset financialAsset;

    public AssetForList() {
    }

    public AssetForList(ImageView icon, String name, String amount, String netProfit, String avgNetProfit, String grossProfit, String avgGrossProfit, String taxPaid, FinancialAsset financialAsset) {
        this.icon = icon;
        this.name = name;
        this.amount = amount;
        this.netProfit = netProfit;
        this.avgNetProfit = avgNetProfit;
        this.grossProfit = grossProfit;
        this.avgGrossProfit = avgGrossProfit;
        this.taxPaid = taxPaid;
        this.financialAsset = financialAsset;
    }

    public ImageView getIcon() {
        return icon;
    }

    public void setIcon(ImageView icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getNetProfit() {
        return netProfit;
    }

    public void setNetProfit(String netProfit) {
        this.netProfit = netProfit;
    }

    public String getAvgNetProfit() {
        return avgNetProfit;
    }

    public void setAvgNetProfit(String avgNetProfit) {
        this.avgNetProfit = avgNetProfit;
    }

    public String getGrossProfit() {
        return grossProfit;
    }

    public void setGrossProfit(String grossProfit) {
        this.grossProfit = grossProfit;
    }

    public String getAvgGrossProfit() {
        return avgGrossProfit;
    }

    public void setAvgGrossProfit(String avgGrossProfit) {
        this.avgGrossProfit = avgGrossProfit;
    }

    public String getTaxPaid() {
        return taxPaid;
    }

    public void setTaxPaid(String taxPaid) {
        this.taxPaid = taxPaid;
    }

    public FinancialAsset getFinancialAsset() {
        return financialAsset;
    }

    public void setFinancialAsset(FinancialAsset financialAsset) {
        this.financialAsset = financialAsset;
    }

    @Override
    public String toString() {
        return "AssetForList{" +
                "icon=" + icon +
                ", name='" + name + '\'' +
                ", amount=" + amount +
                ", netProfit=" + netProfit +
                ", avgNetProfit=" + avgNetProfit +
                ", grossProfit=" + grossProfit +
                ", avgGrossProfit=" + avgGrossProfit +
                ", taxPaid=" + taxPaid +
                ", financialAsset=" + financialAsset +
                '}';
    }
}
