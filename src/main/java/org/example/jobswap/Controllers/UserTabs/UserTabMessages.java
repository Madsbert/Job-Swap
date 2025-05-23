package org.example.jobswap.Controllers.UserTabs;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import org.example.jobswap.Controllers.MainSceneController;
import org.example.jobswap.Model.Match;
import org.example.jobswap.Model.MatchState;
import org.example.jobswap.Model.Message;
import org.example.jobswap.Model.Profile;
import org.example.jobswap.Persistence.Interfaces.MatchDBInterface;
import org.example.jobswap.Persistence.Interfaces.MessageDBInterface;
import org.example.jobswap.Persistence.Interfaces.ProfileDBInterface;
import org.example.jobswap.Persistence.MatchDB;
import org.example.jobswap.Persistence.MessageDB;
import org.example.jobswap.Persistence.ProfileDB;
import org.example.jobswap.Service.BorderedVBox;
import org.example.jobswap.Service.Header;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * class which sets up the tab Messages
 */
public class UserTabMessages extends Tab {
    private static ScrollPane scrollPane;
    private static VBox newContactBox;
    private static VBox oldChatBox;
    private static VBox lastmessageBox;
    private static VBox lastMessageVBox;
    private static VBox oldChatVBow;
    private static VBox newContactVBox;

    public UserTabMessages() {
        super("Messages");

        scrollPane = new ScrollPane();
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        scrollPane.setPadding(new Insets(25, 25, 25, 25));

        VBox vbox = new VBox();
        scrollPane.setContent(vbox);

        lastMessageVBox = new BorderedVBox();
        lastMessageVBox.getChildren().add(new Header("Last Messaged"));
        vbox.getChildren().add(lastMessageVBox);
        lastmessageBox = new VBox();
        lastMessageVBox.getChildren().add(lastmessageBox);
        showLastMessageChats(MainSceneController.getCurrentProfile());


        oldChatVBow = new BorderedVBox();
        oldChatVBow.getChildren().add(new Header("Old Chats"));
        vbox.getChildren().add(oldChatVBow);
        oldChatBox = new VBox();
        oldChatVBow.getChildren().add(oldChatBox);
        showOldChats(MainSceneController.getCurrentProfile());

        newContactVBox = new BorderedVBox();
        newContactVBox.getChildren().add(new Header("New Contact Options"));
        newContactBox = new VBox();
        newContactVBox.getChildren().add(newContactBox);
        showAllAvailableChats(MatchState.BOTH_INTERESTED);

        vbox.getChildren().add(newContactVBox);

        this.setContent(scrollPane);

    }

    private void showAllAvailableChats(MatchState state)
    {
        List<Profile> matchingProfiles = getAllPossibleChatsBasedOnState(MainSceneController.getCurrentProfile().getProfileID(), state);

        List<GridPane> matchingProfilesHBoxes = new ArrayList<>();

        for (Profile matchingProfile : matchingProfiles) {
            GridPane gridPane = new GridPane();
            gridPane.setHgap(150);
            gridPane.setVgap(5);
            gridPane.setPrefSize(Screen.getPrimary().getBounds().getWidth(), 40);
            gridPane.setStyle("-fx-background-color: #fff; -fx-border-color: #da291c; -fx-border-width: 1.5;");

            gridPane.setPadding(new Insets(25, 25, 25, 25));
            gridPane.add(new Label("Username: " + matchingProfile.getUsername()), 0, 0);
            gridPane.add(new Label("Department: " + matchingProfile.getDepartment()), 0, 1);
            gridPane.add(new Label("Job Titel: " + matchingProfile.getJobTitle()), 1, 0);
            gridPane.add(new Label("Job Description: " + matchingProfile.getJobDescription()), 1, 1);
            Button openChatButton = new Button("Open Chat");
            openChatButton.setOnAction(event -> {openChat(MainSceneController.getCurrentProfile().getProfileID(), matchingProfile.getProfileID());});
            gridPane.add(openChatButton, 2, 1);

            gridPane.autosize();
            matchingProfilesHBoxes.add(gridPane);
        }

        newContactBox.getChildren().addAll(matchingProfilesHBoxes);

    }

    public void showLastMessageChats(Profile loggedInProfile){
        MessageDBInterface messageDB = new MessageDB();
        ProfileDBInterface profileDB = new ProfileDB();

        Message newestMessage = messageDB.newestMessageByLoggedInProfile(loggedInProfile.getProfileID());
        Profile receiverProfile;
        HBox newestProfileHBox = new HBox();

        if (newestMessage != null) {
            if (loggedInProfile.getProfileID() == newestMessage.getSenderID()) {
                receiverProfile = profileDB.getProfileFromID(newestMessage.getReceiverID());
            } else {
                receiverProfile = null;
            }
        } else {
            receiverProfile = loggedInProfile;
        }

        GridPane gridPane = new GridPane();
        gridPane.setHgap(150);
        gridPane.setVgap(5);
        gridPane.setPrefSize(Screen.getPrimary().getBounds().getWidth(), 40);
        gridPane.setStyle("-fx-background-color: #fff; -fx-border-color: #da291c; -fx-border-width: 1.5;");

        gridPane.setPadding(new Insets(25, 25, 25, 25));
        gridPane.add(new Label("Username: " + receiverProfile.getUsername()), 0, 0);
        gridPane.add(new Label("Department: " + receiverProfile.getDepartment()), 0, 1);
        gridPane.add(new Label("Job Titel: " + receiverProfile.getJobTitle()), 1, 0);
        gridPane.add(new Label("Job Description: " + receiverProfile.getJobDescription()), 1, 1);
        Button openChatButton = new Button("Open Chat");
        openChatButton.setOnAction(event -> {openChat(MainSceneController.getCurrentProfile().getProfileID(), receiverProfile.getProfileID());});
        gridPane.add(openChatButton, 2, 1);
        gridPane.autosize();

        newestProfileHBox.getChildren().add(gridPane);
        lastmessageBox.getChildren().addAll(newestProfileHBox);
    }

