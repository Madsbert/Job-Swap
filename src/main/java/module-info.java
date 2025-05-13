module org.example.jobswap {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.jobswap to javafx.fxml;
    exports org.example.jobswap;
}