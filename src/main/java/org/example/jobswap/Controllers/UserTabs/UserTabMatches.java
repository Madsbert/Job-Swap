package org.example.jobswap.Controllers.UserTabs;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import org.example.jobswap.Controllers.MainSceneController;
import org.example.jobswap.Controllers.UpdatableTab;
import org.example.jobswap.Model.Match;
import org.example.jobswap.Model.MatchState;
import org.example.jobswap.Model.Profile;
import org.example.jobswap.Persistence.Interfaces.MatchDBInterface;
import org.example.jobswap.Persistence.MatchDB;
import org.example.jobswap.Service.BorderedVBox;
import org.example.jobswap.Service.Header;
import org.example.jobswap.Service.WrapTextLabel;

import java.util.ArrayList;
import java.util.List;

/**
 * class which sets up the tab Matches
 */
public final class UserTabMatches extends UpdatableTab {

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

    private int currentProfileID = MainSceneController.getCurrentProfile().getProfileID();

    /**
     *Constructor for {@link UserTabMatches}
     */
    public UserTabMatches() {
        super("Matches");

        initializeUI();

        loadMatchData();

        showMatchApplications();
        showAcceptedMatch();
        showRequestedMatches();
        showBothInterested();

    }

    /**
     * Sets up UI in {@link UserTabMatches}
     */
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

    /**
     * Loads all {@link Match}es and sorts them to the relevant ArrayLists
     */
    private void  loadMatchData(){
        MatchDBInterface matchDB = new MatchDB();
        allMatches=matchDB.getProfileMatches(currentProfileID);

        for (Match match : allMatches) {

            Profile ownerProfile = match.getOwnerProfile();
            Profile otherProfile = match.getOtherProfile();

            if (match.getMatchState()== MatchState.BOTH_INTERESTED){
                this.bothProfiles.add(ownerProfile.getProfileID() == currentProfileID ? otherProfile : ownerProfile);

            }else if(match.getMatchState()==MatchState.MATCH) {
                this.match = (otherProfile.getProfileID() == currentProfileID ? otherProfile : ownerProfile);
                
            }else if(match.getOwnerProfile().getProfileID()==currentProfileID
                    && (match.getMatchState()==MatchState.APPLICATION||match.getMatchState()==MatchState.REQUESTED)){

                this.applicationProfiles.add(match.getOtherProfile());

            }else if (match.getOtherProfile().getProfileID()==currentProfileID
                    && (match.getMatchState()==MatchState.APPLICATION||match.getMatchState()==MatchState.REQUESTED)){

                this.requestProfiles.add(match.getOwnerProfile());
            }
        }
    }

    /**
     *Adds a{@Profile}to the match{@code Vbox} if exists with the {@link Match} {@link Profile}
     */
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

    /**
     * Adds {@link Profile}es to the request{@link VBox} if exists with the requested {@link Match}es
     */
    private void showRequestedMatches() {
        if (requestProfiles != null) {
            List<GridPane> matchingProfilesHBoxes = new ArrayList<>();
            for (Profile profile : this.requestProfiles) {
                GridPane gridPane = new GridPane();

                ColumnConstraints col1 = new ColumnConstraints();
                col1.setPercentWidth(25);
                ColumnConstraints col2 = new ColumnConstraints();
                col2.setPercentWidth(50);
                ColumnConstraints col3 = new ColumnConstraints();
                col3.setPercentWidth(25);
                gridPane.getColumnConstraints().addAll(col1, col2, col3);

                gridPane.setHgap(100);
                gridPane.setVgap(5);
                gridPane.setPrefSize(Screen.getPrimary().getBounds().getWidth(), 40);
                gridPane.setStyle("-fx-background-color: #fff; -fx-border-color: #da291c; -fx-border-width: 1.5;");

                gridPane.setPadding(new Insets(20, 20, 20, 20));
                gridPane.add(new WrapTextLabel("Username: " + profile.getUsername()), 0, 0);
                gridPane.add(new WrapTextLabel("Department: " + profile.getDepartment()), 0, 1);
                gridPane.add(new WrapTextLabel("Job Titel: " + profile.getJobTitle()), 1, 0);
                gridPane.add(new WrapTextLabel("Job Description: " + profile.getJobDescription()), 1, 1);
                Button iAmInterestedTooButton = new Button("I'm interested too");
                iAmInterestedTooButton.setOnAction(event -> {iAmInterestedToo(currentProfileID,profile.getProfileID());});
                gridPane.add(iAmInterestedTooButton, 2, 0);

                Button denyButton = new Button("Deny Match");
                denyButton.setOnAction(event -> {denyMatch(currentProfileID,profile.getProfileID());});
                gridPane.add(denyButton, 2, 1);

                matchingProfilesHBoxes.add(gridPane);
                gridPane.autosize();


            }
        requestVBox.getChildren().addAll(matchingProfilesHBoxes);
        }
    }

