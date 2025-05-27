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

import java.awt.event.ActionEvent;
import java.util.*;
import java.util.stream.Collectors;

/**
 * class which sets up the tab Messages.
 * The tab is divided into two main sections:
 *  - Left side: Displays contact lists categorized by conversation status
 *  - Right side: Shows the chat history and provides input for new messages
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
    private static TextArea chatHistory;
    private static Header chatHeader;
    private static TextField messageInputField;

    private Profile reseiverProfile;

    /**
     * Constructs a new Messages tab with all UI components and method-usage.
     */
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

    /**
     * Sets up the UI components of the ChatSection.
     */
    private void setupChatUI(){
        //setup RightSide
        chatArea = new BorderedVBox();
        chatHeader = new Header("Chat");
        chatArea.setPadding(new Insets(25, 25, 25, 25));
        chatArea.getChildren().add(chatHeader);

        //chat history created with a textfield, has build in scroll if there is a lot of text.
        chatHistory = new TextArea();
        chatHistory.setEditable(false);
        chatHistory.setWrapText(true);
        VBox.setVgrow(chatHistory, Priority.ALWAYS);  // Fill where there is space
        chatHistory.setPrefHeight(Region.USE_COMPUTED_SIZE);  //Region.USE_COMPUTED_SIZE = don't use fixed height.

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
        chatArea.getChildren().addAll(chatHistory,messageInputBox);
    }

    /**
     * Displays all available chat options based on the specified {@link MatchState}.
     * Only shows {@link Profile}s that haven't been chatted with before.
     * @param state The {@link MatchState} to filter available chats.
     */
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

    /**
     * Displays the {@link Profile} Information of the most recent conversation, in the "Last Messaged" section.
     * Adds a "Chat with" {@link Button}.
     * @param loggedInProfile
     */
    public void showLastMessageChats(Profile loggedInProfile){
        MessageDBInterface messageDB = new MessageDB();
        ProfileDBInterface profileDB = new ProfileDB();

        Message newestMessage = messageDB.newestMessageByLoggedInProfile(loggedInProfile.getProfileID());
        HBox newestProfileHBox = new HBox();
        //does nothing if there is no messages at all.
        if (newestMessage != null) {
            if (loggedInProfile.getProfileID() == newestMessage.getSenderID()) {
                reseiverProfile = profileDB.getProfileFromID(newestMessage.getReceiverID());
            }
            if (loggedInProfile.getProfileID() == newestMessage.getReceiverID()) {
                reseiverProfile = profileDB.getProfileFromID(newestMessage.getSenderID());
            }

            GridPane gridPane = new GridPane();
            gridPane.setHgap(150);
            gridPane.setVgap(5);
            gridPane.setPrefSize(Screen.getPrimary().getBounds().getWidth(), 40);
            gridPane.setStyle("-fx-background-color: #fff; -fx-border-color: #da291c; -fx-border-width: 1.5;");

            gridPane.setPadding(new Insets(25, 25, 25, 25));
            gridPane.add(new Label("Username: " + reseiverProfile.getUsername()), 0, 0);
            gridPane.add(new Label("Department: " + reseiverProfile.getDepartment()), 0, 1);
            gridPane.add(new Label("Job Titel: " + reseiverProfile.getJobTitle()), 1, 0);
            gridPane.add(new Label("Job Description: " + reseiverProfile.getJobDescription()), 1, 1);
            Button openChatButton = new Button("Chat with");
            openChatButton.setOnAction(event -> {openChat(MainSceneController.getCurrentProfile(), reseiverProfile);});
            gridPane.add(openChatButton, 2, 1);
            gridPane.autosize();

            newestProfileHBox.getChildren().add(gridPane);
            lastmessageBox.getChildren().addAll(newestProfileHBox);
        }
        else {
            System.out.println("No new Message");
        }
    }

    /**
     * Displays the {@link Profile} Information of all conversations, in the "Old Messages" section.
     * Adds a "Chat with" {@link Button}.
     * @param loggedInProfile
     */
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

    /**
     * Opens a chat conversation between two {@link Profile}s.
     * Displays the chat history and updates the UI to show the active conversation.
     * @param loggedInProfile The currently logged in user's {@link Profile}
     * @param receiverProfile The {@link Profile} of the user being chatted with
     */
    public void openChat(Profile loggedInProfile,Profile receiverProfile){
        reseiverProfile = receiverProfile;

        chatHeader.setText("Chat with "+receiverProfile.getUsername());
        //clear it.
        chatHistory.clear();

        //array of messages from database between the 2 profiles
        List<Message> AllmessagesBetweenProfiles = new ArrayList<>();
        MessageDBInterface messageDB = new MessageDB();
        AllmessagesBetweenProfiles = messageDB.getMessages(loggedInProfile.getProfileID(),receiverProfile.getProfileID());

        for (Message message : AllmessagesBetweenProfiles) {


            if (message.getSenderID() == loggedInProfile.getProfileID()) {
                chatHistory.appendText("Me: \n" + message.getText());
            }
            else {
                chatHistory.appendText(receiverProfile.getUsername()+": \n" + message.getText());
            }
            chatHistory.appendText("\n");
        }
    }
    /**
     * Sends a new message in the current conversation.
     * Updates the database and refreshes the chat display.
     * @param loggedInProfileID The {@link Profile} of the message sender
     * @param receiverProfileID The {@link Profile} of the message recipient
     */
    private void sendMessage(Profile loggedInProfileID, Profile receiverProfileID)
    {
        MessageDBInterface messageDB = new MessageDB();
        Message newMessage = new Message(loggedInProfileID.getProfileID(),receiverProfileID.getProfileID(),messageInputField.getText());
        messageDB.createMessage(newMessage);

        messageInputField.clear();
        // Update
        refreshMessageTab();
    }
    /**
     * Retrieves all possible chat partners based on {@link MatchState}.
     * Filters out {@link Profile}s that have already been chatted with.
     * A Set Collection is used since it doesn't allow duplicates.
     * @param LoggedInProfileID The ID of the logged in user
     * @param stateOfMatch The {@link MatchState} to filter by
     * @return List of {@link Profile} objects representing potential chat partners
     */
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
    /**
     * Retrieves all {@link Profile} the logged in user has previously chatted with.
     * A Set Collection is used since it doesn't allow duplicates.
     * @param loggedInProfileID The ID of the logged in user
     * @return List of {@link Profile} objects representing previous chat partners
     */
    public List<Profile> getAllPossibleChats(int loggedInProfileID) {
        MessageDBInterface messageDB = new MessageDB();
        ProfileDBInterface profileDB = new ProfileDB();
        //Get stored in a Set since it handles Dups.
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
        // This could be done before adding it to the Set, but it doublechecks for dups.
        List<Profile> possibleChats = new ArrayList<>();
        for (int profileID : uniqueProfileIDs) {
            if (profileID != loggedInProfileID) {
                possibleChats.add(profileDB.getProfileFromID(profileID));
            }
        }

        return possibleChats;
    }
    /**
     * Refreshes all message tab components.
     * Reloads contact lists and reopens the current chat if one is active.
     */
    private void refreshMessageTab(){
        // Clear existing content
        lastmessageBox.getChildren().clear();
        oldChatBox.getChildren().clear();
        newContactBox.getChildren().clear();

        // Reload
        showLastMessageChats(MainSceneController.getCurrentProfile());
        showOldChats(MainSceneController.getCurrentProfile());
        showAllAvailableChats(MatchState.BOTH_INTERESTED);

        //open chat again
        if (reseiverProfile != null) {
            openChat(MainSceneController.getCurrentProfile(), reseiverProfile);
        }
    }
}
