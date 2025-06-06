package org.example.jobswap.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.example.jobswap.Controllers.UserTabs.UserTabMatches;
import org.example.jobswap.Controllers.UserTabs.UserTabMessages;
import org.example.jobswap.Controllers.UserTabs.UserTabProfile;
import org.example.jobswap.Controllers.UserTabs.UserTabSeekJobSwap;
import org.example.jobswap.Model.Profile;
import org.example.jobswap.Service.SceneService;

/**
 * Class which sets up the Mainescene.fxml
 */
public class MainSceneController {
    @FXML
    private TabPane tabPane;

    @FXML
    private Label usernameLabel;

    @FXML
    private Button logoutButton;

    @FXML
    private Button getHelpButton;

    private static Profile currentProfile;

    /**
     * sets up Mainescene
     */
    public void initialize() {
        tabPane.getTabs().add(new UserTabMatches());
        tabPane.getTabs().add(new UserTabSeekJobSwap());
        tabPane.getTabs().add(new UserTabProfile());
        tabPane.getTabs().add(new UserTabMessages());

        tabPane.setOnMouseClicked(event -> {
            if (tabPane.getSelectionModel().getSelectedItem() instanceof UpdatableTab updatableTab) {
                // Updates every tab when t
                updatableTab.update();
            }
        });

        usernameLabel.setText("Hello " + currentProfile.getUsername() + "!");

        //logout button setup
        logoutButton.setOnMouseClicked(event -> {
            MainSceneController.setCurrentProfile(null);
            UserTabMessages.stopUpdating();
            SceneService.shiftScene(event, "JobSwap", "/org/example/jobswap/Login.fxml");
        });

        //Help button setup
        getHelpButton.setOnMouseClicked(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            //new stage for the alert, to change the icon.
            Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
            alertStage.getIcons().add(new Image(getClass().getResource("/org/example/jobswap/JobSwapIcon.png").toExternalForm()));
            //information
            alert.setTitle("Help");
            alert.setHeaderText("If you have a problem with anything, please contact your department System Administrator");
            alert.setContentText("📞 +45 12 34 56 67 ");
            alert.showAndWait();//This shows the alert
        });



    }

    public static Profile getCurrentProfile() {
        return currentProfile;
    }

    public static void setCurrentProfile(Profile currentProfile) {
        MainSceneController.currentProfile = currentProfile;
    }
}
