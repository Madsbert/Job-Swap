package org.example.jobswap.Controllers.UserTabs;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.jobswap.Controllers.MainSceneController;
import org.example.jobswap.Controllers.UpdatableTab;
import org.example.jobswap.Model.Match;
import org.example.jobswap.Model.MatchState;
import org.example.jobswap.Model.Message;
import org.example.jobswap.Model.Profile;
import org.example.jobswap.Persistence.Interfaces.MatchDBInterface;
import org.example.jobswap.Persistence.Interfaces.MessageDBInterface;
import org.example.jobswap.Persistence.Interfaces.ProfileDBInterface;
import org.example.jobswap.Persistence.Interfaces.ReportDBInterface;
import org.example.jobswap.Persistence.MatchDB;
import org.example.jobswap.Persistence.MessageDB;
import org.example.jobswap.Persistence.ProfileDB;
import org.example.jobswap.Persistence.ReportDB;
import org.example.jobswap.Service.BorderedVBox;
import org.example.jobswap.Service.Header;
import org.example.jobswap.Service.WrapTextLabel;


import java.util.*;
import java.util.stream.Collectors;

/**
 * class which sets up the tab Messages.
 * The tab is divided into two main sections:
 *  - Left side: Displays contact lists categorized by conversation status
 *  - Right side: Shows the chat history and provides input for new messages
 */
public class UserTabMessages extends UpdatableTab {
    private static ScrollPane messageSectionLeftPane;
    private static VBox newContactBox;
    private static VBox oldChatBox;
    private static VBox lastmessageBox;
    private static VBox lastMessageVBox;
    private static VBox oldChatVBow;
    private static VBox newContactVBox;
    private static BorderedVBox chatArea;
    private static ScrollPane chatHistory;
    private static Header chatHeader;
    private static TextField messageInputField;
    private static VBox chatAreaVBox;

    private Profile receiverProfile;

    private static Timeline updater;

    /**
     * Constructs a new Messages tab with all UI components and method-usage.
     */
    public UserTabMessages() {
        super("Messages");
        initizializeUI();
        sortMessages();
        setupChatUI();

        //Game Loop Design Pattern
        //Proces Input - Send button
        //Update -get the message from database
        //Render - update the screen
        updater = new Timeline();
        updater.setCycleCount(Timeline.INDEFINITE);
        updater.getKeyFrames().add(new KeyFrame(Duration.seconds(1), event -> {update();}));
        updater.play();

    }

    /**
     * Sets up UI
     */
    private void initizializeUI(){
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
        lastMessageVBox.getChildren().add(new Header("Received"));
        messageUnderTabs.getChildren().add(lastMessageVBox);
        lastmessageBox = new VBox();
        lastMessageVBox.getChildren().add(lastmessageBox);

        //Sets up the old Message UnderTab
        oldChatVBow = new BorderedVBox();
        oldChatVBow.getChildren().add(new Header("Answered"));
        messageUnderTabs.getChildren().add(oldChatVBow);
        oldChatBox = new VBox();
        oldChatVBow.getChildren().add(oldChatBox);

        //Sets up the new contact Message UnderTab
        newContactVBox = new BorderedVBox();
        newContactVBox.getChildren().add(new Header("New Contact Options"));
        newContactBox = new VBox();
        newContactVBox.getChildren().add(newContactBox);
        showAllAvailableChats(MatchState.BOTH_INTERESTED);
        messageUnderTabs.getChildren().add(newContactVBox);

        chatArea = new BorderedVBox();

        //adds each BorderedVBox to the side of the SplitPane
        primarySplitPane.getItems().addAll(messageSectionLeftPane,chatArea);

        this.setContent(primarySplitPane);

    }

