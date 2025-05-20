module org.example.jobswap {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jdk.jshell;
    requires java.desktop;


    opens org.example.jobswap to javafx.fxml;
    exports org.example.jobswap.Controllers;
    opens org.example.jobswap.Controllers to javafx.fxml;
    exports org.example.jobswap.View;
    opens org.example.jobswap.View to javafx.fxml;
    exports org.example.jobswap.Controllers.UserTabs;
    opens org.example.jobswap.Controllers.UserTabs to javafx.fxml;
    exports org.example.jobswap.Controllers.HRTabs;
    opens org.example.jobswap.Controllers.HRTabs to javafx.fxml;
    exports org.example.jobswap.Controllers.SysAdminTabs;
    opens org.example.jobswap.Controllers.SysAdminTabs to javafx.fxml;
}