package org.example.jobswap.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import org.example.jobswap.Controllers.UserTabs.UserTabMatches;
import org.example.jobswap.Controllers.UserTabs.UserTabMessages;
import org.example.jobswap.Controllers.UserTabs.UserTabProfile;
import org.example.jobswap.Controllers.UserTabs.UserTabSeekJobSwap;

public class MainSceneController {
    @FXML
    private TabPane tabPane;

    public void initialize() {
        tabPane.getTabs().add(new UserTabMatches());
        tabPane.getTabs().add(new UserTabMessages());
        tabPane.getTabs().add(new UserTabProfile());
        tabPane.getTabs().add(new UserTabSeekJobSwap());
    }
}
