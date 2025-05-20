package org.example.jobswap.Controllers;

import javafx.event.ActionEvent;
import org.example.jobswap.Controllers.UserTabs.UserTabMatches;
import org.example.jobswap.Service.SceneService;

import java.io.IOException;

public class LoginController {

    private void checkCredentials(String workerId, String password)
    {

    }

    public void login(ActionEvent event) throws IOException {

        UserTabMatches userTabMatches = new UserTabMatches();
        shiftScene(event, "Jobswap", userTabMatches.getClass());
    }

    private void shiftScene(ActionEvent event, String title, Class controller) throws IOException {
        SceneService.shiftScene(event, title, controller);
    }
}
