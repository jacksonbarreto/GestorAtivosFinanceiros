package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import model.AssetWithInvestedValue;
import model.FinancialAsset;
import model.Log;

import java.net.URL;
import java.util.ResourceBundle;

import static model.Session.getCurrentUser;

public class ListAssetsController implements Initializable {

    @FXML
    private VBox vbox;

    @FXML
    private TableView<FinancialAsset> tableAssets;

    @FXML
    private TableColumn<?, ?> colIcon;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colAmaunt;

    @FXML
    private TableColumn<?, ?> colPlusInfo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        colName.setCellValueFactory(new PropertyValueFactory<>("designation"));
        colAmaunt.setCellValueFactory(new PropertyValueFactory<>("getAmountInvested"));
        tableAssets.setItems(getAssets());

    }

    private ObservableList<FinancialAsset> getAssets(){
        ObservableList<FinancialAsset> assets = FXCollections.observableArrayList();
        assets.addAll(getCurrentUser().getFinancialAssets());
        return assets;
    }
}
