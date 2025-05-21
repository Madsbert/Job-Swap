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

public class JobswapApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(JobswapApplication.class.getResource("/org/example/jobswap/Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

        DBConnection.getConnection();
//        Profile jakob = new Profile(
//                AccessLevel.EMPLOYEE,
//                "Anakin Skywalker",
//                "Not just the Men",
//                "Danfoss Redan",
//                "But the women",
//                "And the children too",
//                "Sales",
//                true);;
//        ProfileDB profileDB = new ProfileDB();

//        profileDB.createNewProfile(jakob);

        MatchDBInterface match = new MatchDB();
        List<Profile> profiles= match.seekAllPossibleProfileMatches(10000, "Danfoss Redan");
        System.out.println("Found " + profiles.size() + " matching profiles:");
        System.out.println("------------------------------------------------");

        for (Profile profile : profiles) {
            System.out.println("Profile ID: " + profile.getProfileID());
            System.out.println("Name: " + profile.getName());
            System.out.println("Username: " + profile.getUsername());
            System.out.println("Department: " + profile.getDepartment());
            System.out.println("Job Title: " + profile.getJobTitle());
            System.out.println("Job Category: " + profile.getJobCategory());
            System.out.println("Access Level: " + profile.getAccessLevel());
            System.out.println("Actively Seeking: " + (profile.isActivelySeeking() ? "Yes" : "No"));
            System.out.println("------------------------------------------------");
        }


    }

    public static void main(String[] args) {
        launch();
    }
}