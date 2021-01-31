package controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import model.*;

import java.math.BigDecimal;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static dao.DataBase.banks;
import static model.Session.getCurrentUser;

public class ListAssetsController implements Initializable {

    @FXML
    private VBox vbox;

    @FXML
    private TableView<AssetForList> tableAssets;

    @FXML
    private TableColumn<AssetForList, ImageView> colIcon;

    @FXML
    private TableColumn<AssetForList, String> colName;

    @FXML
    private TableColumn<AssetForList, BigDecimal> colAmaunt;


    @FXML
    private VBox alllAssetsScreen;
    @FXML
    private VBox infoAssetScreen;

    @FXML
    private TextField designation;

    @FXML
    private DatePicker startDate;

    @FXML
    private TextField duration;

    @FXML
    private TextField tax;

    @FXML
    private StackPane assetTypeScreen;

    @FXML
    private VBox paneImovel;

    @FXML
    private TextField monthlyCostCondominium;

    @FXML
    private TextField rentAmount;

    @FXML
    private TextField propertyValue;

    @FXML
    private TextField annualAmountOtherExpenses;

    @FXML
    private TextField location;

    @FXML
    private VBox paneFound;

    @FXML
    private TextField amountInvested;

    @FXML
    private TextField monthlyProfitability;

    @FXML
    private VBox paneTermeDeposit;

    @FXML
    private TextField depositedAmount;

    @FXML
    private TextField annualProfitability;

    @FXML
    private TextField account;

    @FXML
    private ChoiceBox<Bank> bank;

    @FXML
    private JFXButton btnBack;

    @FXML
    private JFXButton btnSave;
    @FXML
    private VBox successScreen;
    @FXML
    private VBox erroUpdateScreen;
    @FXML
    private Label msgError;
    @FXML
    private Label netProfit;

    @FXML
    private Label avgNetProfit;

    @FXML
    private Label grossProfit;

    @FXML
    private Label avgGrossProfit;

    @FXML
    private TableView<PaymentForList> tablePayments;

    @FXML
    private TableColumn<PaymentForList, ?> dateOfPayment;

    @FXML
    private TableColumn<PaymentForList, ?> interestReceived;
    @FXML
    private TableColumn<PaymentForList, ?> monthlyProfit;

