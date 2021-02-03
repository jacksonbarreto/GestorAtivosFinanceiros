package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import model.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static controller.Utils.euroCurrency;
import static dao.DataBase.banks;

public class ListBanksController implements Initializable {

    @FXML
    private DatePicker startDate;
    @FXML
    private DatePicker finalDate;
    @FXML
    private Label erroStartDate;
    @FXML
    private Label erroFinalDate;
    @FXML
    private VBox resultScreen;
    @FXML
    private TableView<BankForView> tableBank;
    @FXML
    private TableColumn<BankForView, ImageView> img;
    @FXML
    private TableColumn<BankForView, String> bankName;
    @FXML
    private TableColumn<BankForView, String> totalCautioned;
    @FXML
    private TableColumn<BankForView, String> interestPaid;
    @FXML
    private VBox editBank;
    @FXML
    private TextField bankNameEditable;
    @FXML
    private Label errorNameBank;
    private Bank bankForEdition;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadTables();
    }

    @FXML
    void closeEditBank() {
        editBank.setVisible(false);
    }

    @FXML
    void findBank() {
        hideErroDate();
        errorNameBank.setVisible(false);
        hideScreens();
        if (startDate.getValue() == null || finalDate.getValue() == null) {
            if (startDate.getValue() == null) {
                erroStartDate.setVisible(true);
            }
            if (finalDate.getValue() == null) {
                erroFinalDate.setVisible(true);
            }
        } else {
            resultScreen.setVisible(true);
            tableBank.setItems(getBanks());
        }

    }

    @FXML
    void saveNewBank() {

        if (!bankNameEditable.getText().isEmpty() && bankNameEditable.getText().length() >= 3) {
            bankForEdition.setName(bankNameEditable.getText());
            editBank.setVisible(false);
            tableBank.setItems(getBanks());
        }else {
            errorNameBank.setVisible(true);
        }
    }

    @FXML
    void handleKeyEditeName(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            saveNewBank();
        }
    }

    private void loadTables() {
        initializeTableBank();
    }

    private ObservableList<BankForView> getBanks() {
        ObservableList<BankForView> banks = FXCollections.observableArrayList();
        banks.addAll(getBanksForList());
        return banks;
    }

    public List<BankForView> getBanksForList() {
        List<BankForView> bankForViews = new ArrayList<>();

        for (Bank bank : banks) {

            BankForView bankForView = new BankForView();
            bankForView.setBankName(bank.getName());
            bankForView.setInterestPaid(euroCurrency.format(bank.getTotalInterestPaid(startDate.getValue(), finalDate.getValue())));
            //bankForView.setTotalCautioned(euroCurrency.format(bank.getEquityInDeposits(startDate.getValue(), finalDate.getValue())));
            bankForView.setTotalCautioned(euroCurrency.format(bank.getEquityInDeposits()));
            bankForView.setImg(new ImageView(new Image(this.getClass().getResourceAsStream("../img/Bank.png"))));
            bankForView.setBank(bank);
            bankForViews.add(bankForView);
        }
        return bankForViews;
    }

    private void initializeTableBank() {
        img.setCellValueFactory(new PropertyValueFactory<>("img"));
        bankName.setCellValueFactory(new PropertyValueFactory<>("bankName"));
        totalCautioned.setCellValueFactory(new PropertyValueFactory<>("totalCautioned"));
        interestPaid.setCellValueFactory(new PropertyValueFactory<>("interestPaid"));
        addButtonToTable();
//        tableBank.setItems(getBanks());
    }


    private void addButtonToTable() {
        TableColumn<BankForView, Void> colBtn = new TableColumn("EDITAR BANCO");

        Callback<TableColumn<BankForView, Void>, TableCell<BankForView, Void>> cellFactory = new Callback<TableColumn<BankForView, Void>, TableCell<BankForView, Void>>() {
            @Override
            public TableCell<BankForView, Void> call(final TableColumn<BankForView, Void> param) {
                final TableCell<BankForView, Void> cell = new TableCell<BankForView, Void>() {

                    private final Button btn = new Button("+");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Bank bank = getTableView().getItems().get(getIndex()).getBank();
                            fillFields(bank);
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

        tableBank.getColumns().add(colBtn);
    }

    private void fillFields(Bank bank) {
        editBank.setVisible(true);
        bankNameEditable.setText(bank.getName());
        bankForEdition = bank;
    }

    private void hideScreens() {
        editBank.setVisible(false);
    }

    private void hideErroDate() {
        erroStartDate.setVisible(false);
        erroFinalDate.setVisible(false);
    }
}
