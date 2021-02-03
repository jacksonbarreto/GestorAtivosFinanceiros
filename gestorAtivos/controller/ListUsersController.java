package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import model.Log;
import model.LogForView;
import model.User;
import model.UserForView;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static controller.Utils.EuropeanDate;
import static dao.DataBase.users;

public class ListUsersController implements Initializable {
    @FXML
    private TableColumn<UserForView, ImageView> colIcon;

    @FXML
    private TableColumn<UserForView, String> ColUsername;
    @FXML
    private TableView<UserForView> tableUsers;
    @FXML
    private VBox infoUserScreen;
    @FXML
    private TableView<LogForView> tableLog;
    @FXML
    private TableColumn<LogForView, String> colData;
    @FXML
    private TableColumn<LogForView, String> ColOperation;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        startTable();
    }

    private void startTable() {

        colIcon.setCellValueFactory(new PropertyValueFactory<>("icon"));
        ColUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        addButtonToTable();
        tableUsers.setItems(getUsers());
    }

    private ObservableList<UserForView> getUsers() {
        ObservableList<UserForView> assets = FXCollections.observableArrayList();
        assets.addAll(getUsersForList());
        return assets;
    }

    public List<UserForView> getUsersForList() {
        List<UserForView> userForViews = new ArrayList<>();

        for (User user : users) {
            UserForView userForView = new UserForView();
            userForView.setUser(user);
            userForView.setUsername(user.getUsername());
            switch (user.getUserType()) {
                case ROOT:
                    userForView.setIcon(new ImageView(new Image(this.getClass().getResourceAsStream("../img/Root.png"))));
                    break;
                case MANAGER:
                    userForView.setIcon(new ImageView(new Image(this.getClass().getResourceAsStream("../img/Admin.png"))));
                    break;
                default:
                    userForView.setIcon(new ImageView(new Image(this.getClass().getResourceAsStream("../img/User.png"))));
            }
            userForViews.add(userForView);
        }
        return userForViews;
    }

    private void addButtonToTable() {
        TableColumn<UserForView, Void> colBtn = new TableColumn("MAIS INFORMAÇÕES");

        Callback<TableColumn<UserForView, Void>, TableCell<UserForView, Void>> cellFactory = new Callback<TableColumn<UserForView, Void>, TableCell<UserForView, Void>>() {
            @Override
            public TableCell<UserForView, Void> call(final TableColumn<UserForView, Void> param) {
                final TableCell<UserForView, Void> cell = new TableCell<UserForView, Void>() {

                    private final Button btn = new Button("+");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            User userSelected = getTableView().getItems().get(getIndex()).getUser();
                            fillFields(userSelected);
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

        tableUsers.getColumns().add(colBtn);
    }

    private void fillFields(User user){
        infoUserScreen.setVisible(true);
        startTableLog(user);
    }

    private void startTableLog(User user) {

        colData.setCellValueFactory(new PropertyValueFactory<>("date"));
        ColOperation.setCellValueFactory(new PropertyValueFactory<>("operation"));
        tableLog.setItems(getLogs(user));
    }
    private ObservableList<LogForView> getLogs(User user) {
        ObservableList<LogForView> assets = FXCollections.observableArrayList();
        assets.addAll(getLogsForList(user));
        return assets;
    }
    public List<LogForView> getLogsForList(User user) {
        List<LogForView> logForViews = new ArrayList<>();

        for (Log log : user.getLogs()) {
            LogForView logForView = new LogForView();
            logForView.setLog(log);
            logForView.setDate(EuropeanDate.format(log.getMoment()));
            logForView.setOperation(log.getOperation().getOperation());
            logForViews.add(logForView);
        }
        return logForViews;
    }

    @FXML
    void closeWindows() {
        infoUserScreen.setVisible(false);
    }
}
