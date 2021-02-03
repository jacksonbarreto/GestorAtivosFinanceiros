package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Log;
import model.LogForView;
import model.Operation;
import model.User;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static controller.Utils.EuropeanDate;
import static model.Session.getCurrentUser;

public class ConsultLogController implements Initializable {

    @FXML
    private TableView<LogForView> tableLogs;
    @FXML
    private TableColumn<LogForView, String> ColDate;

    @FXML
    private TableColumn<LogForView, String> ColOperation;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        startTableLog();
    }


    private void startTableLog() {

        ColDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        ColOperation.setCellValueFactory(new PropertyValueFactory<>("operation"));
        tableLogs.setItems(getLogs());
    }
    private ObservableList<LogForView> getLogs() {
        ObservableList<LogForView> assets = FXCollections.observableArrayList();
        assets.addAll(getLogsForList());
        return assets;
    }
    public List<LogForView> getLogsForList() {
        List<LogForView> logForViews = new ArrayList<>();

        for (Log log : getCurrentUser().getLogs()) {
            LogForView logForView = new LogForView();
            logForView.setLog(log);
            logForView.setDate(EuropeanDate.format(log.getMoment()));
            logForView.setOperation(log.getOperation().getOperation());
            logForViews.add(logForView);
        }
        return logForViews;
    }
}
