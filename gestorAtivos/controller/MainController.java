package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import model.AssetType;
import model.FinancialAsset;
import model.LogicalOperator;
import view.MainView;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import static controller.Utils.applyStyle;
import static model.LogicalOperator.BIGGER_THEN;
import static model.Session.getCurrentUser;
import static model.Shutdown.shutdown;
import static model.User.filterByAmountInvested;


public class MainController implements Initializable {
    private Boolean statusVisibilitySubmenuReport = false;
    @FXML
    private BorderPane mainScreen;
    @FXML
    private JFXButton btnHome;
    @FXML
    private JFXButton btnNewAsset;
    @FXML
    private JFXButton btnConsultLog;
    @FXML
    private JFXButton btnLstAllAssets;
    @FXML
    private JFXButton btnCreateBank;
    @FXML
    private JFXButton btnCreateUser;
    @FXML
    private JFXButton btnConfig;
    @FXML
    private ImageView avatar;
    @FXML
    private VBox userMenu;
    @FXML
    private VBox rootMenu;
    @FXML
    private JFXButton btnReport;
    @FXML
    private JFXButton btnListUsers;
    @FXML
    private JFXButton btnFinancial;
    @FXML
    private JFXButton btnListBank;
    @FXML
    private JFXButton btnTax;
    @FXML
    private TextField search;
    @FXML
    private JFXComboBox<AssetType> assetType;
    @FXML
    private JFXComboBox<LogicalOperator> logicalOperator;
    @FXML
    private TextField amountFilter;
    private String routeOfHome;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configScreenForUser();
        assetType.getItems().add(null);
        assetType.getItems().addAll(FXCollections.observableArrayList(AssetType.values()));
        assetType.setValue(null);
        logicalOperator.getItems().addAll(FXCollections.observableArrayList(LogicalOperator.values()));
        logicalOperator.setValue(BIGGER_THEN);
    }

    @FXML
    void handleFindAsset(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            findAsset();
        }
    }

    @FXML
    void findAsset() {
        FXMLLoader loader = getLoader("findAssets");
        Parent content = loadLoader(loader);
        FindAssetsController controller = loader.getController();
        List<FinancialAsset> source = new ArrayList<>();
        if (!search.getText().isEmpty() || assetType.getValue() != null || !search.getText().isEmpty() || !amountFilter.getText().isEmpty()) {
            if (!search.getText().isEmpty() && assetType.getValue() != null) {
                if (amountFilter.getText().isEmpty()) {
                    source.addAll(getCurrentUser().findFinancialAsset(search.getText(), assetType.getValue()));
                } else {
                    source.addAll(filterByAmountInvested(getCurrentUser().findFinancialAsset(search.getText(), assetType.getValue()), logicalOperator.getValue(), new BigDecimal(amountFilter.getText())));
                }
            } else if (!search.getText().isEmpty()) {
                if (amountFilter.getText().isEmpty()) {
                    source.addAll(getCurrentUser().findFinancialAsset(search.getText()));
                } else {
                    source.addAll(filterByAmountInvested(getCurrentUser().findFinancialAsset(search.getText()), logicalOperator.getValue(), new BigDecimal(amountFilter.getText())));
                }
            } else if (assetType.getValue() != null) {
                if (amountFilter.getText().isEmpty()) {
                    source.addAll(getCurrentUser().findFinancialAsset(assetType.getValue()));
                } else {
                    source.addAll(filterByAmountInvested(getCurrentUser().findFinancialAsset(assetType.getValue()), logicalOperator.getValue(), new BigDecimal(amountFilter.getText())));
                }
            } else if (!amountFilter.getText().isEmpty()) {
                source.addAll(filterByAmountInvested(getCurrentUser().getFinancialAssets(), logicalOperator.getValue(), new BigDecimal(amountFilter.getText())));
            }
            controller.defineSource(source);
            mainScreen.setCenter(content);
        }
    }

    private FXMLLoader getLoader(String screen) {
        return new FXMLLoader(getClass().getResource("/view/" + screen + ".fxml"));
    }

    private Parent loadLoader(FXMLLoader loader) {
        Parent content = null;
        try {
            content = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    private void loadScreen(String screen) {
        mainScreen.setCenter(loadLoader(getLoader(screen)));
    }

    private void configScreenForUser() {
        switch (getCurrentUser().getUserType()) {
            case ROOT:
                avatar.setImage(new Image(this.getClass().getResourceAsStream("../img/Root.png")));
                rootMenu.setVisible(true);
                applyStyle(btnHome, "clicked");
                routeOfHome = "homeRoot";
                break;
            case MANAGER:
                avatar.setImage(new Image(this.getClass().getResourceAsStream("../img/Admin.png")));
                break;
            default:
                avatar.setImage(new Image(this.getClass().getResourceAsStream("../img/User.png")));
                userMenu.setVisible(true);
                applyStyle(btnHome, "clicked");
                routeOfHome = "homeUser";
        }
        loadScreen(routeOfHome);
    }

    public void router(MouseEvent mouseEvent) {
        JFXButton clickedButton = (JFXButton) mouseEvent.getSource();
        String route = null;

        restartButtons();
        if (clickedButton == btnHome) {
            route = routeOfHome;
        } else if (clickedButton == btnNewAsset) {
            route = "newAsset";
        } else if (clickedButton == btnConsultLog) {
            route = "consultLog";
        } else if (clickedButton == btnLstAllAssets) {
            route = "listAssets";
        } else if (clickedButton == btnConfig) {
            route = "configAccount";
        } else if (clickedButton == btnCreateBank) {
            route = "createBank";
        } else if (clickedButton == btnCreateUser) {
            route = "createUser";
        } else if (clickedButton == btnReport) {
            restartButtons();
            if (statusVisibilitySubmenuReport) {
                hideReportSubmenu();
            } else {
                hideReportSubmenu();
                displaysReportSubmenu();
            }
        } else if (clickedButton == btnFinancial) {
            applyStyle(btnReport, "clicked");
            route = "financialReport";
        } else if (clickedButton == btnTax) {
            applyStyle(btnReport, "clicked");
            route = "taxReport";
        } else if (clickedButton == btnListBank) {
            route = "listBanks";
        } else if (clickedButton == btnListUsers) {
            route = "listUsers";
        }
        if (clickedButton != btnReport && clickedButton != btnFinancial && clickedButton != btnTax) {
            hideReportSubmenu();
        }

        applyStyle(clickedButton, "clicked");
        if (route != null) {
            loadScreen(route);
        }
    }

    private void restartButtons() {
        List<JFXButton> buttons = Arrays.asList(btnReport, btnListBank, btnHome, btnTax, btnFinancial, btnCreateUser, btnCreateBank, btnLstAllAssets, btnConsultLog, btnNewAsset, btnListUsers);
        for (JFXButton button : buttons) {
            button.getStyleClass().remove("clicked");
        }
    }

    private void hideReportSubmenu() {
        btnFinancial.setVisible(false);
        btnTax.setVisible(false);
        statusVisibilitySubmenuReport = false;
    }

    private void displaysReportSubmenu() {
        btnFinancial.setVisible(true);
        btnTax.setVisible(true);
        statusVisibilitySubmenuReport = true;
    }

    public void closeWindows() {
        shutdown();
        MainView.getStage().close();
    }
}
