package org.example.jobswap.Controllers.UserTabs;

import javafx.geometry.Insets;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.example.jobswap.Controllers.MainSceneController;
import org.example.jobswap.Model.AccessLevel;
import org.example.jobswap.Model.Department;
import org.example.jobswap.Model.Profile;
import org.example.jobswap.Persistence.DepartmentDB;
import org.example.jobswap.Persistence.MatchDB;
import org.example.jobswap.Service.BorderedVBox;
import org.example.jobswap.Service.Header;

import java.util.ArrayList;
import java.util.List;

/**
 * class which sets up the tab seek jobswap
 */
public class UserTabSeekJobSwap extends javafx.scene.control.Tab {

    private static VBox departmentBox;
    private static ChoiceBox<String> departmentChoiceBox;

    private static VBox availableSwapsBox;
    private static VBox swapsBox;

    public UserTabSeekJobSwap()
    {
        super("Seek JobSwap");

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        scrollPane.setPadding(new Insets(25, 25, 25, 25));

        VBox vbox = new VBox();
        scrollPane.setContent(vbox);

        departmentBox = new BorderedVBox();
        departmentBox.getChildren().add(new Header("Choose Department"));
        departmentBox.setPrefHeight(120);
        vbox.getChildren().add(departmentBox);

        availableSwapsBox = new BorderedVBox();
        availableSwapsBox.getChildren().add(new Header("Potential Matches"));

        swapsBox = new VBox();
        availableSwapsBox.getChildren().add(swapsBox);

        vbox.getChildren().add(availableSwapsBox);



        this.setContent(scrollPane);

        showJobswapOptions();
    }

    private void showJobswapOptions()
    {
        Label label = new Label("Departments:");
        label.setPrefWidth(140);
        label.setStyle("-fx-padding: 0 0 0 10;");
        departmentBox.getChildren().add(label);


        ArrayList<String> departments = new ArrayList<>();
        for (Department department : DepartmentDB.getDepartments())
        {
            if (MainSceneController.getCurrentProfile().getDepartment().equals(department.getDepartmentName()))
            {
                continue;
            }
            departments.add(department.getDepartmentName());
        }

        departmentChoiceBox = new ChoiceBox<>();
        departmentChoiceBox.getItems().addAll(departments);
        departmentChoiceBox.setOnAction(event -> {updateJobList();});
        departmentBox.getChildren().add(departmentChoiceBox);

        updateJobList();
    }

    private void updateJobList()
    {
        if (departmentChoiceBox.getSelectionModel().isEmpty() && departmentChoiceBox.getSelectionModel().getSelectedItem() == null)
        {
            return;
        }

        swapsBox.getChildren().clear();

        List<Profile> matchingProfiles = MatchDB.seekAllPossibleProfileMatches(MainSceneController.getCurrentProfile().getProfileID(),
                departmentChoiceBox.getSelectionModel().getSelectedItem());

        List<HBox> matchingProfilesHBoxes = new ArrayList<>();

        for (Profile profile : matchingProfiles) {
            HBox hbox = new HBox();
            hbox.setSpacing(10);
            hbox.setPadding(new Insets(25, 25, 25, 25));
            hbox.getChildren().add(new Label("Username: " + profile.getUsername()));
            hbox.getChildren().add(new Label("Department: " + profile.getDepartment()));
            hbox.getChildren().add(new Label("Job Titel: " + profile.getJobTitle()));
            hbox.getChildren().add(new Label("Job Description: " + profile.getJobDescription()));

            matchingProfilesHBoxes.add(hbox);
        }

        swapsBox.getChildren().addAll(matchingProfilesHBoxes);
    }

    private void applyForJobswap()
    {

    }
}
