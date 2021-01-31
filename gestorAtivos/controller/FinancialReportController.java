package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import model.*;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import static model.Session.getCurrentUser;

public class FinancialReportController implements Initializable {

    @FXML
    private DatePicker startDate;

    @FXML
    private DatePicker finalDate;

    @FXML
    private Label erroStartDate;

    @FXML
    private Label erroFinalDate;

    @FXML
    private VBox notFoundScreen;

    @FXML
    private VBox resultScreen;

    @FXML
    private TableView<AssetForList> tableAssets;

    @FXML
    private TableColumn<AssetForList, ImageView> icon;

    @FXML
    private TableColumn<AssetForList, String> name;

    @FXML
    private TableColumn<AssetForList, String> netProfit;

    @FXML
    private TableColumn<AssetForList, String> avgNetProfit;

    @FXML
    private TableColumn<AssetForList, String> grossProfit;

    @FXML
    private TableColumn<AssetForList, String> avgGrossProfit;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    void findAssets() {
        hideErroDate();
        hideScreens();
        if (startDate.getValue() == null || finalDate.getValue() == null) {
            if (startDate.getValue() == null) {
                erroStartDate.setVisible(true);
            }
            if (finalDate.getValue() == null) {
                erroFinalDate.setVisible(true);
            }
        } else {
            ObservableList<AssetForList> foundAssets = getAssets(startDate.getValue(), finalDate.getValue());
            if (foundAssets.size() == 0) {
                notFoundScreen.setVisible(true);
            } else {
                resultScreen.setVisible(true);
                icon.setCellValueFactory(new PropertyValueFactory<>("icon"));
                name.setCellValueFactory(new PropertyValueFactory<>("name"));
                netProfit.setCellValueFactory(new PropertyValueFactory<>("netProfit"));
                avgNetProfit.setCellValueFactory(new PropertyValueFactory<>("avgNetProfit"));
                grossProfit.setCellValueFactory(new PropertyValueFactory<>("grossProfit"));
                avgGrossProfit.setCellValueFactory(new PropertyValueFactory<>("avgGrossProfit"));
                tableAssets.setItems(foundAssets);
            }
        }
    }

    private ObservableList<AssetForList> getAssets(LocalDate startDate, LocalDate finalDate) {
        ObservableList<AssetForList> assets = FXCollections.observableArrayList();
        assets.addAll(getAssetForList(startDate, finalDate));
        return assets;
    }

    public List<AssetForList> getAssetForList(LocalDate startDate, LocalDate finalDate) {
        List<AssetForList> assetForLists = new ArrayList<>();

        DecimalFormatSymbols dfs = new DecimalFormatSymbols(new Locale("pt","Portugal"));
        dfs.setDecimalSeparator(',');
        dfs.setGroupingSeparator('.');
        DecimalFormat df = new DecimalFormat("€ ###,##0.00",dfs);

        for (FinancialAsset fa : getCurrentUser().getFinancialAssetsActive(startDate, finalDate)) {
            AssetForList assetForList = new AssetForList();
            assetForList.setAmount(df.format(((AssetWithInvestedValue) fa).getAmountInvested()));
            assetForList.setName(fa.getDesignation());
            assetForList.setNetProfit(df.format(fa.getNetProfit()));
            assetForList.setAvgNetProfit(df.format(fa.getAverageMonthlyNetProfit()));
            assetForList.setGrossProfit(df.format(fa.getGrossProfit()));
            assetForList.setAvgGrossProfit(df.format(fa.getAverageMonthlyGrossProfit()));
            assetForList.setFinancialAsset(fa);
            if (fa instanceof InvestmentFund) {
                assetForList.setIcon(new ImageView(new Image(this.getClass().getResourceAsStream("../img/Invest.png"))));
            } else if (fa instanceof RentalProperty) {
                assetForList.setIcon(new ImageView(new Image(this.getClass().getResourceAsStream("../img/Building.png"))));
            } else if (fa instanceof TermDeposit) {
                assetForList.setIcon(new ImageView(new Image(this.getClass().getResourceAsStream("../img/Bank.png"))));
            }
            assetForLists.add(assetForList);
        }
        return assetForLists;
    }

    private void hideErroDate() {
        erroStartDate.setVisible(false);
        erroFinalDate.setVisible(false);
    }

    private void hideScreens() {
        notFoundScreen.setVisible(false);
        resultScreen.setVisible(false);
    }
}
