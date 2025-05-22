package org.example.jobswap.Controllers.UserTabs;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import org.example.jobswap.Controllers.MainSceneController;
import org.example.jobswap.Model.Match;
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

    private List<Match> matches;

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

        MatchDBInterface matchDB = new MatchDB();
        matches=matchDB.getProfileMatches(MainSceneController.getCurrentProfile().getProfileID());
    }

    private void showAcceptedMatches()
    {



    }

    private void showRequestedMatches()
    {

    }

    /**
     * Displays application matches in Boxes.
     */
    private void showMatchApplications()
    {

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
