package controller;

import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import view.MainView;

import java.net.URL;
import java.util.ResourceBundle;

import static model.Shutdown.shutdown;


public class MainController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void handleMouseEvent(MouseEvent mouseEvent) {

    }

    public void closeWindows() {
        shutdown();
        MainView.getStage().close();
    }
}
