package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;


public class LoginController implements Initializable {

    @FXML
    private JFXTextField inpUsername;
    @FXML
    private JFXPasswordField inpPassword;

    @FXML
    private Circle btnClose;

    @FXML
    private JFXButton btnLogIn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void handleLogIn(javafx.event.ActionEvent actionEvent) {
        System.out.println(inpUsername.getText());
        System.out.println(inpPassword.getText());
    }

    public void handleMouseEvent(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() == btnClose){
            System.exit(0);
        }
    }
}
