package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import model.*;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;

import static controller.Utils.euroCurrency;
import static model.Session.getCurrentUser;

public class HomeUserController implements Initializable {

    @FXML
    private Label username;

    @FXML
    private PieChart chart;

    @FXML
    private Label totalAmount;

    private BigDecimal totalInvestedInRealEstate;
    private BigDecimal totalInvestedInInvestmentFunds;
    private BigDecimal totalInvestedInTermDeposits;
    private BigDecimal totalInvestedInFinancialAssets;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        username.setText(getCurrentUser().getUsername());
        startAmounts();
        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList(
                new PieChart.Data("Imóveis Arrendados", totalInvestedInRealEstate.doubleValue()),
                new PieChart.Data("Fundos de Investimento", totalInvestedInInvestmentFunds.doubleValue()),
                new PieChart.Data("Depósitos a Termo", totalInvestedInTermDeposits.doubleValue())
        );
        chart.setData(pieData);
        totalAmount.setText(euroCurrency.format(totalInvestedInFinancialAssets));
    }

    private void startAmounts() {
        totalInvestedInRealEstate = BigDecimal.ZERO;
        totalInvestedInInvestmentFunds = BigDecimal.ZERO;
        totalInvestedInTermDeposits = BigDecimal.ZERO;
        totalInvestedInFinancialAssets = BigDecimal.ZERO;

        for (FinancialAsset financialAsset : getCurrentUser().getFinancialAssets()) {
            if (financialAsset instanceof TermDeposit) {
                totalInvestedInTermDeposits = totalInvestedInTermDeposits.add(((AssetWithInvestedValue) financialAsset).getAmountInvested());
            } else if (financialAsset instanceof RentalProperty) {
                totalInvestedInRealEstate = totalInvestedInRealEstate.add(((AssetWithInvestedValue) financialAsset).getAmountInvested());
            } else if (financialAsset instanceof InvestmentFund) {
                totalInvestedInInvestmentFunds = totalInvestedInInvestmentFunds.add(((AssetWithInvestedValue) financialAsset).getAmountInvested());
            }
            totalInvestedInFinancialAssets = totalInvestedInFinancialAssets.add(((AssetWithInvestedValue) financialAsset).getAmountInvested());
        }
    }

}
