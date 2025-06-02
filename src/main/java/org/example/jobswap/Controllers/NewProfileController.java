package org.example.jobswap.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.jobswap.Model.Profile;
import org.example.jobswap.Persistence.DepartmentDB;
import org.example.jobswap.Persistence.Interfaces.DepartmentDBInterface;
import org.example.jobswap.Persistence.Interfaces.JobCategoryDBInterface;
import org.example.jobswap.Persistence.Interfaces.LoginDBInterface;
import org.example.jobswap.Persistence.Interfaces.ProfileDBInterface;
import org.example.jobswap.Persistence.JobCategoryDB;
import org.example.jobswap.Persistence.LoginDB;
import org.example.jobswap.Persistence.ProfileDB;
import org.example.jobswap.Service.PasswordEncrypter;
import org.example.jobswap.Service.SceneService;

import java.util.ArrayList;
import java.util.List;

/**
 * class which sets createNewProfileScene fxml and handles some logic for creating a new profile
 */
public class NewProfileController {
    @FXML
    private ChoiceBox departmentChoiceBox;
    @FXML
    private ChoiceBox jobCategoryChoiceBox;
    @FXML
    private TextField profileIdField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextArea jobDescriptionField;
    @FXML
    private TextField jobTitleField;
    @FXML
    private CheckBox activelySeekingCheckbox;
    @FXML
    private Button createButton;

    public int profileID;
    public String fullName;
    public String username;
    public String password;
    public String department;
    public String jobCategory;
    public String description;
    public String jobTitle;
    public boolean activelySeeking;

    //initialize and add listeners to the Text Fields.
    public void initialize(){
        setupDepartmentChoiceBox();
        setupJobCategoryChoiceBox();
        createButton.setDisable(true);

        // Add listeners to all input fields
        profileIdField.textProperty().addListener((obs, oldVal, newVal) -> checkButtonState());
        nameField.textProperty().addListener((obs, oldVal, newVal) -> checkButtonState());
        usernameField.textProperty().addListener((obs, oldVal, newVal) -> checkButtonState());
        passwordField.textProperty().addListener((obs, oldVal, newVal) -> checkButtonState());
        jobDescriptionField.textProperty().addListener((obs, oldVal, newVal) -> checkButtonState());
        jobTitleField.textProperty().addListener((obs, oldVal, newVal) -> checkButtonState());



    }

    /**
     * This method it triggered by the createButton, it checks if everything is valid.
     * If it is valid, return to loginScene, if not, an alert pops up.
     * @param actionEvent a click of a {@link Button}
     */
    public void createProfileInDatabase(ActionEvent actionEvent) {
        //set variables
        profileID = Integer.parseInt(profileIdField.getText());
        fullName = nameField.getText();
        username = usernameField.getText();
        department = departmentChoiceBox.getValue().toString();
        jobCategory = jobCategoryChoiceBox.getSelectionModel().getSelectedItem().toString();
        description = jobDescriptionField.getText();
        jobTitle = jobTitleField.getText();
        activelySeeking = activelySeekingCheckbox.isSelected();

        password = PasswordEncrypter.encrypt(passwordField.getText());

        //create new profile object
        Profile newProfile = new Profile(profileID,fullName,username, department, jobTitle, description, jobCategory,activelySeeking);
        ProfileDBInterface profileDB = new ProfileDB();
        boolean isProfileCreated;

        //create new profile in database, if it was created, create a login with the ID and password
        isProfileCreated = profileDB.createNewProfile(newProfile);

        try {
            if (isProfileCreated) {
                LoginDBInterface loginDB = new LoginDB();
                loginDB.addLoginToDataBase(profileID, password);
                SceneService.shiftScene(actionEvent, "Login Screen", "/org/example/jobswap/Login.fxml");
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ID is already in use");
                alert.setHeaderText("You have entered a wrong employee ID, this one is already in use");
                alert.setContentText("Please try again");
                alert.showAndWait();//This shows the alert
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("something went wrong in CreateProfileInDatabase" + e.getMessage());
        }

    }

    /**
     * Setups the Department ChoiceBox, and auto selects the first option.
     */
    public void setupDepartmentChoiceBox(){
        ArrayList<String> departments = new ArrayList<>();
        DepartmentDBInterface departmentDB= new DepartmentDB();

        for (org.example.jobswap.Model.Department department : departmentDB.getDepartments())
        {
            departments.add(department.getDepartmentName());
        }
        departmentChoiceBox.getItems().addAll(departments);
        departmentChoiceBox.getSelectionModel().selectFirst();
    }

    /**
     * Setups the JobCategory ChoiceBox, and auto selects the first option.
     */
    public void setupJobCategoryChoiceBox(){
        JobCategoryDBInterface jobCategoryDB= new JobCategoryDB();

        List<String> jobCategories = jobCategoryDB.getCategories();
        jobCategoryChoiceBox.getItems().addAll(jobCategories);
        jobCategoryChoiceBox.getSelectionModel().selectFirst();
    }

    /**
     * The method that is checked by the listener, and makes the create-{@link Button} not disable if everything is valid.
     */
    public void checkButtonState(){
            boolean validID = validateIDField();
            boolean validName = !nameField.getText().isBlank();
            boolean validPassword = !passwordField.getText().isBlank();
            boolean validUsername = !usernameField.getText().isBlank();
            boolean validDescription = true;
            boolean validJobTitle = true;
            boolean validActivelySeeking = true;

            createButton.setDisable(!(validID && validName && validPassword && validUsername && validDescription &&
                    validJobTitle && validActivelySeeking));
    }

    /**
     * Checks if profileIDField is valid. If not, changes the Text Field Border.
     * @return a boolean
     */
    private boolean validateIDField() {
        if (!profileIdField.getText().isBlank()) {
            try {
                Integer.parseInt(profileIdField.getText());
                profileIdField.setStyle(""); // Clear error styling if any
                return true;
            } catch (NumberFormatException e) {
                profileIdField.setStyle("-fx-border-color: red;"); // Show error if text
                return false;
            }
        }
        profileIdField.setStyle("-fx-border-color: red;"); // Show error if blank field
        return false;
    }

    /**
     * is called from the Cancel {@link Button}, saves nothing, and returns to the loginScene.
     * @param actionEvent a {@link Button} click
     */
    public void cancel(ActionEvent actionEvent){
        try {
            SceneService.shiftScene(actionEvent,"Login Screen","/org/example/jobswap/Login.fxml");
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("something went wrong in Cancel" + e.getMessage());
        }

    }
}
