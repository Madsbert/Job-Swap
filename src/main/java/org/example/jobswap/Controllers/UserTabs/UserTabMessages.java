package org.example.jobswap.Controllers.UserTabs;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
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

import java.util.*;
import java.util.stream.Collectors;

/**
 * class which sets up the tab Messages
 */
public class UserTabMessages extends Tab {
    private static ScrollPane messageSectionLeftPane;
    private static VBox newContactBox;
    private static VBox oldChatBox;
    private static VBox lastmessageBox;
    private static VBox lastMessageVBox;
    private static VBox oldChatVBow;
    private static VBox newContactVBox;
    private static BorderedVBox chatArea;
    private static HBox chatHistoryHbox;
    private static TextArea chatHistoryReceiver;
    private static TextArea chatHistorySender;
    private static Header chatHeader;
    private static TextField messageInputField;

    private Profile reseiverProfile;

    public UserTabMessages() {
        super("Messages");

        //setup SplitPane, the same as a HBox but dynamic
        SplitPane primarySplitPane = new SplitPane();
        primarySplitPane.setDividerPositions(0.70); //70% left 30% right

        //setup LeftSide
        messageSectionLeftPane = new ScrollPane();
        messageSectionLeftPane.setFitToHeight(true);
        messageSectionLeftPane.setFitToWidth(true);
        messageSectionLeftPane.setPadding(new Insets(25, 25, 25, 25));

        VBox messageUnderTabs = new VBox();
        messageSectionLeftPane.setContent(messageUnderTabs);

        //Sets up the Last Messaged UnderTab
        lastMessageVBox = new BorderedVBox();
        lastMessageVBox.getChildren().add(new Header("Last Messaged"));
        messageUnderTabs.getChildren().add(lastMessageVBox);
        lastmessageBox = new VBox();
        lastMessageVBox.getChildren().add(lastmessageBox);
        showLastMessageChats(MainSceneController.getCurrentProfile());

        //Sets up the old Message UnderTab
        oldChatVBow = new BorderedVBox();
        oldChatVBow.getChildren().add(new Header("Old Chats"));
        messageUnderTabs.getChildren().add(oldChatVBow);
        oldChatBox = new VBox();
        oldChatVBow.getChildren().add(oldChatBox);
        showOldChats(MainSceneController.getCurrentProfile());

        //Sets up the new contact Message UnderTab
        newContactVBox = new BorderedVBox();
        newContactVBox.getChildren().add(new Header("New Contact Options"));
        newContactBox = new VBox();
        newContactVBox.getChildren().add(newContactBox);
        showAllAvailableChats(MatchState.BOTH_INTERESTED);
        messageUnderTabs.getChildren().add(newContactVBox);

        setupChatUI();

        //adds each BorderedVBox to the side of the SplitPane
        primarySplitPane.getItems().addAll(messageSectionLeftPane,chatArea);

        this.setContent(primarySplitPane);

    }

