package org.example.jobswap.Controllers.UserTabs;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import org.example.jobswap.Controllers.MainSceneController;
import org.example.jobswap.Controllers.UpdatableTab;
import org.example.jobswap.Model.Department;
import org.example.jobswap.Model.Profile;
import org.example.jobswap.Persistence.DepartmentDB;
import org.example.jobswap.Persistence.Interfaces.DepartmentDBInterface;
import org.example.jobswap.Persistence.Interfaces.JobCategoryDBInterface;
import org.example.jobswap.Persistence.Interfaces.ProfileDBInterface;
import org.example.jobswap.Persistence.JobCategoryDB;
import org.example.jobswap.Persistence.ProfileDB;
import org.example.jobswap.Service.BorderedVBox;
import org.example.jobswap.Service.Header;

import java.util.ArrayList;
import java.util.List;

/**
 * class which sets up the tab Profile
 */
public class UserTabProfile extends UpdatableTab {

    private ChoiceBox departmentChoiceBox;
    private ChoiceBox jobCategoryChoiceBox;
    private TextField jobTitleField;
    private TextField jobDescriptionField;
    private TextField usernameField;

    private VBox optionsBox;
    private VBox profileDetailsBox;

    private CheckBox editModeCheckBox;
    private CheckBox activeCheckBox;

    private final Profile changedProfile;

    /**
     * {@link UserTabProfile} constructor
     */
    public UserTabProfile() {
        super("Profile");

        changedProfile = new Profile(MainSceneController.getCurrentProfile());

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        scrollPane.setPadding(new Insets(25, 25, 25, 25));

        VBox primaryVBox = new BorderedVBox();
        scrollPane.setContent(primaryVBox);

        primaryVBox.getChildren().add(new Header("Personal details"));

        HBox sections = new HBox();
        primaryVBox.getChildren().add(sections);

        setupProfileDetails();
        setupOptions();

        sections.getChildren().add(profileDetailsBox);
        sections.getChildren().add(optionsBox);

        this.setContent(scrollPane);
    }

    /**
     * Sets up all the options in the right side area.
     * Options are Edit mode, Actively searching and Save Changes.
     */
    private void setupOptions() {
        optionsBox = new VBox();
        optionsBox.setPrefWidth(Screen.getPrimary().getBounds().getWidth()/2);

        editModeCheckBox = new CheckBox("Edit Mode");
        editModeCheckBox.setSelected(false);
        editModeCheckBox.setOnAction(event -> updateEditableState());
        optionsBox.getChildren().add(editModeCheckBox);

        activeCheckBox = new CheckBox("Actively Searching");
        activeCheckBox.setSelected(MainSceneController.getCurrentProfile().isActivelySeeking());

        activeCheckBox.setOnAction(event -> MainSceneController.getCurrentProfile().setActivelySeeking(activeCheckBox.isSelected()));
        optionsBox.getChildren().add(activeCheckBox);

        Button applyButton = getApplyButton();
        optionsBox.getChildren().add(applyButton);


        updateEditableState();
    }

    /**
     * Creates an Apply/save changes {@link Button}.
     * @return Save Changes {@link Button}.
     */
    private Button getApplyButton() {
        Button applyButton = new Button("Save Changes");
        applyButton.setOnAction((event) -> {
            changedProfile.setDepartment(departmentChoiceBox.getSelectionModel().getSelectedItem().toString());
            changedProfile.setJobCategory(jobCategoryChoiceBox.getSelectionModel().getSelectedItem().toString());
            changedProfile.setJobTitle(jobTitleField.getText());
            changedProfile.setDescription(jobDescriptionField.getText());
            changedProfile.setActivelySeeking(activeCheckBox.isSelected());
            changedProfile.setUsername(usernameField.getText());

            System.out.println("Profile Diff: " + !changedProfile.equals(MainSceneController.getCurrentProfile()));
            if (!changedProfile.equals(MainSceneController.getCurrentProfile())) {
                MainSceneController.setCurrentProfile(new Profile(changedProfile));
                ProfileDBInterface profileDB = new ProfileDB();
                profileDB.updateProfile(changedProfile);
            }
        });
        return applyButton;
    }

    /**
     * Sets up the {@link Profile} details area. Left side of page.
     * This calls the setup methods for all the details in order.
     */
    private void setupProfileDetails() {
        profileDetailsBox = new VBox();
        profileDetailsBox.setPrefWidth(Screen.getPrimary().getBounds().getWidth()/2);

        setupNameBox();
        setupIDBox();
        setupDepartmentBox();
        setupJobCategoryBox();
        setupJobTitleBox();
        setupJobDescription();
        setupUsernameBox();
    }

    /**
     * Sets up name {@link Label} and the {@link Label} containing the {@link Profile} name.
     */
    private void setupNameBox()
    {
        HBox nameBox = new HBox();

        Label tLabel = new Label("Name:");
        tLabel.setPrefWidth(140);
        tLabel.setStyle("-fx-padding: 0 0 0 10;");

        nameBox.getChildren().add(tLabel);
        nameBox.getChildren().add(new Label(MainSceneController.getCurrentProfile().getName()));
        profileDetailsBox.getChildren().add(nameBox);
    }