    FinancialAsset financialAssetGeneric;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        startTable();
        bank.getItems().addAll(FXCollections.observableArrayList(banks));
    }

    private void startTable(){
        hideScreens();
        hidePaneAssets();
        alllAssetsScreen.setVisible(true);
        colIcon.setCellValueFactory(new PropertyValueFactory<>("icon"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAmaunt.setCellValueFactory(new PropertyValueFactory<>("amount"));
        addButtonToTable();
        tableAssets.setItems(getAssets());

        dateOfPayment.setCellValueFactory(new PropertyValueFactory<>("dateOfPayment"));
        interestReceived.setCellValueFactory(new PropertyValueFactory<>("interestReceived"));
        monthlyProfit.setCellValueFactory(new PropertyValueFactory<>("monthlyProfitability"));
    }

    private ObservableList<AssetForList> getAssets() {
        ObservableList<AssetForList> assets = FXCollections.observableArrayList();
        assets.addAll(getAssetForList());
        return assets;
    }

    public List<AssetForList> getAssetForList() {
        DecimalFormatSymbols dfs = new DecimalFormatSymbols(new Locale("pt","Portugal"));
        dfs.setDecimalSeparator(',');
        dfs.setGroupingSeparator('.');
        DecimalFormat df = new DecimalFormat("€ ###,##0.00",dfs);

        List<AssetForList> assetForLists = new ArrayList<>();

        for (FinancialAsset fa : getCurrentUser().getFinancialAssetsreverseOrderActiveForUpdate()) {
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

    private ObservableList<PaymentForList> getPayments(FinancialAsset financialAsset) {
        ObservableList<PaymentForList> payments = FXCollections.observableArrayList();
        payments.addAll(getPaymentsForList(financialAsset));
        return payments;
    }
    public List<PaymentForList> getPaymentsForList(FinancialAsset financialAsset) {
        List<PaymentForList> payments = new ArrayList<>();
        DecimalFormatSymbols dfs = new DecimalFormatSymbols(new Locale("pt","Portugal"));
        dfs.setDecimalSeparator(',');
        dfs.setGroupingSeparator('.');
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        DecimalFormat df = new DecimalFormat("€ ###,##0.00",dfs);
        DecimalFormat dfP = new DecimalFormat("##0.00 %",dfs);

        for (Payment payment : financialAsset.getPayments()){
            PaymentForList paymentForList = new PaymentForList();
            paymentForList.setInterestReceived(df.format(payment.getInterestReceived()));
            paymentForList.setMonthlyProfitability(dfP.format(payment.getMonthlyProfitability()));
            paymentForList.setDateOfPayment(dtf.format(payment.getDateOfPayment()));
            payments.add(paymentForList);
        }
        return payments;
    }

    private void addButtonToTable() {
        TableColumn<AssetForList, Void> colBtn = new TableColumn("MAIS INFORMAÇÕES");

        Callback<TableColumn<AssetForList, Void>, TableCell<AssetForList, Void>> cellFactory = new Callback<TableColumn<AssetForList, Void>, TableCell<AssetForList, Void>>() {
            @Override
            public TableCell<AssetForList, Void> call(final TableColumn<AssetForList, Void> param) {
                final TableCell<AssetForList, Void> cell = new TableCell<AssetForList, Void>() {

                    private final Button btn = new Button("+");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            FinancialAsset financialAsset = getTableView().getItems().get(getIndex()).getFinancialAsset();
                            fillFields(financialAsset);
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };
        colBtn.setCellFactory(cellFactory);

        tableAssets.getColumns().add(colBtn);
    }

    private void fillFields(FinancialAsset financialAsset) {
        DecimalFormatSymbols dfs = new DecimalFormatSymbols(new Locale("pt","Portugal"));
        dfs.setDecimalSeparator(',');
        dfs.setGroupingSeparator('.');
        DecimalFormat df = new DecimalFormat("###,##0.00",dfs);

        hideScreens();
        hidePaneAssets();
        financialAssetGeneric = financialAsset;
        designation.setText(financialAsset.getDesignation());
        startDate.setValue(financialAsset.getStartDate());
        duration.setText(String.valueOf(financialAsset.getDuration()));
        tax.setText(financialAsset.getTax().toString());

        infoAssetScreen.setVisible(true);

        if (financialAsset instanceof TermDeposit) {
            depositedAmount.setText(((TermDeposit) financialAsset).getAmountInvested().toString());
            annualProfitability.setText(((TermDeposit) financialAsset).getAnnualProfitability().toString());
            account.setText(((TermDeposit) financialAsset).getAccount());
            bank.setValue(((TermDeposit) financialAsset).getBank());
            paneTermeDeposit.setVisible(true);
        } else if (financialAsset instanceof InvestmentFund) {
            amountInvested.setText(((InvestmentFund) financialAsset).getAmountInvested().toString());
            monthlyProfitability.setText(((InvestmentFund) financialAsset).getMonthlyProfitability().toString());
            paneFound.setVisible(true);
        } else if (financialAsset instanceof RentalProperty) {
            monthlyCostCondominium.setText(((RentalProperty) financialAsset).getMonthlyCostCondominium().toString());
            rentAmount.setText(((RentalProperty) financialAsset).getRentAmount().toString());
            propertyValue.setText(((RentalProperty) financialAsset).getPropertyValue().toString());
            annualAmountOtherExpenses.setText(((RentalProperty) financialAsset).getAnnualAmountOtherExpenses().toString());
            location.setText(((RentalProperty) financialAsset).getLocation());
            paneImovel.setVisible(true);
        }


        netProfit.setText(df.format(financialAsset.getNetProfit()));
        avgNetProfit.setText(df.format(financialAsset.getAverageMonthlyNetProfit()));
        grossProfit.setText(df.format(financialAsset.getGrossProfit()));
        avgGrossProfit.setText(df.format(financialAsset.getAverageMonthlyGrossProfit()));

        tablePayments.setItems(getPayments(financialAsset));

    }

    @FXML
    void updateAsset() {
        try {
            financialAssetGeneric.setDesignation(designation.getText());

            if (financialAssetGeneric instanceof TermDeposit) {
                ((TermDeposit) financialAssetGeneric).changeDepositedAmount(new BigDecimal(depositedAmount.getText()));
                ((TermDeposit) financialAssetGeneric).changeAnnualProfitability(new BigDecimal(annualProfitability.getText()));
                ((TermDeposit) financialAssetGeneric).setAccount(account.getText());
                ((TermDeposit) financialAssetGeneric).setBank(bank.getValue());
                ((TermDeposit) financialAssetGeneric).changeDuration(Integer.parseInt(duration.getText()));
                ((TermDeposit) financialAssetGeneric).changeStartDate(startDate.getValue());
            } else if (financialAssetGeneric instanceof InvestmentFund) {
                ((InvestmentFund) financialAssetGeneric).changeAmountInvested(new BigDecimal(amountInvested.getText()));
                ((InvestmentFund) financialAssetGeneric).setMonthlyProfitability(new BigDecimal(monthlyProfitability.getText()));
                ((InvestmentFund) financialAssetGeneric).changeDuration(Integer.parseInt(duration.getText()));
                ((InvestmentFund) financialAssetGeneric).changeStartDate(startDate.getValue());
            } else if (financialAssetGeneric instanceof RentalProperty) {
                ((RentalProperty) financialAssetGeneric).setMonthlyCostCondominium(new BigDecimal(monthlyCostCondominium.getText()));
                ((RentalProperty) financialAssetGeneric).changeRentAmount(new BigDecimal(rentAmount.getText()));
                ((RentalProperty) financialAssetGeneric).setPropertyValue(new BigDecimal(propertyValue.getText()));
                ((RentalProperty) financialAssetGeneric).setAnnualAmountOtherExpenses(new BigDecimal(annualAmountOtherExpenses.getText()));
                ((RentalProperty) financialAssetGeneric).setLocation(location.getText());
                ((RentalProperty) financialAssetGeneric).changeDuration(Integer.parseInt(duration.getText()));
                ((RentalProperty) financialAssetGeneric).changeStartDate(startDate.getValue());
            }
            hideScreens();
            successScreen.setVisible(true);
        }catch (Exception e){
            hideScreens();
            hidePaneAssets();
            msgError.setText(e.getMessage());
            erroUpdateScreen.setVisible(true);
        }
    }

    @FXML
    void back() {
        hideScreens();
        hidePaneAssets();
        alllAssetsScreen.setVisible(true);
        tableAssets.setItems(getAssets());
    }

    private void hidePaneAssets() {
        paneTermeDeposit.setVisible(false);
        paneFound.setVisible(false);
        paneImovel.setVisible(false);
    }

    private void hideScreens() {
        alllAssetsScreen.setVisible(false);
        infoAssetScreen.setVisible(false);
        successScreen.setVisible(false);
        erroUpdateScreen.setVisible(false);
    }
}