    private void setupChatUI(){
        //setup RightSide
        chatArea = new BorderedVBox();
        chatHeader = new Header("Chat");
        chatArea.setPadding(new Insets(25, 25, 25, 25));
        chatArea.getChildren().add(chatHeader);

        //chat history created with 2 textArea inside of a Hbox, has build in scroll if there is a lot of text.
        chatHistoryHbox = new HBox();

        //Receiver Section
        chatHistoryReceiver = new TextArea();
        chatHistoryReceiver.setEditable(false);
        chatHistoryReceiver.setWrapText(true);
        VBox.setVgrow(chatHistoryReceiver, Priority.ALWAYS);  // Fill where there is space
        chatHistoryReceiver.setPrefHeight(Region.USE_COMPUTED_SIZE);  //Region.USE_COMPUTED_SIZE = don't use fixed height.
        chatHistoryReceiver.setStyle("-fx-text-fill: #da291c");

        //Sender Section
        chatHistorySender = new TextArea();
        chatHistorySender.setEditable(false);
        chatHistorySender.setWrapText(true);
        VBox.setVgrow(chatHistorySender, Priority.ALWAYS);  // Fill where there is space
        chatHistorySender.setPrefHeight(Region.USE_COMPUTED_SIZE);  //Region.USE_COMPUTED_SIZE = don't use fixed height.
        chatHistorySender.setStyle("-fx-text-alignment: right;");
        chatHistorySender.setStyle("-fx-text-fill: #1CDA29;");

        chatHistoryHbox.getChildren().addAll(chatHistoryReceiver,chatHistorySender);

        //setup bottom of chat.
        HBox messageInputBox = new HBox(10);
        messageInputBox.setAlignment(Pos.CENTER_RIGHT);

        messageInputField = new TextField();
        messageInputField.setPrefWidth(chatArea.getWidth());
        HBox.setHgrow(messageInputField, Priority.ALWAYS); //Dynamic spacing for messageField.
        messageInputField.setPromptText("Type your message...");
        //setup button
        Button sendButton = new Button("Send");
        sendButton.setOnAction(event -> {sendMessage(MainSceneController.getCurrentProfile(),reseiverProfile);});

        //adds it to the RightSide VBox.
        messageInputBox.getChildren().addAll(messageInputField, sendButton);
        chatArea.getChildren().addAll(chatHistoryHbox,messageInputBox);

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
            Button openChatButton = new Button("Chat with");
            openChatButton.setOnAction(event -> {openChat(MainSceneController.getCurrentProfile(), matchingProfile);});
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
        Button openChatButton = new Button("Chat with");
        openChatButton.setOnAction(event -> {openChat(MainSceneController.getCurrentProfile(), receiverProfile);});
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
            Button openChatButton = new Button("Chat with");
            openChatButton.setOnAction(event -> {openChat(MainSceneController.getCurrentProfile(), matchingProfile);});
            gridPane.add(openChatButton, 2, 1);

            gridPane.autosize();
            matchingProfilesHBoxes.add(gridPane);
        }
        oldChatBox.getChildren().addAll(matchingProfilesHBoxes);

    }

    public void openChat(Profile loggedInProfile,Profile receiverProfile){
        reseiverProfile = receiverProfile;

        chatHeader.setText("Chat with "+receiverProfile.getUsername());
        //clear it.
        chatHistoryReceiver.clear();
        chatHistorySender.clear();

        //array of messages from database between the 2 profiles

        MessageDBInterface messageDB = new MessageDB();
        List<Message> allMessagesBetweenProfiles = messageDB.getMessages(loggedInProfile.getProfileID(),receiverProfile.getProfileID());
        List<String> messagesSender = new ArrayList<>();
        List<String> messagesReceiver = new ArrayList<>();

        //Sort array into sender and receiver arrays.
        for (Message message : allMessagesBetweenProfiles) {
            if (message.getSenderID() == loggedInProfile.getProfileID()) {
                messagesSender.add(message.getText());
                messagesReceiver.add(" ");
            }
            else {
                messagesReceiver.add(message.getText());
                messagesSender.add(" ");
            }
        }

        //add message text to each text area.
        for (String text : messagesSender) {
            chatHistorySender.appendText(text+"\n");

        }
        for (String text : messagesReceiver) {
            chatHistoryReceiver.appendText(text+"\n");
        }
    }

    private void sendMessage(Profile loggedInProfileID, Profile receiverProfileID)
    {
        MessageDBInterface messageDB = new MessageDB();
        Message newMessage = new Message(loggedInProfileID.getProfileID(),receiverProfileID.getProfileID(),messageInputField.getText());
        messageDB.createMessage(newMessage);

    }

    private void showHelpInformation()
    {
    }


    public List<Profile> getAllPossibleChatsBasedOnState(int LoggedInProfileID, MatchState stateOfMatch) {
        MatchDBInterface matchDB = new MatchDB();

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
