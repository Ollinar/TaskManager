package com.saklam.taskmanager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.stage.StageStyle;

public class App extends Application {
    static Scene scene;
    static Stage stage;
    private double xOffset = 0;
    private double yOffset = 0;
    
    
    @Override
    public void start(Stage PrimStage) throws IOException {

        scene = new Scene(loadFXML("Landing"));
        stage = new Stage();
        stage.setTitle("Keeper");
        stage.setScene(scene);
        stage.sizeToScene();
        stage.setResizable(false);
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