    /**
     * Sets up the UI components of the ChatSection.
     */
    private void setupChatUI(){
        //setup RightSide
        chatHeader = new Header("Chat");
        chatArea.setPadding(new Insets(25, 25, 25, 25));
        chatArea.getChildren().add(chatHeader);

        //chat history created with a ScrollPane with a Vbox inside
        chatHistory = new ScrollPane();
        chatHistory.setFitToWidth(true);  //don't use width
        chatHistory.setPrefHeight(600);
        chatHistory.getStyleClass().add("chat-history");

        chatAreaVBox= new VBox();
        chatAreaVBox.setFillWidth(true);
        chatHistory.setContent(chatAreaVBox);
        chatAreaVBox.getStyleClass().add("chat-history");




        //setup bottom of chat.
        HBox messageInputBox = new HBox(10);
        messageInputBox.setAlignment(Pos.CENTER_RIGHT);

        messageInputField = new TextField();
        messageInputField.setPrefWidth(chatArea.getWidth());
        HBox.setHgrow(messageInputField, Priority.ALWAYS); //Dynamic spacing for messageField.
        messageInputField.setPromptText("Type your message...");
        //setup button
        Button sendButton = new Button("Send");
        sendButton.setOnAction(event -> {sendMessage(MainSceneController.getCurrentProfile(), receiverProfile);});

        //adds it to the Chat UI VBox.
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

                ColumnConstraints col1 = new ColumnConstraints();
                col1.setPercentWidth(25);
                ColumnConstraints col2 = new ColumnConstraints();
                col2.setPercentWidth(60);
                ColumnConstraints col3 = new ColumnConstraints();
                col3.setPercentWidth(15);
                gridPane.getColumnConstraints().addAll(col1, col2, col3);

                gridPane.setHgap(80);
                gridPane.setVgap(5);
                gridPane.setPrefSize(Screen.getPrimary().getBounds().getWidth(), 40);
                gridPane.setStyle("-fx-background-color: #fff; -fx-border-color: #da291c; -fx-border-width: 1.5;");

                gridPane.setPadding(new Insets(20, 20, 20, 20));
                gridPane.add(new WrapTextLabel("Username: " + matchingProfile.getUsername()), 0, 0);
                gridPane.add(new WrapTextLabel("Department: " + matchingProfile.getDepartment()), 0, 1);
                gridPane.add(new WrapTextLabel("Job Titel: " + matchingProfile.getJobTitle()), 1, 0);
                gridPane.add(new WrapTextLabel("Job Description: " + matchingProfile.getJobDescription()), 1, 1);

                Button openChatButton = new Button("Chat  📝");
                openChatButton.setOnAction(event -> {
                    openChat(MainSceneController.getCurrentProfile(), matchingProfile);
                });
                gridPane.add(openChatButton, 2, 0);


                Button reportButton = new Button("Report❗");
                reportButton.setStyle("-fx-text-fill: red;");
                reportButton.setOnAction(event -> {
                    reportUser(MainSceneController.getCurrentProfile().getProfileID(), matchingProfile.getProfileID());
                });
                gridPane.add(reportButton, 2, 1);

                gridPane.autosize();
                matchingProfilesHBoxes.add(gridPane);
            }
            newContactBox.getChildren().addAll(matchingProfilesHBoxes);
    }

    /**
     * Displays the {@link Profile} Information of the most recent conversation, in the "Last Messaged" section
     * if a Report between the other user hasn't been reported
     * Adds a "Chat with" {@link Button}.
     * @param receivedMessages {@link Message Messages} where the last receiver was the current user
     */
    public void showReceivedMessageProfiles(List<Message> receivedMessages){
        ProfileDBInterface profileDB = new ProfileDB();
        ReportDBInterface reportDB = new ReportDB();

        VBox newestProfileVBox = new VBox();
        //does nothing if there is no messages at all.
        for (Message message : receivedMessages) {
            if (!reportDB.checkIfReportExistsBetweenUsers(message.getReceiverID(), message.getSenderID())) {
                Profile senderProfile = profileDB.getProfileFromID(message.getSenderID());

                GridPane gridPane = new GridPane();

                ColumnConstraints col1 = new ColumnConstraints();
                col1.setPercentWidth(25);
                ColumnConstraints col2 = new ColumnConstraints();
                col2.setPercentWidth(60);
                ColumnConstraints col3 = new ColumnConstraints();
                col3.setPercentWidth(15);
                gridPane.getColumnConstraints().addAll(col1, col2, col3);

                gridPane.setHgap(80);
                gridPane.setVgap(5);
                gridPane.setPrefSize(Screen.getPrimary().getBounds().getWidth(), 40);
                gridPane.setStyle("-fx-background-color: #fff; -fx-border-color: #da291c; -fx-border-width: 1.5;");

                gridPane.setPadding(new Insets(20, 20, 20, 20));
                gridPane.add(new WrapTextLabel("Username: " + senderProfile.getUsername()), 0, 0);
                gridPane.add(new WrapTextLabel("Department: " + senderProfile.getDepartment()), 0, 1);
                gridPane.add(new WrapTextLabel("Job Titel: " + senderProfile.getJobTitle()), 1, 0);
                gridPane.add(new WrapTextLabel("Job Description: " + senderProfile.getJobDescription()), 1, 1);

                Button openChatButton = new Button("Chat  📝");
                openChatButton.setOnAction(event -> {
                    openChat(MainSceneController.getCurrentProfile(), senderProfile);
                });
                gridPane.add(openChatButton, 2, 0);


                Button reportButton = new Button("Report❗");
                reportButton.setStyle("-fx-text-fill: red;");
                reportButton.setOnAction(event -> {
                    reportUser(MainSceneController.getCurrentProfile().getProfileID(), senderProfile.getProfileID());
                });
                gridPane.add(reportButton, 2, 1);

                gridPane.autosize();

                newestProfileVBox.getChildren().add(gridPane);
            }
        }
        lastmessageBox.getChildren().addAll(newestProfileVBox);
    }

    /**
     * Displays the {@link Profile} Information of all conversations, in the "Answered chats" section
     * if a Report between the other user hasn't been reported
     * Adds a "Chat with" {@link Button}.
     * @param answeredMessages {@link Message Messages} where the last sender was the current user
     */
    public void showAnsweredMessageProfiles(List<Message> answeredMessages){
        List<GridPane> matchingProfilesHBoxes = new ArrayList<>();

        ProfileDBInterface profileDB = new ProfileDB();
        ReportDBInterface reportDB = new ReportDB();
        for (Message message : answeredMessages) {
            if(!reportDB.checkIfReportExistsBetweenUsers(message.getSenderID(), message.getReceiverID())){
            Profile receiverProfile = profileDB.getProfileFromID(message.getReceiverID());
            GridPane gridPane = new GridPane();

            ColumnConstraints col1 = new ColumnConstraints();
            col1.setPercentWidth(25);
            ColumnConstraints col2 = new ColumnConstraints();
            col2.setPercentWidth(60);
            ColumnConstraints col3 = new ColumnConstraints();
            col3.setPercentWidth(15);
            gridPane.getColumnConstraints().addAll(col1, col2, col3);


            gridPane.setHgap(80);
            gridPane.setVgap(5);
            gridPane.setPrefSize(Screen.getPrimary().getBounds().getWidth(), 40);
            gridPane.setStyle("-fx-background-color: #fff; -fx-border-color: #da291c; -fx-border-width: 1.5;");

            gridPane.setPadding(new Insets(20, 20, 20, 20));
            gridPane.add(new WrapTextLabel("Username: " + receiverProfile.getUsername()), 0, 0);
            gridPane.add(new WrapTextLabel("Department: " + receiverProfile.getDepartment()), 0, 1);
            gridPane.add(new WrapTextLabel("Job Titel: " + receiverProfile.getJobTitle()), 1, 0);
            gridPane.add(new WrapTextLabel("Job Description: " + receiverProfile.getJobDescription()), 1, 1);

            Button openChatButton = new Button("Chat  📝");
            openChatButton.setOnAction(event -> {openChat(MainSceneController.getCurrentProfile(), receiverProfile);});
            gridPane.add(openChatButton, 2, 0);


            Button reportButton = new Button("Report❗");
            reportButton.setStyle("-fx-text-fill: red;");
            reportButton.setOnAction(event -> {reportUser(MainSceneController.getCurrentProfile().getProfileID(),receiverProfile.getProfileID());});
            gridPane.add(reportButton, 2, 1);

            gridPane.autosize();
            matchingProfilesHBoxes.add(gridPane);
            }
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
        this.receiverProfile = receiverProfile;

        chatHeader.setText("Chat with "+receiverProfile.getUsername());
        //clear it.
        chatAreaVBox.getChildren().clear();

        chatAreaVBox.setPadding(new Insets(10));
        chatAreaVBox.setSpacing(10);

        //array of messages from database between the 2 profiles
        List<Message> AllmessagesBetweenProfiles = new ArrayList<>();
        MessageDBInterface messageDB = new MessageDB();
        AllmessagesBetweenProfiles = messageDB.getMessages(loggedInProfile.getProfileID(),receiverProfile.getProfileID());

        for (Message message : AllmessagesBetweenProfiles) {
            if (message.getSenderID() == loggedInProfile.getProfileID()) {
                HBox hbox = new HBox();
                Label loggedInLabel = new Label();

                loggedInLabel.setText(message.getText());
                loggedInLabel.setWrapText(true);
                loggedInLabel.getStyleClass().add("message-label-my");

                hbox.setAlignment(Pos.CENTER_RIGHT);
                hbox.setMaxWidth(chatAreaVBox.getWidth());

                hbox.getChildren().add(loggedInLabel);
                chatAreaVBox.getChildren().add(hbox);

            }
            else {
                HBox hbox = new HBox();
                Label otherProfileLabel = new Label();

                otherProfileLabel.setText(message.getText());
                otherProfileLabel.setWrapText(true);
                otherProfileLabel.getStyleClass().add("message-label-other");

                hbox.setAlignment(Pos.CENTER_LEFT);
                hbox.setMaxWidth(chatAreaVBox.getWidth());

                hbox.getChildren().add(otherProfileLabel);
                chatAreaVBox.getChildren().add(hbox);
            }
        }
        // Scroll to bottom
        chatHistory.setVvalue(1.0);
    }
    /**
     * Sends a new message in the current conversation.
     * Updates the database and refreshes the chat display.
     * @param loggedInProfileID The {@link Profile} of the message sender
     * @param receiverProfileID The {@link Profile} of the message recipient
     */
    private void sendMessage(Profile loggedInProfileID, Profile receiverProfileID)
    {
    if (messageInputField.getText().isBlank() || receiverProfileID==null)
        {
            return;
        }

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
     * Retrieves all {@link Profile}s the logged in user has previously chatted with.
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
     * Refreshes all {@link Message} tab components.
     * Reloads contact lists and reopens the current chat if one is active.
     */
    private void refreshMessageTab(){
        if (MainSceneController.getCurrentProfile() == null) {
            stopUpdating();
        }

        // Clear existing content
        lastmessageBox.getChildren().clear();
        oldChatBox.getChildren().clear();
        newContactBox.getChildren().clear();

        // Reload
        sortMessages();
        showAllAvailableChats(MatchState.BOTH_INTERESTED);

        //open chat again
        if (receiverProfile != null) {
            openChat(MainSceneController.getCurrentProfile(), receiverProfile);
        }
    }

    /**
     * sorts the {@link Message}s in two arraylist iSentLast and iRecievedLast
     */
    public void sortMessages() {
        Profile loggedInProfile = MainSceneController.getCurrentProfile();
        List<Profile> profilesWhereLoggedInProfileHasChattedWithBefore = getAllPossibleChats(loggedInProfile.getProfileID());
        MessageDBInterface messageDB = new MessageDB();

        List<Message> iSentLast = new ArrayList<>();
        List<Message> iReceivedLast = new ArrayList<>();

        for (Profile matchingProfile : profilesWhereLoggedInProfileHasChattedWithBefore) {
            List<Message> allMessages = messageDB.getMessages(loggedInProfile.getProfileID(), matchingProfile.getProfileID());
            Message lastMessage = allMessages.getLast();

            if (lastMessage != null && lastMessage.getSenderID() == loggedInProfile.getProfileID()) {
                iSentLast.add(lastMessage);
            }
            else {
                iReceivedLast.add(lastMessage);
            }
        }

        showAnsweredMessageProfiles(iSentLast);
        showReceivedMessageProfiles(iReceivedLast);
    }

    /**
     * Updates content of tab
     */
    @Override
    public void update() {
        refreshMessageTab();
    }

    /**
     * stops the gameloop
     */
    public static void stopUpdating(){
        updater.stop();
    }

    /**
     * User can {@link org.example.jobswap.Model.Report} other users if there has happened something inappropriate
     * shows an Alert to make sure the user wants to report
     * and deletes match
     * @param profileIDOfReporter the {@link Profile}ID of the reporter
     * @param profileIDOfReported the {@link Profile}ID of the reported
     */
    public void reportUser(int profileIDOfReporter, int profileIDOfReported){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        //new stage for the alert, to change the icon.
        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
        alertStage.getIcons().add(new Image(getClass().getResource("/org/example/jobswap/JobSwapIcon.png").toExternalForm()));
        alert.setTitle("Report a user");
        alert.setHeaderText("You are about to report a user, because a user has done something in appropriate");
        alert.setContentText("HR will contact you if you proceed" +
        "\nThe user will also be removed from your matches\n" +
                "Do you want to proceed? ");

        //Waits for user choice and saves result of choice
        Optional<ButtonType> result = alert.showAndWait();

        // checks if user choose ok
        if (result.isPresent() && result.get() == ButtonType.OK) {
            ReportDBInterface reportDB = new ReportDB();
            reportDB.createReport(profileIDOfReporter, profileIDOfReported);

            MatchDBInterface matchDB = new MatchDB();
            matchDB.deleteMatch(profileIDOfReporter, profileIDOfReported);

            Alert info = new Alert(Alert.AlertType.INFORMATION);
            info.setTitle("Report sent");
            info.setHeaderText(null);
            info.setContentText("The user has been reported and removed from your matches.");
            info.showAndWait();

        }

    }
}
