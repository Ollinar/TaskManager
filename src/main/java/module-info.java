module com.saklam.taskmanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.google.zxing;
    requires com.google.zxing.javase;
    requires webcam.capture;
    requires java.desktop;
    requires javafx.swing;
    requires java.base;



    opens com.saklam.taskmanager.controllers to javafx.fxml;
    opens com.saklam.taskmanager to javafx.fxml;
    opens com.saklam.taskmanager.models to javafx.base;
    exports com.saklam.taskmanager.models to java.compiler;
    exports com.saklam.taskmanager;
    exports com.saklam.taskmanager.controllers;
}