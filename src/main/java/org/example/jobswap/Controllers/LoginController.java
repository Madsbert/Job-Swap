package org.example.jobswap.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.jobswap.Model.Profile;
import org.example.jobswap.Persistence.Interfaces.LoginDBInterface;
import org.example.jobswap.Persistence.LoginDB;
import org.example.jobswap.Persistence.ProfileDB;
import org.example.jobswap.Service.PasswordEncrypter;
import org.example.jobswap.Service.SceneService;

import java.io.IOException;


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


    @FXML
    public void initialize() {
        // Allows "Enter" key to log in
        loginButton.setDefaultButton(true);
    }

    /**
     * Method that checks if the password and employeeID matches in database
     * @return a {@code boolean} if the credentials match or not
     */
    private boolean checkCredentials()
    {
        int id = Integer.parseInt(employeeIDFields.getText());
        String pass = PasswordEncrypter.encrypt(passwordField.getText());
        try {
            LoginDBInterface db = new LoginDB();
            return db.checkCredentials(id, pass);
        }catch(NumberFormatException e) {
            System.out.println("invalid employee ID");
            return false;
        }catch (Exception e) {
            System.out.println("invalid password or employee ID");
            return false;
        }
    }

    /**
     * {@code Buttonevent} that shiftsscene if credentials are correct else it shows {@code Alert}
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
