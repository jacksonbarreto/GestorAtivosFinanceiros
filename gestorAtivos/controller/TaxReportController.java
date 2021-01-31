package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import model.*;

import java.math.BigDecimal;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import static model.Session.getCurrentUser;

public class TaxReportController implements Initializable {
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
    private TableColumn<AssetForList, ?> icon;

    @FXML
    private TableColumn<AssetForList, String> name;

    @FXML
    private TableColumn<AssetForList, String> taxDueTotal;

    @FXML
    private VBox taxScreen;
    @FXML
    private TableView<PaymentForList> tablePayment;

    @FXML
    private TableColumn<PaymentForList, String> dateOfPayment;

    @FXML
    private TableColumn<PaymentForList, String> taxDue;

    @FXML
    private ImageView iconPayment;

    @FXML
    private Label namePayment;

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
                taxDueTotal.setCellValueFactory(new PropertyValueFactory<>("taxPaid"));
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

        DecimalFormatSymbols dfs = new DecimalFormatSymbols(new Locale("pt", "Portugal"));
        dfs.setDecimalSeparator(',');
        dfs.setGroupingSeparator('.');
        DecimalFormat df = new DecimalFormat("€ ###,##0.00", dfs);

        for (FinancialAsset fa : getCurrentUser().getFinancialAssetsActive(startDate, finalDate)) {
            AssetForList assetForList = new AssetForList();
            assetForList.setName(fa.getDesignation());
            BigDecimal totalTax = BigDecimal.ZERO;
            for (Payment payment : fa.getPayments()){
                totalTax = totalTax.add(payment.getTaxDue(fa.getTax()));
            }
            assetForList.setTaxPaid(df.format(totalTax));
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

    @FXML
    private void rowSelected(MouseEvent event){
        if (!tableAssets.getSelectionModel().isEmpty()){
            FinancialAsset financialAsset = tableAssets.getSelectionModel().getSelectedItem().getFinancialAsset();
            AssetForList paymentForList = tableAssets.getSelectionModel().getSelectedItem();
            taxScreen.setVisible(true);
            dateOfPayment.setCellValueFactory(new PropertyValueFactory<>("dateOfPayment"));
            taxDue.setCellValueFactory(new PropertyValueFactory<>("taxDue"));
            tablePayment.setItems(getPayments(financialAsset));
            iconPayment.setImage(paymentForList.getIcon().getImage());
            namePayment.setText(paymentForList.getName());
        }
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

        for (Payment payment : financialAsset.getPayments()){
            PaymentForList paymentForList = new PaymentForList();
            paymentForList.setTaxDue(df.format(payment.getTaxDue(financialAsset.getTax())));
            paymentForList.setDateOfPayment(dtf.format(payment.getDateOfPayment()));
            payments.add(paymentForList);
        }
        return payments;
    }

    @FXML
    private void closeTaxScreen(){
        taxScreen.setVisible(false);
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
