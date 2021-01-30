package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import model.Bank;

import java.net.URL;
import java.util.ResourceBundle;

import static dao.BankDAO.saveBank;

public class CreateBankController implements Initializable {


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private TextField bankName;

    @FXML
    private JFXButton btnSave;

    @FXML
    void saveNewBank(ActionEvent event) {
        Bank bank = new Bank(bankName.getText());
        saveBank(bank);
    }
}
