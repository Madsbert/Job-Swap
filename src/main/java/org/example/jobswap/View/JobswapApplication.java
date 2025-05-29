package org.example.jobswap.View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * class to start application
 */
public class JobswapApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(JobswapApplication.class.getResource("/org/example/jobswap/Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Login");
        //adds icon
        try {
            Image icon = new Image(getClass().getResource("/org/example/jobswap/JobSwapIcon.png").toExternalForm());
            stage.getIcons().add(icon);
        }
        catch (NullPointerException e) {
            System.out.println("Image not found");
        }

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}