    /**
     * Adds {@link Profile}es to the application{@link VBox} if exists with the application {@link Match}es
     */
    private void showMatchApplications() {
        if (applicationProfiles != null) {
            List<GridPane> matchingProfilesHBoxes = new ArrayList<>();
            for (Profile profile : this.applicationProfiles) {
                GridPane gridPane = new GridPane();
                ColumnConstraints col1 = new ColumnConstraints();
                col1.setPercentWidth(25);
                ColumnConstraints col2 = new ColumnConstraints();
                col2.setPercentWidth(75);
                gridPane.getColumnConstraints().addAll(col1, col2);

                gridPane.setHgap(150);
                gridPane.setVgap(5);
                gridPane.setPrefSize(Screen.getPrimary().getBounds().getWidth(), 40);
                gridPane.setStyle("-fx-background-color: #fff; -fx-border-color: #da291c; -fx-border-width: 1.5;");

                gridPane.setPadding(new Insets(25, 25, 25, 25));
                gridPane.add(new WrapTextLabel("Username: " + profile.getUsername()), 0, 0);
                gridPane.add(new WrapTextLabel("Department: " + profile.getDepartment()), 0, 1);
                gridPane.add(new WrapTextLabel("Job Titel: " + profile.getJobTitle()), 1, 0);
                gridPane.add(new WrapTextLabel("Job Description: " + profile.getJobDescription()), 1, 1);

                gridPane.autosize();
                matchingProfilesHBoxes.add(gridPane);
            }
            applicationsVBox.getChildren().addAll(matchingProfilesHBoxes);
        }
    }

    /**
     * Adds {@link Profile}es to the both{@link  VBox} if exists with the both interested {@link Match}es
     */
    private void showBothInterested() {
        if (bothProfiles != null) {
            List<GridPane> matchingProfilesHBoxes = new ArrayList<>();

            for (Profile profile : this.bothProfiles) {
                GridPane gridPane = new GridPane();
                ColumnConstraints col1 = new ColumnConstraints();
                col1.setPercentWidth(25);
                ColumnConstraints col2 = new ColumnConstraints();
                col2.setPercentWidth(50);
                ColumnConstraints col3 = new ColumnConstraints();
                col3.setPercentWidth(15);
                gridPane.getColumnConstraints().addAll(col1, col2, col3);

                gridPane.setHgap(100);
                gridPane.setVgap(5);
                gridPane.setPrefSize(Screen.getPrimary().getBounds().getWidth(), 40);
                gridPane.setStyle("-fx-background-color: #fff; -fx-border-color: #da291c; -fx-border-width: 1.5;");

                gridPane.setPadding(new Insets(20, 20, 20, 20));
                gridPane.add(new WrapTextLabel("Username: " + profile.getUsername()), 0, 0);
                gridPane.add(new WrapTextLabel("Department: " + profile.getDepartment()), 0, 1);
                gridPane.add(new WrapTextLabel("Job Titel: " + profile.getJobTitle()), 1, 0);
                gridPane.add(new WrapTextLabel("Job Description: " + profile.getJobDescription()), 1, 1);

                Button acceptMatchButton = new Button("Accept Match");
                acceptMatchButton.setOnAction(event -> {
                    acceptMatch(currentProfileID,profile.getProfileID());});
                gridPane.add(acceptMatchButton, 2, 0);

                Button denyButton = new Button("Deny Match");
                denyButton.setOnAction(event -> {denyMatch(currentProfileID,profile.getProfileID());});
                gridPane.add(denyButton, 2, 1);

                gridPane.autosize();
                matchingProfilesHBoxes.add(gridPane);



            }
            bothVBox.getChildren().addAll(matchingProfilesHBoxes);
        }
    }

    /**
     * Accepts an appliction so the {@link Match} now have the {@link MatchState} both interested
     * @param ownerProfile a {@link Profile}id of the owners' {@link Profile}
     * @param otherProfile a {@link Profile}id of the others' {@link Profile}
     */
    private void iAmInterestedToo(int ownerProfile, int otherProfile )
    {
        MatchDBInterface db = new MatchDB();
        Match existingMatch = db.getMatchFromProfileIDs(ownerProfile,otherProfile);

        if(existingMatch!=null) {
            existingMatch.updateState(MatchState.BOTH_INTERESTED);
            db.updateMatch(existingMatch);
        }
        refreshMatchDisplay();
    }

    /**
     * Denys a Request or Both intersested so the {@link Match} will be deleted
     * @param ownerProfile a {@link Profile}id of the owners' {@link Profile}
     * @param otherProfile a {@link Profile}id of the others' {@link Profile}
     */
    private void denyMatch(int ownerProfile, int otherProfile)
    {
        MatchDBInterface db = new MatchDB();
        Match existingMatch = db.getMatchFromProfileIDs(ownerProfile,otherProfile);

        if(existingMatch!=null) {
            db.deleteMatch(ownerProfile,otherProfile);
        }
        refreshMatchDisplay();
    }


    private void sendMessage(String matchId)
    {

    }

    /**
     * Accepts an Interested too so the {@link Match} now have the {@link MatchState} Match
     * Or OneProfileIsReadyToMatch depending on whether the other {@link Profile} has pushed the Interested too {@code Button}
     * @param ownerProfile a {@link Profile}id of the owners' {@link Profile}
     * @param otherProfile a {@link Profile}id of the others' {@link Profile}
     */
    private void acceptMatch(int ownerProfile, int otherProfile )
    {
        MatchDBInterface db = new MatchDB();
        Match existingMatch = db.getMatchFromProfileIDs(ownerProfile,otherProfile);

        if(existingMatch!=null) {
            db.updateMatchStateFromBothInterestedToMatch(existingMatch,MainSceneController.getCurrentProfile());
        }
        refreshMatchDisplay();
    }

    /**
     * Refeshes the display so it matches the database
     */
    public void refreshMatchDisplay() {
        // Update ID from active Profile
        currentProfileID = MainSceneController.getCurrentProfile().getProfileID();

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

    /**
     * Updates contents of tab
     */
    @Override
    public void update() {
        refreshMatchDisplay();
    }
}
