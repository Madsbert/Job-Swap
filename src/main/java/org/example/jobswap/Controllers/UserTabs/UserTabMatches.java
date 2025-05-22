package org.example.jobswap.Controllers.UserTabs;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import org.example.jobswap.Controllers.MainSceneController;
import org.example.jobswap.Model.Match;
import org.example.jobswap.Model.Profile;
import org.example.jobswap.Persistence.MatchDB;
import org.example.jobswap.Service.BorderedVBox;
import org.example.jobswap.Service.Header;

import java.util.ArrayList;
import java.util.List;

/**
 * class which sets up the tab Matches
 */
public class UserTabMatches extends javafx.scene.control.Tab {

    private static ScrollPane scrollPane;
    private static VBox matchVBox;
    private static VBox requestVBox;
    private static VBox applicationsVBox;
    private static VBox bothVBox;

    public UserTabMatches() {
        super("Matches");

        scrollPane = new ScrollPane();
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        scrollPane.setPadding(new Insets(25, 25, 25, 25));

        VBox vbox = new VBox();
        scrollPane.setContent(vbox);

        matchVBox = new BorderedVBox();
        matchVBox.getChildren().add(new Header("Matches"));
        vbox.getChildren().add(matchVBox);

        requestVBox = new BorderedVBox();
        requestVBox.getChildren().add(new Header("Request"));
        vbox.getChildren().add(requestVBox);

        applicationsVBox = new BorderedVBox();
        applicationsVBox.getChildren().add(new Header("Applications"));
        vbox.getChildren().add(applicationsVBox);

        bothVBox = new BorderedVBox();
        bothVBox.getChildren().add(new Header("Both Interested"));
        vbox.getChildren().add(bothVBox);

        this.setContent(scrollPane);

        showMatchApplications();
    }

    private void showAcceptedMatches()
    {

    }

    private void showRequestedMatches()
    {

    }

    private void showMatchApplications()
    {
        List<Profile> matchingProfiles = MatchDB.seekAllPossibleProfileMatches(MainSceneController.getCurrentProfile().getProfileID(),
                MainSceneController.getCurrentProfile().getDepartment());

        List<HBox> matchingProfilesHBoxes = new ArrayList<>();

        for (Profile profile : matchingProfiles) {
            HBox hbox = new HBox();
            hbox.setSpacing(10);
            hbox.setPadding(new Insets(25, 25, 25, 25));
            hbox.getChildren().add(new Label("Username: " + profile.getUsername()));
            hbox.getChildren().add(new Label("Department: " + profile.getDepartment()));
            hbox.getChildren().add(new Label("Job Titel: " + profile.getJobTitle()));
            hbox.getChildren().add(new Label("Job Description: " + profile.getJobDescription()));

            matchingProfilesHBoxes.add(hbox);
        }

        applicationsVBox.getChildren().addAll(matchingProfilesHBoxes);
    }

    private void showBothInterested()
    {

    }

    private void acceptMatch(String matchId)
    {

    }

    private void denyMatch(String matchId)
    {

    }

    private void sendMessage(String matchId)
    {

    }
}
