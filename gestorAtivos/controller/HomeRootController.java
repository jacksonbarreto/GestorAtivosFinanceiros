package controller;

import javafx.fxml.Initializable;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

import static dao.DataBase.getTotalBanks;
import static dao.DataBase.getTotalUsers;
import static model.Session.getCurrentUser;

public class HomeRootController implements Initializable {
    @FXML
    private Label username;
    @FXML
    private Label banks;
    @FXML
    private Label users;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        username.setText(getCurrentUser().getUsername());
        banks.setText(String.valueOf(getTotalBanks()));
        users.setText(String.valueOf(getTotalUsers()));
    }
}
