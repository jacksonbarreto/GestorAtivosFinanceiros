package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

import static model.Session.getCurrentUser;

public class configAccountController implements Initializable {
    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private PasswordField passwordConfirmation;
    @FXML
    private Label errorMsg;
    @FXML
    private VBox successScreen;
    @FXML
    private VBox fieldScreen;
    @FXML
    private Label errorInCreate;

    @FXML
    private VBox errorScreen;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        username.setText(getCurrentUser().getUsername());
    }


    @FXML
    void saveNewUser() {
        try {
            if (inputValid()) {
                getCurrentUser().changeUsername(username.getText());
                getCurrentUser().changePassword(password.getText());
                hideScreens();
                successScreen.setVisible(true);
            } else {
                errorMsg.setVisible(true);
            }

        } catch (Exception e) {
            hideScreens();
            errorInCreate.setText(e.getMessage());
            errorScreen.setVisible(true);
        }

    }

    private boolean passwordsAreTheSame() {
        return (password.getText().compareTo(passwordConfirmation.getText()) == 0);
    }

    private boolean inputValid() {
        if (password.getText().isEmpty()) {
            errorMsg.setText("A senha deve ser preenchida.");
            return false;
        } else if (password.getText().length() <= 3) {
            errorMsg.setText("A senha deve ter mais que 3 carácteres.");
            return false;
        }
        if (passwordConfirmation.getText().isEmpty()) {
            errorMsg.setText("A senha de confirmação deve ser preenchida.");
            return false;
        } else if (passwordConfirmation.getText().length() <= 3) {
            errorMsg.setText("A senha de confirmação deve ter mais que 3 carácteres.");
            return false;
        } else if (!passwordsAreTheSame()) {
            errorMsg.setText("A senha e senha de confirmação são diferentes.");
            return false;
        }
        if (username.getText().isEmpty()) {
            errorMsg.setText("O nome de usuário deve ser preenchido.");
            return false;
        } else if (username.getText().length() <= 3) {
            errorMsg.setText("O nome de usuário deve ter mais que 3 carácteres.");
            return false;
        }
        return true;
    }

    private void hideScreens() {
        fieldScreen.setVisible(false);
        successScreen.setVisible(false);
        errorScreen.setVisible(false);
    }
}
