package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import model.Bank;
import model.InvestmentFund;
import model.RentalProperty;
import model.TermDeposit;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import static dao.DataBase.banks;
import static model.Session.getCurrentUser;

public class NewAssetController implements Initializable {

    @FXML
    private JFXRadioButton RDTermDeposit;

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
    private Label errorFieldMsg;

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
        startDate.setValue(LocalDate.now());
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
        if (inputsAreValid()) {
            try {
                int durationAsset = Integer.parseInt(duration.getText());
                BigDecimal taxAsset = new BigDecimal(tax.getText());
                String designationAsset = designation.getText();

                if (RDTermDeposit.isSelected()) {
                    termDeposit = new TermDeposit(durationAsset, taxAsset, designationAsset, new BigDecimal(depositedAmount.getText()), new BigDecimal(annualProfitability.getText()), account.getText(), bank.getValue());
                    termDeposit.changeStartDate(startDate.getValue());
                    getCurrentUser().addAssetFinancial(termDeposit);
                } else if (RDRentalProperty.isSelected()) {
                    rentalProperty = new RentalProperty(durationAsset, taxAsset, designationAsset, new BigDecimal(propertyValue.getText()), new BigDecimal(rentAmount.getText()), new BigDecimal(monthlyCostCondominium.getText()), new BigDecimal(annualAmountOtherExpenses.getText()), location.getText());
                    rentalProperty.changeStartDate(startDate.getValue());
                    getCurrentUser().addAssetFinancial(rentalProperty);
                } else if (RDInvestmentFund.isSelected()) {
                    investmentFund = new InvestmentFund(durationAsset, taxAsset, designationAsset, new BigDecimal(amountInvested.getText()), new BigDecimal(monthlyProfitability.getText()));
                    investmentFund.changeStartDate(startDate.getValue());
                    getCurrentUser().addAssetFinancial(investmentFund);
                }
                mainAssetScreen.setVisible(false);
                successScreen.setVisible(true);
            } catch (Exception e) {
                mainAssetScreen.setVisible(false);
                errorMsg.setText(e.getMessage());
                errorScreen.setVisible(true);
            }
        } else {
            errorFieldMsg.setVisible(true);
        }
    }


    private boolean inputsAreValid() {
        try {


            if (designation.getText().isEmpty() || designation.getText().length() < 3) {
                errorFieldMsg.setText("O nome do ativo financeiro não pode estar vazio ou ter menos do que 3 carácteres.");
                return false;
            }
            if (duration.getText().isEmpty()) {
                errorFieldMsg.setText("A duração deve ser preenchida.");
                return false;
            } else if (Double.parseDouble(duration.getText()) <= 0) {
                errorFieldMsg.setText("A duração deve ser maior que 0.");
                return false;
            }
            if (tax.getText().isEmpty()) {
                errorFieldMsg.setText("O imposto deve ser preenchido.");
                return false;
            } else if (Double.parseDouble(tax.getText()) < 0) {
                errorFieldMsg.setText("O valor da taxa não pode ser negativo.");
                return false;
            }

            if (RDTermDeposit.isSelected()) {

                if (depositedAmount.getText().isEmpty()) {
                    errorFieldMsg.setText("O valor do depósito deve ser preenchido.");
                    return false;
                } else if (Double.parseDouble(depositedAmount.getText()) < 0) {
                    errorFieldMsg.setText("O valor do depósito não pode ser negativo.");
                    return false;
                }
                if (account.getText().isEmpty() || account.getText().length() < 3) {
                    errorFieldMsg.setText("A conta deve ser preenchida.");
                    return false;
                }
                if (bank.getValue() == null) {
                    errorFieldMsg.setText("O banco deve ser preenchido.");
                    return false;
                }
                if (annualProfitability.getText().isEmpty()) {
                    errorFieldMsg.setText("A rentabilidade anual deve ser preenchida.");
                    return false;
                }

            } else if (RDRentalProperty.isSelected()) {
                if (propertyValue.getText().isEmpty()) {
                    errorFieldMsg.setText("O valor da propriedade deve ser preenchido.");
                    return false;
                } else if (Double.parseDouble(propertyValue.getText()) < 0) {
                    errorFieldMsg.setText("O valor da propriedade não pode ser negativo.");
                    return false;
                }
                if (rentAmount.getText().isEmpty()) {
                    errorFieldMsg.setText("O valor da renda deve ser preenchido.");
                    return false;
                } else if (Double.parseDouble(rentAmount.getText()) < 0) {
                    errorFieldMsg.setText("O valor da renda não pode ser negativo.");
                    return false;
                }
                if (monthlyCostCondominium.getText().isEmpty()) {
                    errorFieldMsg.setText("O valor do condomínio deve ser preenchido.");
                    return false;
                } else if (Double.parseDouble(monthlyCostCondominium.getText()) < 0) {
                    errorFieldMsg.setText("O valor do condomínio não pode ser negativo.");
                    return false;
                }
                if (annualAmountOtherExpenses.getText().isEmpty()) {
                    errorFieldMsg.setText("O valor das outras despesas deve ser preenchido.");
                    return false;
                } else if (Double.parseDouble(annualAmountOtherExpenses.getText()) < 0) {
                    errorFieldMsg.setText("O valor das outras despesas não pode ser negativo.");
                    return false;
                }
                if (location.getText().isEmpty()) {
                    errorFieldMsg.setText("O endereço deve ser preenchido.");
                    return false;
                }
            } else if (RDInvestmentFund.isSelected()) {
                if (amountInvested.getText().isEmpty()) {
                    errorFieldMsg.setText("O valor investido deve ser preenchido.");
                    return false;
                } else if (Double.parseDouble(amountInvested.getText()) < 0) {
                    errorFieldMsg.setText("O valor investido não pode ser negativo.");
                    return false;
                }
                if (monthlyProfitability.getText().isEmpty()) {
                    errorFieldMsg.setText("A rentabilidade mensal deve ser preenchida.");
                    return false;
                }
            }
            return true;
        }catch (Exception e){
            mainAssetScreen.setVisible(false);
            errorMsg.setText(e.getMessage());
            errorScreen.setVisible(true);
        }
        return false;
    }
}
