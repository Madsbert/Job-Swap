package org.example.jobswap.Controllers.UserTabs;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import org.example.jobswap.Controllers.MainSceneController;
import org.example.jobswap.Model.MatchState;
import org.example.jobswap.Model.Message;
import org.example.jobswap.Model.Profile;
import org.example.jobswap.Persistence.Interfaces.MessageDBInterface;
import org.example.jobswap.Persistence.MatchDB;
import org.example.jobswap.Persistence.MessageDB;
import org.example.jobswap.Service.BorderedVBox;
import org.example.jobswap.Service.Header;

import java.util.ArrayList;
import java.util.List;

/**
 * class which sets up the tab Messages
 */
public class UserTabMessages extends javafx.scene.control.Tab {
    private static ScrollPane scrollPane;
    private static VBox newContactBox;
    private static VBox oldChatBox;
    private static VBox newChatBox;
    private static VBox newChatVBox;
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

        newChatVBox = new BorderedVBox();
        newChatVBox.getChildren().add(new Header("New Chats"));
        vbox.getChildren().add(newChatVBox);
        newChatBox = new VBox();
        newChatVBox.getChildren().add(newChatBox);

        oldChatVBow = new BorderedVBox();
        oldChatVBow.getChildren().add(new Header("Old Chats"));
        vbox.getChildren().add(oldChatVBow);
        oldChatBox = new VBox();
        oldChatVBow.getChildren().add(oldChatBox);

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
        MessageDBInterface messageDB = new MessageDB();
        List<Profile> matchingProfiles = messageDB.getAllPossibleChatsBasedOnState(MainSceneController.getCurrentProfile().getProfileID(), state);

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
    public void openChat(int profileIDLoggedIn,int ProfileIDReceiver){

    }

    private void writeNewMessage(Message message)
    {

    }

    private void showHelpInformation()
    {

    }
}
