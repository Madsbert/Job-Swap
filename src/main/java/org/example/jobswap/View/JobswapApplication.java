package org.example.jobswap.View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.jobswap.Foundation.DBConnection;
import org.example.jobswap.Model.AccessLevel;
import org.example.jobswap.Model.Match;
import org.example.jobswap.Model.Profile;
import org.example.jobswap.Persistence.Interfaces.MatchDBInterface;
import org.example.jobswap.Persistence.MatchDB;
import org.example.jobswap.Persistence.ProfileDB;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.Objects;

/**
 * class to start application
 */
public class JobswapApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(JobswapApplication.class.getResource("/org/example/jobswap/Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}