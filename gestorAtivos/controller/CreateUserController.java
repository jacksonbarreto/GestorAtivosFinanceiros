package controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.User;
import model.UserType;

import java.net.URL;
import java.util.ResourceBundle;

import static dao.UserDAO.saveUser;
import static model.UserType.SIMPLE;
import static model.UserType.UserTypeStringConverter;

public class CreateUserController implements Initializable {

    @FXML
    private TextField username;

    @FXML
    private PasswordField pass;

    @FXML
    private PasswordField confirmPass;

    @FXML
    private ChoiceBox<UserType> chUserType;

    @FXML
    private JFXButton btnSave;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        chUserType.getItems().addAll(FXCollections.observableArrayList(UserType.values()));
        chUserType.setValue(SIMPLE);
        chUserType.setConverter(new UserTypeStringConverter());
    }

    @FXML
    void saveNewUser(ActionEvent event) {
        User user = new User(username.getText(),pass.getText(),chUserType.getValue());
        saveUser(user);
    }

}
