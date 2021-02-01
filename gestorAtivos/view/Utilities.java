package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class Utilities {

    public static Parent configureWindow(String resource, Stage primaryStage, Class<?> classe) throws IOException {
        if (resource == null || resource.isEmpty() || primaryStage == null || classe == null || !implementsStandardWindow(classe))
            throw new IllegalArgumentException();
        Parent root = FXMLLoader.load(classe.getResource(resource));
        Scene scene = new Scene(root);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.getIcons().add(new Image(classe.getResourceAsStream("../img/icon.png")));
        try {
            classe.getMethod("setStage", Stage.class).invoke(classe, primaryStage);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return root;
    }

    private static boolean implementsStandardWindow(Class<?> classe) {
        Class<?>[] interfaces = classe.getInterfaces();

        for (Class<?> anInterface : interfaces) {
            if (anInterface.isAssignableFrom(StandardWindow.class))
                return true;
        }
        return false;
    }

}
