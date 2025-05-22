package org.example.jobswap.Controllers;

import javafx.event.ActionEvent;
import org.example.jobswap.Controllers.UserTabs.UserTabMatches;
import org.example.jobswap.Model.AccessLevel;
import org.example.jobswap.Model.Profile;
import org.example.jobswap.Service.SceneService;

import java.io.IOException;

/**
 * class to control the Login.fxml and has logic for login
 */
public class LoginController {

    private void checkCredentials(String workerId, String password)
    {

    }

    public void login(ActionEvent event) throws IOException {

//        Profile tempTestProfile = new Profile(
//                AccessLevel.EMPLOYEE,
//                2,
//                "Mikkel",
//                "Sønderborg",
//                "Afløser",
//                "Rykker pakker",
//                "Pakkeri",
//                false
//        );

        //MainSceneController.setCurrentProfile(tempTestProfile);
        UserTabMatches userTabMatches = new UserTabMatches();
        SceneService.shiftScene(event, "Jobswap","/org/example/jobswap/MainScene.fxml");
    }

    public void SceneShiftToCreateAccount(ActionEvent actionEvent) throws IOException {
        SceneService.shiftScene(actionEvent, "Create New Profile", "/org/example/jobswap/CreateNewProfilScene.fxml" );
    }
}
