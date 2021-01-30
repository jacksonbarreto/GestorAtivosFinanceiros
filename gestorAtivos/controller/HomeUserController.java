package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import model.User;

import java.net.URL;
import java.util.ResourceBundle;

import static model.Session.getCurrentUser;

public class HomeUserController implements Initializable {

    @FXML
    private Label username;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       username.setText(getCurrentUser().getUsername());
    }

}
