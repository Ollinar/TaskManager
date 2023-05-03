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
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("hello-view.fxml"));
        scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static void setRoot(String fxmlName) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(App.class.getResource("fxml/" + fxmlName + ".fxml")));
        scene.setRoot(root);
        stage.sizeToScene();
    }
}