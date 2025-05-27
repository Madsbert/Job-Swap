package org.example.jobswap.Service;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.jobswap.View.JobswapApplication;

import java.io.IOException;

/**
 * class which have methods to shift scene
 */
public class SceneService {
    /**
     * Method to shift scene
     * @param actionEvent an event
     * @param title string title of the scene
     * @param fxmlFile string fxml file name
     */
    public static void shiftScene(ActionEvent actionEvent, String title, String fxmlFile ){
        try {
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(JobswapApplication.class.getResource(fxmlFile));
            Scene scene = new Scene(fxmlLoader.load());

            stage.setTitle(title);
            stage.setScene(scene);
            stage.show();
        }catch (IOException e){
            e.printStackTrace();
            System.out.println(e.getMessage() + "something went wrong in shift scene");
        }
    }

    /**
     * Method to shift scene
     * @param actionEvent a Mouseevent
     * @param title string title of the scene
     * @param fxmlFile the name of the fxml
     */
    public static void shiftScene(MouseEvent actionEvent, String title, String fxmlFile ) {
        try {
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(JobswapApplication.class.getResource(fxmlFile));
            Scene scene = new Scene(fxmlLoader.load());

            stage.setTitle(title);
            stage.setScene(scene);
            stage.show();
        }catch (IOException e){
            e.printStackTrace();
            System.out.println(e.getMessage() + "something went wrong in shift scene");
        }
    }
}
