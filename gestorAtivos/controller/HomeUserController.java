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
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.ResourceBundle;

import static model.Session.getCurrentUser;

public class HomeUserController implements Initializable {

    @FXML
    private Label username;

    @FXML
    private PieChart chart;

    @FXML
    private Label totalAmount;

    private BigDecimal totalImovel;
    private BigDecimal totalFound;
    private BigDecimal totalDeposit;
    private BigDecimal totalAssets;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DecimalFormatSymbols dfs = new DecimalFormatSymbols(new Locale("pt","Portugal"));
        dfs.setDecimalSeparator(',');
        dfs.setGroupingSeparator('.');
        DecimalFormat df = new DecimalFormat("€ ###,##0.00",dfs);

       username.setText(getCurrentUser().getUsername());
        startAmounts();
        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList(
                new PieChart.Data("Imóveis Arrendados", totalImovel.doubleValue()),
                new PieChart.Data("Fundos de Investimento", totalFound.doubleValue()),
                new PieChart.Data("Depósitos a Termo", totalDeposit.doubleValue())
        );
        chart.setData(pieData);
        totalAmount.setText(df.format(totalAssets));
    }

    private void startAmounts(){
        totalImovel = BigDecimal.ZERO;
        totalFound = BigDecimal.ZERO;
        totalDeposit = BigDecimal.ZERO;
        totalAssets = BigDecimal.ZERO;

        for (FinancialAsset financialAsset : getCurrentUser().getFinancialAssets()){
            if (financialAsset instanceof TermDeposit){
                totalDeposit = totalDeposit.add(((AssetWithInvestedValue)financialAsset).getAmountInvested());
            }else if (financialAsset instanceof RentalProperty){
                totalImovel = totalImovel.add(((AssetWithInvestedValue)financialAsset).getAmountInvested());
            }else if (financialAsset instanceof InvestmentFund){
                totalFound = totalFound.add(((AssetWithInvestedValue)financialAsset).getAmountInvested());
            }
            totalAssets = totalAssets.add(((AssetWithInvestedValue)financialAsset).getAmountInvested());
        }
    }

}
