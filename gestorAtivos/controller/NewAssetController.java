package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import model.*;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;

import static dao.DataBase.banks;
import static model.Session.getCurrentUser;

public class NewAssetController implements Initializable {

    @FXML
    private JFXRadioButton RDTermDeposit;

    @FXML
    private ToggleGroup AssetType;

    @FXML
    private JFXRadioButton RDRentalProperty;

    @FXML
    private JFXRadioButton RDInvestmentFund;

    @FXML
    private VBox paneImovel;
    @FXML
    private VBox paneTermeDeposit;

    @FXML
    private VBox paneFound;

    @FXML
    private TextField designation;

    @FXML
    private DatePicker startDate;

    @FXML
    private TextField duration;

    @FXML
    private TextField tax;

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
    private TextField amountInvested;

    @FXML
    private TextField monthlyProfitability;


    @FXML
    private TextField depositedAmount;

    @FXML
    private TextField annualProfitability;

    @FXML
    private TextField account;

    @FXML
    private JFXComboBox<Bank> bank;
    @FXML
    private VBox successScreen;

    @FXML
    private VBox errorScreen;

    @FXML
    private VBox mainAssetScreen;

    @FXML
    private Label errorMsg;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            bank.getItems().addAll(FXCollections.observableArrayList(banks));
    }

    @FXML
    void assetSelected(ActionEvent event) {
        hideAllPane();
        if (event.getSource() == RDTermDeposit) {
            paneTermeDeposit.setVisible(true);
        } else if (event.getSource() == RDRentalProperty) {
            paneImovel.setVisible(true);
        } else if (event.getSource() == RDInvestmentFund) {
            paneFound.setVisible(true);
        }
    }


    private void hideAllPane() {
        paneImovel.setVisible(false);
        paneTermeDeposit.setVisible(false);
        paneFound.setVisible(false);
    }

    public void saveAsset() {
        TermDeposit termDeposit;
        RentalProperty rentalProperty;
        InvestmentFund investmentFund;

        try {
            int durationAsset = Integer.parseInt(duration.getText());
            BigDecimal taxAsset = new BigDecimal(tax.getText());
            String designationAsset = new String(designation.getText());

            if (RDTermDeposit.isSelected()) {
                termDeposit = new TermDeposit(durationAsset, taxAsset, designationAsset, new BigDecimal(depositedAmount.getText()), new BigDecimal(annualProfitability.getText()), account.getText(), bank.getValue());
                getCurrentUser().addAssetFinancial(termDeposit);
            } else if (RDRentalProperty.isSelected()) {
                rentalProperty = new RentalProperty(durationAsset, taxAsset, designationAsset, new BigDecimal(propertyValue.getText()), new BigDecimal(rentAmount.getText()), new BigDecimal(monthlyCostCondominium.getText()), new BigDecimal(annualAmountOtherExpenses.getText()), location.getText());
                getCurrentUser().addAssetFinancial(rentalProperty);
            } else if (RDInvestmentFund.isSelected()) {
                investmentFund = new InvestmentFund(durationAsset, taxAsset, designationAsset, new BigDecimal(amountInvested.getText()), new BigDecimal(monthlyProfitability.getText()));
                getCurrentUser().addAssetFinancial(investmentFund);
            }
            mainAssetScreen.setVisible(false);
            successScreen.setVisible(true);
        } catch (Exception e) {
            mainAssetScreen.setVisible(false);
            errorMsg.setText(e.getMessage());
            errorScreen.setVisible(true);
        }
    }
}
