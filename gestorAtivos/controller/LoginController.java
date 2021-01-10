package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;


public class LoginController implements Initializable {

    @FXML
    private JFXTextField inpUsername;
    @FXML
    private JFXPasswordField inpPassword;

    @FXML
    private FontAwesomeIconView btnClose;

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
