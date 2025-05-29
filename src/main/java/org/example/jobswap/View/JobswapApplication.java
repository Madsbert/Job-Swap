package org.example.jobswap.View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.example.jobswap.Controllers.LoginController;
import org.example.jobswap.Foundation.DBConnection;
import org.example.jobswap.Model.AccessLevel;
import org.example.jobswap.Model.Match;
import org.example.jobswap.Model.Profile;
import org.example.jobswap.Persistence.Interfaces.LoginDBInterface;
import org.example.jobswap.Persistence.Interfaces.MatchDBInterface;
import org.example.jobswap.Persistence.Interfaces.ProfileDBInterface;
import org.example.jobswap.Persistence.LoginDB;
import org.example.jobswap.Persistence.MatchDB;
import org.example.jobswap.Persistence.ProfileDB;
import org.example.jobswap.Service.PasswordEncrypter;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

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