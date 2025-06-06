package org.example.jobswap.Controllers.UserTabs;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import org.example.jobswap.Controllers.MainSceneController;
import org.example.jobswap.Controllers.UpdatableTab;
import org.example.jobswap.Model.*;
import org.example.jobswap.Persistence.DepartmentDB;
import org.example.jobswap.Persistence.Interfaces.DepartmentDBInterface;
import org.example.jobswap.Persistence.Interfaces.MatchDBInterface;
import org.example.jobswap.Persistence.MatchDB;
import org.example.jobswap.Service.BorderedVBox;
import org.example.jobswap.Service.Header;
import org.example.jobswap.Service.WrapTextLabel;

import java.util.ArrayList;
import java.util.List;

/**
 * class which sets up the tab seek jobswap
 */
public class UserTabSeekJobSwap extends UpdatableTab {

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
        DepartmentDBInterface departmentDB = new DepartmentDB();

        for (Department department : departmentDB.getDepartments())
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

        String selectedDepartment = departmentChoiceBox.getSelectionModel()
                .getSelectedItem().substring(0, departmentChoiceBox.getSelectionModel().getSelectedItem().indexOf(","));

        MatchDBInterface matchDB = new MatchDB();

        List<Profile> matchingProfiles = matchDB.seekAllPossibleProfileMatches(MainSceneController.getCurrentProfile().getProfileID(),
                selectedDepartment);

        List<GridPane> matchingProfilesHBoxes = new ArrayList<>();

        for (Profile profile : matchingProfiles) {
            if (profile.getProfileID() == MainSceneController.getCurrentProfile().getProfileID())
            {
                continue;
            }

            GridPane gridPane = new GridPane();
            gridPane.setHgap(100);
            gridPane.setVgap(5);
            gridPane.setPrefSize(Screen.getPrimary().getBounds().getWidth(), 40);
            gridPane.setStyle("-fx-background-color: #fff; -fx-border-color: #da291c; -fx-border-width: 1.5;");

            ColumnConstraints col1 = new ColumnConstraints();
            col1.setPercentWidth(25);
            ColumnConstraints col2 = new ColumnConstraints();
            col2.setPercentWidth(50);
            ColumnConstraints col3 = new ColumnConstraints();
            col3.setPercentWidth(25);
            gridPane.getColumnConstraints().addAll(col1, col2, col3);

            gridPane.setPadding(new Insets(20, 20, 20, 20));
            gridPane.add(new WrapTextLabel("Username: " + profile.getUsername()), 0, 0);
            gridPane.add(new WrapTextLabel("Department: " + profile.getDepartment()), 0, 1);
            gridPane.add(new WrapTextLabel("Job Title: " + profile.getJobTitle()), 1, 0);
            gridPane.add(new WrapTextLabel("Job Description: " + profile.getJobDescription()), 1, 1);
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
    }
}
