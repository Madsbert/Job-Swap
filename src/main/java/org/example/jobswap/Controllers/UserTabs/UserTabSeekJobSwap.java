package org.example.jobswap.Controllers.UserTabs;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import org.example.jobswap.Controllers.MainSceneController;
import org.example.jobswap.Model.*;
import org.example.jobswap.Persistence.DepartmentDB;
import org.example.jobswap.Persistence.Interfaces.MatchDBInterface;
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

    /**
     * Adds the information in th departmentBox so the user can see possible jobswaps
     */
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
            departments.add(department.getDepartmentName() + ", " + department.getCity());
        }

        departmentChoiceBox = new ChoiceBox<>();
        departmentChoiceBox.getItems().addAll(departments);
        departmentChoiceBox.setOnAction(event -> {updateJobList();});
        departmentBox.getChildren().add(departmentChoiceBox);

        updateJobList();
    }

    /**
     * Gets all {@link Profile}s that matches the criteria {@link Department} and jobcategory
     */
    private void updateJobList()
    {
        if (departmentChoiceBox.getSelectionModel().isEmpty() && departmentChoiceBox.getSelectionModel().getSelectedItem() == null)
        {
            return;
        }

        swapsBox.getChildren().clear();

        List<Profile> matchingProfiles = MatchDB.seekAllPossibleProfileMatches(MainSceneController.getCurrentProfile().getProfileID(),
                departmentChoiceBox.getSelectionModel().getSelectedItem());

        List<GridPane> matchingProfilesHBoxes = new ArrayList<>();

        for (Profile profile : matchingProfiles) {
            GridPane gridPane = new GridPane();
            gridPane.setHgap(150);
            gridPane.setVgap(5);
            gridPane.setPrefSize(Screen.getPrimary().getBounds().getWidth(), 40);
            gridPane.setStyle("-fx-background-color: #fff; -fx-border-color: #da291c; -fx-border-width: 1.5;");

            gridPane.setPadding(new Insets(25, 25, 25, 25));
            gridPane.add(new Label("Username: " + profile.getUsername()), 0, 0);
            gridPane.add(new Label("Department: " + profile.getDepartment()), 0, 1);
            gridPane.add(new Label("Job Titel: " + profile.getJobTitle()), 1, 0);
            gridPane.add(new Label("Job Description: " + profile.getJobDescription()), 1, 1);
            Button applyButton = new Button("Apply for Swap");
            applyButton.setOnAction(event -> {applyForJobswap(profile);});
            gridPane.add(applyButton, 2, 1);

            gridPane.autosize();
            matchingProfilesHBoxes.add(gridPane);
        }

        swapsBox.getChildren().addAll(matchingProfilesHBoxes);
    }

    /**
     * apply for the jobswap and refreshes display and database
     * @param profileToApplyTo the {@link Profile} the user wants to swap with
     */
    private void applyForJobswap(Profile profileToApplyTo)
    {
        MatchDBInterface db = new MatchDB();
        Match newMatch = new Match(MatchState.APPLICATION, MainSceneController.getCurrentProfile(), profileToApplyTo);

        db.createMatch(newMatch);
        updateJobList();
        UserTabMatches.getInstance().refreshMatchDisplay();
    }
}
