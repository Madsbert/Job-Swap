package org.example.jobswap.Service;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.jobswap.View.JobswapApplication;

import java.io.IOException;


public class SceneService {
    public static void shiftScene(ActionEvent actionEvent, String title, Class controller ) throws IOException {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(JobswapApplication.class.getResource("/org/example/jobswap/MainScene.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        // Get controller
         controller = fxmlLoader.getController();
         //need a method for initizializing the scene

        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }
}
