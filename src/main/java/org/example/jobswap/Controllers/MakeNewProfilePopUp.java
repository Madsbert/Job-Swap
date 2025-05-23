package org.example.jobswap.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.example.jobswap.Model.Profile;
import org.example.jobswap.Persistence.DepartmentDB;
import org.example.jobswap.Persistence.Interfaces.LoginDBInterface;
import org.example.jobswap.Persistence.Interfaces.ProfileDBInterface;
import org.example.jobswap.Persistence.JobCategoryDB;
import org.example.jobswap.Persistence.LoginDB;
import org.example.jobswap.Persistence.ProfileDB;
import org.example.jobswap.Service.PasswordEncrypter;
import org.example.jobswap.Service.SceneService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * class which sets createNewProfileScene fxml and handles some logic for creating a new profile
 */
public class MakeNewProfilePopUp {
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

    public int profileID;
    public String fullName;
    public String username;
    public String password;
    public String department;
    public String jobCategory;
    public String description;
    public String jobTitle;
    public boolean activelySeeking;

    public void initialize(){
        setupDepartmentChoiceBox();
        setupJobCategoryChoiceBox();
    }

    public void createProfileInDatabase(ActionEvent actionEvent) throws IOException {
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

        System.out.println(newProfile);
        newProfile.toString();

        //create new profile in database, if it was created, create a login with the ID and password
        isProfileCreated = profileDB.createNewProfile(newProfile);

        if (isProfileCreated) {
            LoginDBInterface loginDB = new LoginDB();
            loginDB.addLoginToDataBase(profileID,password);
            SceneService.shiftScene(actionEvent,"Login Screen","/org/example/jobswap/Login.fxml");
        }

    }

    public void setupDepartmentChoiceBox(){
        ArrayList<String> departments = new ArrayList<>();

        for (org.example.jobswap.Model.Department department : DepartmentDB.getDepartments())
        {
            departments.add(department.getDepartmentName());
        }
        departmentChoiceBox.getItems().addAll(departments);
    }
    public void setupJobCategoryChoiceBox(){
        List<String> jobCategories = JobCategoryDB.getCategories();
        jobCategoryChoiceBox.getItems().addAll(jobCategories);
    }

    public void Cancel(ActionEvent actionEvent) throws IOException {
        SceneService.shiftScene(actionEvent,"Login Screen","/org/example/jobswap/Login.fxml");
    }
}
