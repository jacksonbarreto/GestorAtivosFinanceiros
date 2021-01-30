package controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.User;
import view.LoginView;
import view.MainView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static model.Login.userIsValid;
import static model.Session.addUserInSession;
import static model.Shutdown.shutdown;


public class LoginController implements Initializable {

    @FXML
    private JFXTextField inpUsername;
    @FXML
    private JFXPasswordField inpPassword;

    @FXML
    private FontAwesomeIconView btnClose;
    @FXML
    private Label alert;

    @FXML
    private FontAwesomeIconView btnMinus;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        inpPassword.setOnKeyPressed((KeyEvent e) -> {
            if (e.getCode() == KeyCode.ENTER) {
                handleLogIn();
            }
        });
    }

    public void handleLogIn() {
        StringBuilder message = new StringBuilder("Informe o ");
        alert.setVisible(false);
        if (inpUsername.getText().isEmpty() || inpPassword.getText().isEmpty()) {
            if (inpUsername.getText().isEmpty() && inpPassword.getText().isEmpty()) {
                message.append("username e o password!");
            } else if (inpUsername.getText().isEmpty()) {
                message.append("username!");
            } else {
                message.append("password!");
            }
            alert.setText(message.toString());
            alert.setVisible(true);
        } else {
            User user = userIsValid(inpUsername.getText(), inpPassword.getText());
            if (user == null) {
                alert.setText("username ou password inválidos.");
                alert.setVisible(true);
            } else {
                addUserInSession(user);
                MainView mainView = new MainView();
                LoginView.getStage().close();
                try {
                    mainView.start(new Stage());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void handleMouseEvent(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() == btnClose) {
            closeWindows();
        }
        if (mouseEvent.getSource() == btnMinus) {
            Stage stage = (Stage) btnMinus.getScene().getWindow();
            stage.setIconified(true);
        }
    }

    public void closeWindows() {
        shutdown();
        LoginView.getStage().close();
    }
}
