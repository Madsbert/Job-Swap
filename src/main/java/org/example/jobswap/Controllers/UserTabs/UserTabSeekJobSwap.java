package org.example.jobswap.Controllers.UserTabs;

import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import org.example.jobswap.Model.AccessLevel;
import org.example.jobswap.Service.BorderedVBox;
import org.example.jobswap.Service.Header;

public class UserTabSeekJobSwap extends javafx.scene.control.Tab {

    public UserTabSeekJobSwap()
    {
        super("Seek JobSwap");

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        scrollPane.setPadding(new Insets(25, 25, 25, 25));

        VBox vbox = new VBox();
        scrollPane.setContent(vbox);

        VBox matchVBox = new BorderedVBox();
        matchVBox.getChildren().add(new Header("Choose Department"));
        vbox.getChildren().add(matchVBox);

        VBox applicationsVBox = new BorderedVBox();
        applicationsVBox.getChildren().add(new Header("Potential Matches"));
        vbox.getChildren().add(applicationsVBox);

        this.setContent(scrollPane);
    }

    private void showJobswapOptions()
    {

    }

    private void setSelectedDepartment(int department)
    {

    }

    private void setSelectedJobcategory(int jobCategory)
    {

    }

    private void applyForJobswap()
    {

    }
}
