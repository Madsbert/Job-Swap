package org.example.jobswap.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.example.jobswap.Model.Department;
import org.example.jobswap.Model.Profile;
import org.example.jobswap.Persistence.DepartmentDB;
import org.example.jobswap.Persistence.Interfaces.LoginDBInterface;
import org.example.jobswap.Persistence.Interfaces.ProfileDBInterface;
import org.example.jobswap.Persistence.JobCategoryDB;
import org.example.jobswap.Persistence.LoginDB;
import org.example.jobswap.Persistence.ProfileDB;
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
    public String Department;
    public String JobCategory;
    public String Description;
    public boolean ActivelySeeking;

    public void initialize(){
        setupDepartmentChoiceBox();
        setupJobCategoryChoiceBox();
    }

    public void createProfileInDatabase(ActionEvent actionEvent) throws IOException {
        //set variables
        profileID = Integer.parseInt(profileIdField.getText());
        fullName = nameField.getText();
        username = usernameField.getText();
        password = passwordField.getText();
        Department = jobTitleField.getText();
        Description = jobDescriptionField.getText();
        ActivelySeeking = activelySeekingCheckbox.isSelected();

        //create new profile object
        Profile newProfile = new Profile(profileID,fullName,username,password,Department,JobCategory,Description,ActivelySeeking);
        ProfileDBInterface profileDB = new ProfileDB();
        boolean isProfileCreated;
        //create new profile in database, if it was created, create a login with the ID and password
        isProfileCreated = profileDB.createNewProfile(newProfile);

        if (isProfileCreated) {
            LoginDBInterface loginDB = new LoginDB();
            loginDB.addLoginToDataBase(profileID,password);
        }
        SceneService.shiftScene(actionEvent,"Login Screen","/org/example/jobswap/Login.fxml");
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
    public String encryptedPassword(String password){
        String newPassword = String.valueOf(password.hashCode());
        return newPassword;
    }
    public void Cancel(ActionEvent actionEvent) throws IOException {
        SceneService.shiftScene(actionEvent,"Login Screen","/org/example/jobswap/Login.fxml");
    }
}
