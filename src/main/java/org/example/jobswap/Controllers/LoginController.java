package org.example.jobswap.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.jobswap.Controllers.UserTabs.UserTabMatches;
import org.example.jobswap.Model.AccessLevel;
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
            System.out.println("wrong credentials");
        }else {

            ProfileDB db = new ProfileDB();
            Profile profile = db.getProfileFromID(Integer.parseInt(employeeID.getText()));
            MainSceneController.setCurrentProfile(profile);
            UserTabMatches userTabMatches = new UserTabMatches();
            SceneService.shiftScene(event, "Jobswap", "/org/example/jobswap/MainScene.fxml");
        }

    }

    public void SceneShiftToCreateAccount(ActionEvent actionEvent) throws IOException {
        SceneService.shiftScene(actionEvent, "Create New Profile", "/org/example/jobswap/CreateNewProfilScene.fxml" );
    }
}
