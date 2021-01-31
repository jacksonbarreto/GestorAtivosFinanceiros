package view;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

import static model.Bootloader.boot;
import static view.Utilities.configureWindow;

public class LoginView extends Application implements StandardWindow {

    private static Stage stage;
    private double xOffset;
    private double yOffset;

    public static void main(String[] args) {
        boot();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = configureWindow("Login.fxml", primaryStage, LoginView.class);

        root.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - xOffset);
            primaryStage.setY(event.getScreenY() - yOffset);
        });
        stage.getIcons().add(new Image(this.getClass().getResourceAsStream("../img/icon.jpg")));
    }

    public static void setStage(Stage stage) {
        LoginView.stage = stage;
    }

    public static Stage getStage() {
        return stage;
    }
}