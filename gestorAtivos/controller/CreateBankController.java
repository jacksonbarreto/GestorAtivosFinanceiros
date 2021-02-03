package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import model.Bank;

import java.net.URL;
import java.util.ResourceBundle;

import static dao.BankDAO.saveBank;

public class CreateBankController implements Initializable {

    @FXML
    private VBox createScreen;

    @FXML
    private TextField bankName;

    @FXML
    private Label errorNameBank;

    @FXML
    private VBox success;

    @FXML
    private VBox errorScreen;

    @FXML
    private Label msgError;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hideScreens();
        createScreen.setVisible(true);
    }

    @FXML
    void saveNewBank() {
        try {
            if (!bankName.getText().isEmpty() && bankName.getText().length() >= 3) {
                Bank bank = new Bank(bankName.getText());
                saveBank(bank);
                hideScreens();
                success.setVisible(true);
            } else {
                errorNameBank.setVisible(true);
            }
        } catch (Exception e) {
            hideScreens();
            errorScreen.setVisible(true);
            msgError.setText(e.getMessage());
        }
    }

    @FXML
    void keyHandle(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            saveNewBank();
        }
    }

    private void hideScreens() {
        createScreen.setVisible(false);
        errorScreen.setVisible(false);
        success.setVisible(false);
    }
}
