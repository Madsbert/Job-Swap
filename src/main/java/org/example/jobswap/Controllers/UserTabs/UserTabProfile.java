package org.example.jobswap.Controllers.UserTabs;

import javafx.geometry.Insets;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.example.jobswap.Controllers.MainSceneController;
import org.example.jobswap.Service.BorderedVBox;
import org.example.jobswap.Service.Header;

import java.util.ArrayList;

public class UserTabProfile extends javafx.scene.control.Tab {

    private boolean editMode = false;
    private ChoiceBox departmentChoiceBox;
    private ChoiceBox jobCategoryChoiceBox;
    private TextField jobTitleField;
    private TextField jobDescriptionField;

    public UserTabProfile() {
        super("Profile");

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        scrollPane.setPadding(new Insets(25, 25, 25, 25));

        VBox vbox = new VBox();
        scrollPane.setContent(vbox);

        VBox profileDetailsBox = new BorderedVBox();
        profileDetailsBox.getChildren().add(new Header("Personal details"));
        vbox.getChildren().add(profileDetailsBox);

        HBox nameBox = new HBox();

        Label tLabel = new Label("Name:");
        tLabel.setPrefWidth(140);
        tLabel.setStyle("-fx-padding: 0 0 0 10;");

        nameBox.getChildren().add(tLabel);
        nameBox.getChildren().add(new Label(MainSceneController.getCurrentProfile().getName()));
        profileDetailsBox.getChildren().add(nameBox);

        HBox idBox = new HBox();

        Label t2Label = new Label("ID:");
        t2Label.setPrefWidth(140);
        t2Label.setStyle("-fx-padding: 0 0 0 10;");

        idBox.getChildren().add(t2Label);
        idBox.getChildren().add(new Label("" + MainSceneController.getCurrentProfile().getProfileID()));
        profileDetailsBox.getChildren().add(idBox);

        HBox departmentBox = new HBox();
        ArrayList<String> departments = new ArrayList<>();
        departments.add("Sønderborg");
        departmentChoiceBox = new ChoiceBox();
        departmentChoiceBox.getItems().addAll(departments);

        Label t3Label = new Label("Departments:");
        t3Label.setPrefWidth(140);
        t3Label.setStyle("-fx-padding: 0 0 0 10;");

        departmentBox.getChildren().add(t3Label);
        departmentBox.getChildren().add(departmentChoiceBox);
        profileDetailsBox.getChildren().add(departmentBox);


        HBox jobCategoryBox = new HBox();
        ArrayList<String> jobCategories = new ArrayList<>();
        jobCategories.add("Sanitet");
        jobCategories.add("Produktion");
        jobCategories.add("Pakkeri");
        jobCategoryChoiceBox = new ChoiceBox();
        jobCategoryChoiceBox.getItems().addAll(jobCategories);
        for (int i = 0; i < jobCategories.size(); i++) {
            if (MainSceneController.getCurrentProfile().getJobCategory().equals(jobCategories.get(i))) {
                jobCategoryChoiceBox.getSelectionModel().select(i);
            }
        }

        Label t4Label = new Label("Job Category:");
        t4Label.setPrefWidth(140);
        t4Label.setStyle("-fx-padding: 0 0 0 10;");

        jobCategoryBox.getChildren().add(t4Label);
        jobCategoryBox.getChildren().add(jobCategoryChoiceBox);
        profileDetailsBox.getChildren().add(jobCategoryBox);



        HBox JobTitleBox = new HBox();

        jobTitleField = new TextField(MainSceneController.getCurrentProfile().getJobTitle());

        Label t5Label = new Label("Job Title:");
        t5Label.setPrefWidth(140);
        t5Label.setStyle("-fx-padding: 0 0 0 10;");

        JobTitleBox.getChildren().add(t5Label);
        JobTitleBox.getChildren().add(jobTitleField);
        profileDetailsBox.getChildren().add(JobTitleBox);

        HBox JobDescriptionBox = new HBox();
        Label t6Label = new Label("Job Description:");

        jobDescriptionField = new TextField(MainSceneController.getCurrentProfile().getDescription());

        t6Label.setPrefWidth(140);
        t6Label.setStyle("-fx-padding: 0 0 0 10;");
        JobDescriptionBox.getChildren().add(t6Label);
        JobDescriptionBox.getChildren().add(jobDescriptionField);
        profileDetailsBox.getChildren().add(JobDescriptionBox);

        this.setContent(scrollPane);
    }

    private void getProfileInformation()
    {

    }

    private void editProfileInformation()
    {

    }

    private void setSeekingJobswap(boolean status)
    {

    }

    private void saveChanges()
    {

    }
}
