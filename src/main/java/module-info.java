module com.saklam.taskmanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.saklam.taskmanager to javafx.fxml;
    exports com.saklam.taskmanager;
}