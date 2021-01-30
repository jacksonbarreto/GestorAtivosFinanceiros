package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Log;
import model.Operation;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import static model.Session.getCurrentUser;

public class ConsultLogController implements Initializable {

    @FXML
    private TableView<Log> tableLogs;
    @FXML
    private TableColumn<Log, LocalDateTime> ColDate;

    @FXML
    private TableColumn<Log, Operation> ColOperation;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ColDate.setCellValueFactory(new PropertyValueFactory<Log, LocalDateTime>("moment"));
        ColOperation.setCellValueFactory(new PropertyValueFactory<Log, Operation>("operation"));
        tableLogs.setItems(getLogs());
    }

    public ObservableList<Log> getLogs(){
        ObservableList<Log> logs = FXCollections.observableArrayList();
        logs.addAll(getCurrentUser().getLogs());
        return logs;
    }
}
