package org.example.jobswap.View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.jobswap.Foundation.DBConnection;
import org.example.jobswap.Model.AccessLevel;
import org.example.jobswap.Model.Profile;
import org.example.jobswap.Persistence.ProfileDB;

import java.io.IOException;
import java.sql.Connection;
import java.util.Objects;

public class JobswapApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(JobswapApplication.class.getResource("/org/example/jobswap/Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

        DBConnection.getConnection();
        Profile jakob = new Profile(AccessLevel.EMPLOYEE,"Jakob Jessen","Danfoss Sales","CSO","IT","Jakob2rune",true,"Head of IT and Sales");
        ProfileDB profileDB = new ProfileDB();

        profileDB.createNewProfile(jakob);


    }

    public static void main(String[] args) {
        launch();
    }
}