    public void showOldChats(Profile loggedInProfile){
        List<Profile> profilesWhereLoggedInProfileHasChattedWithBefore = getAllPossibleChats(loggedInProfile.getProfileID());

        List<GridPane> matchingProfilesHBoxes = new ArrayList<>();

        for (Profile matchingProfile : profilesWhereLoggedInProfileHasChattedWithBefore) {
            GridPane gridPane = new GridPane();
            gridPane.setHgap(150);
            gridPane.setVgap(5);
            gridPane.setPrefSize(Screen.getPrimary().getBounds().getWidth(), 40);
            gridPane.setStyle("-fx-background-color: #fff; -fx-border-color: #da291c; -fx-border-width: 1.5;");

            gridPane.setPadding(new Insets(25, 25, 25, 25));
            gridPane.add(new Label("Username: " + matchingProfile.getUsername()), 0, 0);
            gridPane.add(new Label("Department: " + matchingProfile.getDepartment()), 0, 1);
            gridPane.add(new Label("Job Titel: " + matchingProfile.getJobTitle()), 1, 0);
            gridPane.add(new Label("Job Description: " + matchingProfile.getJobDescription()), 1, 1);
            Button openChatButton = new Button("Open Chat");
            openChatButton.setOnAction(event -> {openChat(MainSceneController.getCurrentProfile().getProfileID(), matchingProfile.getProfileID());});
            gridPane.add(openChatButton, 2, 1);

            gridPane.autosize();
            matchingProfilesHBoxes.add(gridPane);
        }
        oldChatBox.getChildren().addAll(matchingProfilesHBoxes);

    }

    public void openChat(int profileIDLoggedIn,int ProfileIDReceiver){
        MessageDBInterface messageDB = new MessageDB();
        messageDB.createMessage(new Message(profileIDLoggedIn,ProfileIDReceiver,"THIS IS A TEST MESSAGE FOR TESTING!"));
    }

    private void writeNewMessage(Message message)
    {

    }

    private void showHelpInformation()
    {
    }





    public List<Profile> getAllPossibleChatsBasedOnState(int LoggedInProfileID, MatchState stateOfMatch) {
        MatchDBInterface matchDB = new MatchDB();
        MessageDBInterface messageDB = new MessageDB();

        // Gets all matches
        List<Match> matches = matchDB.getProfileMatches(LoggedInProfileID);
        // Gets the profileID of profiles LoggedInProfile has chatted with before.
        Set<Integer> alreadyChattedProfiles = getAllPossibleChats(LoggedInProfileID)
                .stream()
                .map(Profile::getProfileID)
                .collect(Collectors.toSet());
        // Return List
        List<Profile> possibleChats = new ArrayList<>();

        for (Match match : matches) {
            if (match.getMatchState() == stateOfMatch) {
                Profile owner = match.getOwnerProfile();
                Profile other = match.getOtherProfile();

                // Determine which profile is not the logged-in user
                Profile potentialChat;
                if (owner.getProfileID() == LoggedInProfileID) {
                    potentialChat = other;
                } else {
                    potentialChat = owner;
                }

                // Only add if not already chatted with
                if (!alreadyChattedProfiles.contains(potentialChat.getProfileID())) {
                    possibleChats.add(potentialChat);
                }
            }
        }
        return possibleChats;
    }

    public List<Profile> getAllPossibleChats(int loggedInProfileID) {
        MessageDBInterface messageDB = new MessageDB();
        ProfileDBInterface profileDB = new ProfileDB();
        //Get stored in a Set since it handles Dubs.
        Set<Integer> uniqueProfileIDs = new HashSet<>();

        HashMap<Integer, Integer> allChats = messageDB.allChatsOfProfile(loggedInProfileID);

        for (Map.Entry<Integer, Integer> entry : allChats.entrySet()) {
            int senderID = entry.getKey();
            int receiverID = entry.getValue();
            // Add the other ID (not the loggedInProfileID)
            if (senderID == loggedInProfileID) {
                uniqueProfileIDs.add(receiverID);
            } else {
                uniqueProfileIDs.add(senderID);
            }
        }

        // Convert the unique IDs to Profile objects.
        // This could be done before adding it to the Set, but it doublechecks for dubs.
        List<Profile> possibleChats = new ArrayList<>();
        for (int profileID : uniqueProfileIDs) {
            if (profileID != loggedInProfileID) {
                possibleChats.add(profileDB.getProfileFromID(profileID));
            }
        }

        return possibleChats;
    }
}
