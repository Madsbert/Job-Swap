package org.example.jobswap.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import org.example.jobswap.Controllers.UserTabs.UserTabMatches;
import org.example.jobswap.Controllers.UserTabs.UserTabMessages;
import org.example.jobswap.Controllers.UserTabs.UserTabProfile;
import org.example.jobswap.Controllers.UserTabs.UserTabSeekJobSwap;
import org.example.jobswap.Model.Profile;

/**
 * class which sets up the MAinescene.fxml
 */
public class MainSceneController {
    @FXML
    private TabPane tabPane;

    private static Profile currentProfile;

    public void initialize() {
        tabPane.getTabs().add(UserTabMatches.getInstance());
        tabPane.getTabs().add(new UserTabSeekJobSwap());
        tabPane.getTabs().add(new UserTabProfile());
        tabPane.getTabs().add(new UserTabMessages());
    }

    public static Profile getCurrentProfile() {
        return currentProfile;
    }

    public static void setCurrentProfile(Profile currentProfile) {
        MainSceneController.currentProfile = currentProfile;
    }
}
