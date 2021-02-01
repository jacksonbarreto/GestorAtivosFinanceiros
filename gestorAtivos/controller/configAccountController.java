package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

import static model.Session.getCurrentUser;

public class configAccountController implements Initializable {
    @FXML
    private TextField username;

    @FXML
    private PasswordField pass;

    @FXML
    private PasswordField confirPass;

    @FXML
    private JFXButton btnSave;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            username.setText(getCurrentUser().getUsername());
    }


    @FXML
    void saveNewUser(ActionEvent event) {
        getCurrentUser().changeUsername(username.getText());
        getCurrentUser().changePassword(pass.getText());
    }
}
