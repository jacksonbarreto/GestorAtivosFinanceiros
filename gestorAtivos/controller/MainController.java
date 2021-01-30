package controller;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import view.MainView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static controller.PanelLocation.CENTER;
import static controller.PanelLocation.SIDEBAR;
import static model.Session.getCurrentUser;
import static model.Shutdown.shutdown;


public class MainController implements Initializable {

    @FXML
    private BorderPane mainScreen;

    @FXML
    private JFXButton btnHome;
    @FXML
    private JFXButton btnHome1;
    @FXML
    private JFXButton btnNewAsset;
    @FXML
    private JFXButton btnConsultLog;
    @FXML
    private JFXButton btnLstAllAssets;
    @FXML
    private JFXButton btnCriateBank;
    @FXML
    private JFXButton btnCreateUser;
    @FXML
    private ImageView btnConfig;
    @FXML
    private ImageView iconUser;
    @FXML
    private ImageView iconAdmin;
    @FXML
    private VBox userMenu;
    @FXML
    private VBox rootMenu;
    @FXML
    private ImageView iconRoot;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configScreenForUser();
    }

    public void loadScreen(String screen, PanelLocation panelLocation) {
        Parent content = null;
        try {
            content = FXMLLoader.load(getClass().getResource("/view/" + screen + ".fxml"));

        } catch (IOException e) {
            e.printStackTrace();
        }
        switch (panelLocation){
            case CENTER:
                mainScreen.setCenter(content);
                break;
            case SIDEBAR:

        }

    }

    private void configScreenForUser() {
        switch (getCurrentUser().getUserType()) {
            case ROOT:
                iconRoot.setVisible(true);
                rootMenu.setVisible(true);
                loadScreen("homeRoot", CENTER);
                break;
            case MANAGER:
                iconAdmin.setVisible(true);
                break;
            default:
                iconUser.setVisible(true);
                userMenu.setVisible(true);
                loadScreen("homeUser", CENTER);
        }
    }

    public void handleMouseEvent(MouseEvent mouseEvent) {

        if (mouseEvent.getSource() == btnHome) {
            loadScreen("homeUser", CENTER);
        } else if (mouseEvent.getSource() == btnNewAsset) {
            loadScreen("newAsset", CENTER);
        } else if (mouseEvent.getSource() == btnConsultLog) {
            loadScreen("consultLog", CENTER);
        } else if (mouseEvent.getSource() == btnLstAllAssets) {
            loadScreen("listAssets", CENTER);
        } else if (mouseEvent.getSource() == btnConfig) {
            loadScreen("configAccount", CENTER);
        }else if (mouseEvent.getSource() == btnHome1) {
            loadScreen("homeRoot", CENTER);
        }else if (mouseEvent.getSource() == btnCriateBank) {
            loadScreen("createBank", CENTER);
        }else if (mouseEvent.getSource() == btnCreateUser) {
            loadScreen("createUser", CENTER);
        }

    }


    public void closeWindows() {
        shutdown();
        MainView.getStage().close();
    }
}
