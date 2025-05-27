package org.example.jobswap.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.jobswap.Model.Profile;
import org.example.jobswap.Persistence.Interfaces.LoginDBInterface;
import org.example.jobswap.Persistence.Interfaces.ProfileDBInterface;
import org.example.jobswap.Persistence.LoginDB;
import org.example.jobswap.Persistence.ProfileDB;
import org.example.jobswap.Service.PasswordEncrypter;
import org.example.jobswap.Service.SceneService;


/**
 * class to control the Login.fxml and has logic for login
 */
public class LoginController {

    @FXML
    private TextField employeeIDFields;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    private int wrongIDCounter = 1;
    private int oldID;


    @FXML
    public void initialize() {
        // Allows "Enter" key to log in
        loginButton.setDefaultButton(true);
    }

    /**
     * Method that checks if the password and employeeID matches in database
     * @return a {@code boolean} if the credentials match or not
     */
    private boolean checkCredentials() {
        try {
            //check if fields aren't empty
            if (!employeeIDFields.getText().isBlank() && !passwordField.getText().isBlank()) {
                int id = Integer.parseInt(employeeIDFields.getText());
                String pass = PasswordEncrypter.encrypt(passwordField.getText());

                LoginDBInterface db = new LoginDB();
                if (db.checkIsLocked(id)){
                    return db.checkCredentials(id, pass);
                }
            }
            return false; // Returns false if a field is blank
        } catch (NumberFormatException e) {
            System.out.println("Invalid employee ID (must be a number)");
            return false;
        } catch (Exception e) {
            System.out.println("Error checking credentials: " + e.getMessage());
            return false;
        }
    }

    /**
     * {@code Buttonevent} that shiftsscene if credentials are correct else it shows {@code Alert}, and add to a wrongIDcounter
     * if counter hits 5, update the profile with the tried ID to "isLocked = true"
     * @param event click on a button
     */
    public void login(ActionEvent event){
        try {
            if (!checkCredentials()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Wrong Credentials");
                alert.setHeaderText("You have entered a wrong employee ID or password");
                alert.setContentText("Please enter a valid employee ID or password");
                alert.showAndWait();//This shows the alert
                wrongIDCounter++;

                if (oldID != Integer.parseInt(employeeIDFields.getText())) {
                    oldID = Integer.parseInt(employeeIDFields.getText());
                    wrongIDCounter = 1;
                }
                System.out.println("Wrong ID Counter: " + wrongIDCounter);
                if (wrongIDCounter ==5 ) {
                    ProfileDBInterface profileDB = new ProfileDB();
                    Profile currentIDsProfile = profileDB.getProfileFromID(Integer.parseInt(employeeIDFields.getText()));
                    profileDB.updateProfile(new Profile(
                            currentIDsProfile.getAccessLevelNotOrdnial(),
                            currentIDsProfile.getProfileID(),
                            currentIDsProfile.getName(),
                            currentIDsProfile.getUsername(),
                            currentIDsProfile.getDepartment(),
                            currentIDsProfile.getJobTitle(),
                            currentIDsProfile.getJobDescription(),
                            currentIDsProfile.getJobCategory(),
                            currentIDsProfile.isActivelySeeking(),
                            true));
                }

            } else {

                ProfileDB db = new ProfileDB();
                Profile profile = db.getProfileFromID(Integer.parseInt(employeeIDFields.getText()));
                MainSceneController.setCurrentProfile(profile);

                SceneService.shiftScene(event, "Jobswap", "/org/example/jobswap/MainScene.fxml");
            }
        }catch(Exception e) {
            e.printStackTrace();
            System.out.println("invalid employee ID or password");
            System.out.println(e.getMessage() + "Something went wrong in Login");
        }

    }

    /**
     * Scene shift to create new account
     * @param actionEvent click on the button
     */
    public void SceneShiftToCreateAccount(ActionEvent actionEvent) {
        try {
            SceneService.shiftScene(actionEvent, "Create New Profile", "/org/example/jobswap/CreateNewProfilScene.fxml");
        }catch(Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage() + "Something went wrong in SceneShiftToCreateAccount");
        }
    }
}