    /**
     * Sets up id {@link Label} and the {@link Label} containing the profileID.
     */
    private void setupIDBox()
    {
        HBox idBox = new HBox();

        Label t2Label = new Label("ID:");
        t2Label.setPrefWidth(140);
        t2Label.setStyle("-fx-padding: 0 0 0 10;");

        idBox.getChildren().add(t2Label);
        idBox.getChildren().add(new Label("" + MainSceneController.getCurrentProfile().getProfileID()));
        profileDetailsBox.getChildren().add(idBox);
    }

    /**
     * Sets up {@link Department} {@link Label} and a {@link ChoiceBox} containing all the Departments, which it gets from the Database.
     * It also sets the {@Profile}s current {@link Department} to be selected in the {@link ChoiceBox} if it is valid.
     */
    private void setupDepartmentBox()
    {
        HBox departmentBox = new HBox();
        ArrayList<String> departments = new ArrayList<>();
        DepartmentDBInterface departmentDB = new DepartmentDB();

        for (Department department : departmentDB.getDepartments())
        {
            departments.add(department.getDepartmentName());
        }

        departmentChoiceBox = new ChoiceBox<String>();
        departmentChoiceBox.getItems().addAll(departments);

        for (int i = 0; i < departments.size(); i++) {
            if (MainSceneController.getCurrentProfile().getDepartment().equals(departments.get(i))) {
                departmentChoiceBox.getSelectionModel().select(i);
            }
        }

        Label t3Label = new Label("Departments:");
        t3Label.setPrefWidth(140);
        t3Label.setStyle("-fx-padding: 0 0 0 10;");

        departmentBox.getChildren().add(t3Label);
        departmentBox.getChildren().add(departmentChoiceBox);
        profileDetailsBox.getChildren().add(departmentBox);
    }

    /**
     * Sets up Job category {@link Label} and a {@link ChoiceBox} containing all the Job categories, which it gets from the Database.
     * It also sets the {@link Profile}s current Job category to be selected in the {@link ChoiceBox} if it is valid.
     */
    private void setupJobCategoryBox()
    {
        HBox jobCategoryBox = new HBox();
        JobCategoryDBInterface jobCategoryDB = new JobCategoryDB();

        List<String> jobCategories = jobCategoryDB.getCategories();

        jobCategoryChoiceBox = new ChoiceBox<String>();
        jobCategoryChoiceBox.getItems().addAll(jobCategories);
        for (int i = 0; i < jobCategories.size(); i++) {
            if (MainSceneController.getCurrentProfile().getJobCategory().equals(jobCategories.get(i))) {
                jobCategoryChoiceBox.getSelectionModel().select(i);
            }
        }

        Label t4Label = new Label("Job Category:");
        t4Label.setPrefWidth(140);
        t4Label.setStyle("-fx-padding: 0 0 0 10;");

        jobCategoryBox.getChildren().add(t4Label);
        jobCategoryBox.getChildren().add(jobCategoryChoiceBox);
        profileDetailsBox.getChildren().add(jobCategoryBox);
    }

    /**
     * Sets up JobTitle {@link Label} and a TextField containing the current {@link Profile} job title.
     */
    private void setupJobTitleBox()
    {
        HBox JobTitleBox = new HBox();

        jobTitleField = new TextField(MainSceneController.getCurrentProfile().getJobTitle());

        Label t5Label = new Label("Job Title:");
        t5Label.setPrefWidth(140);
        t5Label.setStyle("-fx-padding: 0 0 0 10;");

        JobTitleBox.getChildren().add(t5Label);
        JobTitleBox.getChildren().add(jobTitleField);
        profileDetailsBox.getChildren().add(JobTitleBox);
    }

    /**
     * Sets up JobDescription {@link Label} and a {@link TextField} containing the current {@link Profile} job description.
     */
    private void setupJobDescription()
    {
        HBox JobDescriptionBox = new HBox();
        Label t6Label = new Label("Job Description:");

        jobDescriptionField = new TextField(MainSceneController.getCurrentProfile().getJobDescription());

        t6Label.setPrefWidth(140);
        t6Label.setStyle("-fx-padding: 0 0 0 10;");
        JobDescriptionBox.getChildren().add(t6Label);
        JobDescriptionBox.getChildren().add(jobDescriptionField);
        profileDetailsBox.getChildren().add(JobDescriptionBox);
    }
    /**
     * Sets up JobTitle {@link Label} and a {@link TextField} containing the current {@link Profile} job title.
     */
    private void setupUsernameBox()
    {
        HBox usernameBox = new HBox();

        usernameField = new TextField(MainSceneController.getCurrentProfile().getUsername());

        Label t7Label = new Label("Username:");
        t7Label.setPrefWidth(140);
        t7Label.setStyle("-fx-padding: 0 0 0 10;");
        usernameBox.getChildren().add(t7Label);
        usernameBox.getChildren().add(usernameField);
        profileDetailsBox.getChildren().add(usernameBox);
    }

    /**
     * Updates the logic behind the edit mode.
     * disables editable fields if edit mode is off, does the opposite if it is enabled.
     */
    private void updateEditableState()
    {
        if (editModeCheckBox.isSelected()) {
            departmentChoiceBox.setDisable(false);
            jobCategoryChoiceBox.setDisable(false);
            jobTitleField.setDisable(false);
            jobDescriptionField.setDisable(false);
            usernameField.setDisable(false);
        }
        else
        {
            departmentChoiceBox.setDisable(true);
            jobCategoryChoiceBox.setDisable(true);
            jobTitleField.setDisable(true);
            jobDescriptionField.setDisable(true);
            usernameField.setDisable(true);
        }
    }
}
