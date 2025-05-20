package org.example.jobswap.Controllers.UserTabs;

import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import org.example.jobswap.Controllers.MainSceneController;
import org.example.jobswap.Model.Department;
import org.example.jobswap.Model.Profile;
import org.example.jobswap.Service.BorderedVBox;
import org.example.jobswap.Service.Header;

import java.util.ArrayList;
import java.util.List;

public class UserTabProfile extends javafx.scene.control.Tab {

    private boolean editMode = false;
    private ChoiceBox departmentLabel;
    private TextField jobCategoryLabel;

    public UserTabProfile() {
        super("Profile");

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        scrollPane.setPadding(new Insets(25, 25, 25, 25));

        VBox vbox = new VBox();
        scrollPane.setContent(vbox);

        VBox matchVBox = new BorderedVBox();
        matchVBox.getChildren().add(new Header("Personal details"));
        vbox.getChildren().add(matchVBox);

        HBox nameBox = new HBox();

        Label tLabel = new Label("Name:");
        tLabel.setPrefWidth(140);
        tLabel.setStyle("-fx-padding: 0 20 0 10;");

        nameBox.getChildren().add(tLabel);
        nameBox.getChildren().add(new Label(MainSceneController.getCurrentProfile().getName()));
        matchVBox.getChildren().add(nameBox);

        HBox idBox = new HBox();

        Label t2Label = new Label("ID:");
        t2Label.setPrefWidth(140);
        t2Label.setStyle("-fx-padding: 0 20 0 10;");

        idBox.getChildren().add(t2Label);
        idBox.getChildren().add(new Label("" + MainSceneController.getCurrentProfile().getProfileID()));
        matchVBox.getChildren().add(idBox);

        HBox departmentBox = new HBox();
        ArrayList<String> departments = new ArrayList<>();
        departments.add("Sønderborg");
        departmentLabel = new ChoiceBox();
        departmentLabel.getItems().addAll(departments);

        Label t3Label = new Label("Departments:");
        t3Label.setPrefWidth(140);
        t3Label.setStyle("-fx-padding: 0 20 0 10;");

        departmentBox.getChildren().add(t3Label);
        departmentBox.getChildren().add(departmentLabel);
        matchVBox.getChildren().add(departmentBox);

        this.setContent(scrollPane);
    }

    private void getProfileInformation()
    {

    }

    private void editProfileInformation()
    {

    }

    private void setSeekingJobswap(boolean status)
    {

    }

    private void saveChanges()
    {

    }
}
