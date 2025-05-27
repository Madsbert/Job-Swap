package org.example.jobswap.Service;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.jobswap.Controllers.UserTabs.UserTabMatches;
import org.example.jobswap.View.JobswapApplication;

import java.io.IOException;

/**
 * class which have a method to shift scene
 */
public class SceneService {
    public static void shiftScene(ActionEvent actionEvent, String title, String fxmlFile ) throws IOException {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(JobswapApplication.class.getResource(fxmlFile));
        Scene scene = new Scene(fxmlLoader.load());

        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }

    public static void shiftScene(MouseEvent actionEvent, String title, String fxmlFile ) throws IOException {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(JobswapApplication.class.getResource(fxmlFile));
        Scene scene = new Scene(fxmlLoader.load());

        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }
}
