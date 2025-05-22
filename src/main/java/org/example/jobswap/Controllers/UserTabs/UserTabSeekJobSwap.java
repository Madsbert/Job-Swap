package org.example.jobswap.Controllers.UserTabs;

import javafx.geometry.Insets;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.example.jobswap.Controllers.MainSceneController;
import org.example.jobswap.Model.AccessLevel;
import org.example.jobswap.Model.Department;
import org.example.jobswap.Persistence.DepartmentDB;
import org.example.jobswap.Service.BorderedVBox;
import org.example.jobswap.Service.Header;

import java.util.ArrayList;

/**
 * class which sets up the tab seek jobswap
 */
public class UserTabSeekJobSwap extends javafx.scene.control.Tab {

    private static VBox departmentBox;
    private static ChoiceBox<String> departmentChoiceBox;

    public UserTabSeekJobSwap()
    {
        super("Seek JobSwap");

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        scrollPane.setPadding(new Insets(25, 25, 25, 25));

        VBox vbox = new VBox();
        scrollPane.setContent(vbox);

        departmentBox = new BorderedVBox();
        departmentBox.getChildren().add(new Header("Choose Department"));
        vbox.getChildren().add(departmentBox);

        VBox applicationsVBox = new BorderedVBox();
        applicationsVBox.getChildren().add(new Header("Potential Matches"));
        vbox.getChildren().add(applicationsVBox);

        this.setContent(scrollPane);

        showJobswapOptions();
    }

    private void showJobswapOptions()
    {
        ArrayList<String> departments = new ArrayList<>();

        for (Department department : DepartmentDB.getDepartments())
        {
            departments.add(department.getDepartmentName());
        }

        departmentChoiceBox = new ChoiceBox<String>();
        departmentChoiceBox.getItems().addAll(departments);

        Label label = new Label("Departments:");
        label.setPrefWidth(140);
        label.setStyle("-fx-padding: 0 0 0 10;");

        departmentBox.getChildren().add(label);
        departmentBox.getChildren().add(departmentChoiceBox);
    }

    private void setSelectedDepartment(int department)
    {

    }

    private void setSelectedJobcategory(int jobCategory)
    {

    }

    private void applyForJobswap()
    {

    }
}
