package org.example.jobswap.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.jobswap.Model.Profile;
import org.example.jobswap.Persistence.Interfaces.LoginDBInterface;
import org.example.jobswap.Persistence.LoginDB;
import org.example.jobswap.Persistence.ProfileDB;
import org.example.jobswap.Service.SceneService;

import java.io.IOException;


/**
 * class to control the Login.fxml and has logic for login
 */
public class LoginController {

    @FXML
    private TextField employeeID;

    @FXML
    private PasswordField password;

    /**
     * method that checks if the passwaord and employeeID matches in database
     * @return
     */
    private boolean checkCredentials()
    {
        int id = Integer.parseInt(employeeID.getText());
        String pass = password.getText();
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
     * Button event that shiftsscene if credentials are correct else it shows alert
     * @param event click on a button
     * @throws IOException
     */
    public void login(ActionEvent event) throws IOException {

//        Profile tempTestProfile = new Profile(
//                AccessLevel.EMPLOYEE,
//                1,
//                "Mikkel",
//                "MikkerMan",
//                "Sønderborg",
//                "Afløser",
//                "Rykker pakker",
//                "Pakkeri",
//                false
//        );
        if (!checkCredentials()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Wrong Credentials");
            alert.setHeaderText("You have entered a wrong employee ID or password");
            alert.setContentText("Please enter a valid employee ID or password");
            alert.showAndWait();//This shows the alert
        }else {

            ProfileDB db = new ProfileDB();
            Profile profile = db.getProfileFromID(Integer.parseInt(employeeID.getText()));
            MainSceneController.setCurrentProfile(profile);
            SceneService.shiftScene(event, "Jobswap", "/org/example/jobswap/MainScene.fxml");
        }

    }

    /**
     * Scene shift to create new account
     * @param actionEvent click on the button
     * @throws IOException
     */
    public void SceneShiftToCreateAccount(ActionEvent actionEvent) throws IOException {
        SceneService.shiftScene(actionEvent, "Create New Profile", "/org/example/jobswap/CreateNewProfilScene.fxml" );
    }
}
