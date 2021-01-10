package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.User;

import java.net.URL;
import java.util.ResourceBundle;

import static model.Login.userIsValid;
import static model.Session.addUserInSession;


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

    @FXML
    private JFXButton btnLogIn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void handleLogIn(javafx.event.ActionEvent actionEvent) {
        System.out.println(inpUsername.getText());
        System.out.println(inpPassword.getText());
        StringBuilder message = new StringBuilder("Informe o ");
        alert.setVisible(false);
        if (inpUsername.getText().isEmpty() || inpPassword.getText().isEmpty()){
            if (inpUsername.getText().isEmpty() && inpPassword.getText().isEmpty()){
                message.append("username e o password!");
            } else if (inpUsername.getText().isEmpty()){
                message.append("username!");
            } else {
                message.append("password!");
            }
            alert.setText(message.toString());
            alert.setVisible(true);
        }else {
            User user = userIsValid(inpUsername.getText(),inpPassword.getText());
            if (user == null){
                alert.setText("username ou password inválidos.");
                alert.setVisible(true);
            } else {
                addUserInSession(user);
                alert.setText("conectado");
                alert.setVisible(true);
            }
        }

    }

    public void handleMouseEvent(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() == btnClose){
            System.exit(0);
        }
        if (mouseEvent.getSource() == btnMinus){
            Stage stage = (Stage) btnMinus.getScene().getWindow();
            stage.setIconified(true);
        }
    }
}
