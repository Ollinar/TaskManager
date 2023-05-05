package com.saklam.taskmanager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class App extends Application {
    static Scene scene;
    static Stage stage;
    @Override
    public void start(Stage PrimStage) throws IOException {

        scene = new Scene(loadFXML("Today"));
        stage = new Stage();
        stage.setTitle("Task Completed");
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static void setRoot(String fxmlName) throws IOException {
        scene.setRoot(loadFXML(fxmlName));
        stage.sizeToScene();
    }
    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader  (App.class.getResource("/fxml/"+fxml + ".fxml"));
        return fxmlLoader.load();
    }
}