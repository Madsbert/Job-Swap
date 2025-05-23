package org.example.jobswap.Controllers.UserTabs;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import org.example.jobswap.Controllers.MainSceneController;
import org.example.jobswap.Model.Match;
import org.example.jobswap.Model.MatchState;
import org.example.jobswap.Model.Profile;
import org.example.jobswap.Persistence.Interfaces.MatchDBInterface;
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

    private List<Match> allMatches;
    private List<Profile> applicationProfiles=new ArrayList<>();
    private List<Profile> requestProfiles=new ArrayList<>();
    private List<Profile> bothProfiles=new ArrayList<>();
    private Profile match=null;

    private int currentProfileID=MainSceneController.getCurrentProfile().getProfileID();

    public UserTabMatches() {
        super("Matches");

        initializeUI();

        loadMatchData();

        showMatchApplications();
        showAcceptedMatch();
        showRequestedMatches();
        showBothInterested();

    }

    private void initializeUI(){
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
    }

    private void  loadMatchData(){
        MatchDBInterface matchDB = new MatchDB();
        allMatches=matchDB.getProfileMatches(currentProfileID);

        for (Match match : allMatches) {
         if (match.getMatchState()== MatchState.BOTH_INTERESTED){
            if(match.getOwnerProfile().getProfileID()==currentProfileID) {
                this.bothProfiles.add(match.getOtherProfile());
            }else{
                this.bothProfiles.add(match.getOwnerProfile());
            }
         }else if(match.getMatchState()==MatchState.MATCH) {
             if (match.getOtherProfile().getProfileID() == currentProfileID) {
                 this.match = match.getOwnerProfile();
             } else {
                 this.match = match.getOtherProfile();
             }
         }else if(match.getOwnerProfile().getProfileID()==currentProfileID){
                this.applicationProfiles.add(match.getOtherProfile());
            }else if (match.getOtherProfile().getProfileID()==currentProfileID){
                this.requestProfiles.add(match.getOwnerProfile());

            }
        }
    }
    private void showAcceptedMatch() {
        if (match != null) {
            List<GridPane> matchingProfilesHBoxes = new ArrayList<>();
            GridPane gridPane = new GridPane();
            gridPane.setHgap(150);
            gridPane.setVgap(5);
            gridPane.setPrefSize(Screen.getPrimary().getBounds().getWidth(), 40);
            gridPane.setStyle("-fx-background-color: #fff; -fx-border-color: #da291c; -fx-border-width: 1.5;");
            gridPane.setPadding(new Insets(25, 25, 25, 25));

            gridPane.add(new Label("Username: " + this.match.getUsername()), 0, 0);
            gridPane.add(new Label("Department: " + this.match.getDepartment()), 0, 1);
            gridPane.add(new Label("Job Titel: " + this.match.getJobTitle()), 1, 0);
            gridPane.add(new Label("Job Description: " + this.match.getJobDescription()), 1, 1);

            gridPane.autosize();
            matchingProfilesHBoxes.add(gridPane);

            matchVBox.getChildren().addAll(matchingProfilesHBoxes);
        }
    }

    private void showRequestedMatches() {
        if (requestProfiles != null) {
            List<GridPane> matchingProfilesHBoxes = new ArrayList<>();
            for (Profile profile : this.requestProfiles) {
                GridPane gridPane = new GridPane();
                gridPane.setHgap(150);
                gridPane.setVgap(5);
                gridPane.setPrefSize(Screen.getPrimary().getBounds().getWidth(), 40);
                gridPane.setStyle("-fx-background-color: #fff; -fx-border-color: #da291c; -fx-border-width: 1.5;");

                gridPane.setPadding(new Insets(25, 25, 25, 25));
                gridPane.add(new Label("Username: " + profile.getUsername()), 0, 0);
                gridPane.add(new Label("Department: " + profile.getDepartment()), 0, 1);
                gridPane.add(new Label("Job Titel: " + profile.getJobTitle()), 1, 0);
                gridPane.add(new Label("Job Description: " + profile.getJobDescription()), 1, 1);
                Button iAmInterestedTooButton = new Button("I am interested too");
                iAmInterestedTooButton.setOnAction(event -> {interestedToo(currentProfileID,profile.getProfileID());});
                gridPane.add(iAmInterestedTooButton, 2, 1);

                gridPane.autosize();
                matchingProfilesHBoxes.add(gridPane);
            }
        requestVBox.getChildren().addAll(matchingProfilesHBoxes);
        }
    }

    /**
     * Displays application matches in Boxes.
     */
    private void showMatchApplications() {
        if (applicationProfiles != null) {
            List<GridPane> matchingProfilesHBoxes = new ArrayList<>();
            for (Profile profile : this.applicationProfiles) {
                GridPane gridPane = new GridPane();
                gridPane.setHgap(150);
                gridPane.setVgap(5);
                gridPane.setPrefSize(Screen.getPrimary().getBounds().getWidth(), 40);
                gridPane.setStyle("-fx-background-color: #fff; -fx-border-color: #da291c; -fx-border-width: 1.5;");

                gridPane.setPadding(new Insets(25, 25, 25, 25));
                gridPane.add(new Label("Username: " + profile.getUsername()), 0, 0);
                gridPane.add(new Label("Department: " + profile.getDepartment()), 0, 1);
                gridPane.add(new Label("Job Titel: " + profile.getJobTitle()), 1, 0);
                gridPane.add(new Label("Job Description: " + profile.getJobDescription()), 1, 1);

                gridPane.autosize();
                matchingProfilesHBoxes.add(gridPane);
            }
            applicationsVBox.getChildren().addAll(matchingProfilesHBoxes);
        }
    }

    private void showBothInterested() {
        if (bothProfiles != null) {
            List<GridPane> matchingProfilesHBoxes = new ArrayList<>();

            for (Profile profile : this.bothProfiles) {
                GridPane gridPane = new GridPane();
                gridPane.setHgap(150);
                gridPane.setVgap(5);
                gridPane.setPrefSize(Screen.getPrimary().getBounds().getWidth(), 40);
                gridPane.setStyle("-fx-background-color: #fff; -fx-border-color: #da291c; -fx-border-width: 1.5;");

                gridPane.setPadding(new Insets(25, 25, 25, 25));
                gridPane.add(new Label("Username: " + profile.getUsername()), 0, 0);
                gridPane.add(new Label("Department: " + profile.getDepartment()), 0, 1);
                gridPane.add(new Label("Job Titel: " + profile.getJobTitle()), 1, 0);
                gridPane.add(new Label("Job Description: " + profile.getJobDescription()), 1, 1);
                //Button applyButton = new Button("Apply for Swap");
                //applyButton.setOnAction(event -> {applyForJobswap(profile);});
                //gridPane.add(applyButton, 2, 1);

                gridPane.autosize();
                matchingProfilesHBoxes.add(gridPane);
            }
            bothVBox.getChildren().addAll(matchingProfilesHBoxes);
        }
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

    private void interestedToo(int ownerProfile, int otherProfile )
    {
        MatchDBInterface db = new MatchDB();
        Match existingMatch = db.getMatchFromProfileIDs(ownerProfile,otherProfile);

        if(existingMatch!=null) {
            existingMatch.updateState(MatchState.BOTH_INTERESTED);
            db.updateMatch(existingMatch);
        }
        refreshMatchDisplay();
    }

    private void refreshMatchDisplay() {
        // Clear existing data
        applicationProfiles.clear();
        requestProfiles.clear();
        bothProfiles.clear();
        match = null;

        // Clear UI
        matchVBox.getChildren().clear();
        requestVBox.getChildren().clear();
        applicationsVBox.getChildren().clear();
        bothVBox.getChildren().clear();

        // Re-add headers
        matchVBox.getChildren().add(new Header("Matches"));
        requestVBox.getChildren().add(new Header("Request"));
        applicationsVBox.getChildren().add(new Header("Applications"));
        bothVBox.getChildren().add(new Header("Both Interested"));

        // Reload and redisplay
        loadMatchData();
        showMatchApplications();
        showAcceptedMatch();
        showRequestedMatches();
        showBothInterested();
    }
}
