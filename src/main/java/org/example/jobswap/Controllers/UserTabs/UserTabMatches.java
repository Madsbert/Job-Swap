package org.example.jobswap.Controllers.UserTabs;

import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import org.example.jobswap.Service.BorderedVBox;
import org.example.jobswap.Service.Header;

/**
 * class which sets up the tab Matches
 */
public class UserTabMatches extends javafx.scene.control.Tab {
    public UserTabMatches() {
        super("Matches");

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        scrollPane.setPadding(new Insets(25, 25, 25, 25));

        VBox vbox = new VBox();
        scrollPane.setContent(vbox);

        VBox matchVBox = new BorderedVBox();
        matchVBox.getChildren().add(new Header("Matches"));
        vbox.getChildren().add(matchVBox);

        VBox requestVBox = new BorderedVBox();
        requestVBox.getChildren().add(new Header("Request"));
        vbox.getChildren().add(requestVBox);

        VBox applicationsVBox = new BorderedVBox();
        applicationsVBox.getChildren().add(new Header("Applications"));
        vbox.getChildren().add(applicationsVBox);

        VBox bothVBox = new BorderedVBox();
        bothVBox.getChildren().add(new Header("Both Interested"));
        vbox.getChildren().add(bothVBox);

        this.setContent(scrollPane);
    }

    private void showAcceptedMatches()
    {

    }

    private void showRequestedMatches()
    {

    }

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
