package com.saklam.taskmanager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.StageStyle;

public class App extends Application {
    static Scene scene;
    static Stage stage;
    private double xOffset = 0;
    private double yOffset = 0;
    
    
    @Override
    public void start(Stage PrimStage) throws IOException {

       /* scene = new Scene(loadFXML("Landing"));
        stage = new Stage();
        stage.setTitle("Keeper");
        stage.setScene(scene);
        stage.sizeToScene();
        stage.setResizable(false);
        stage.show();
        stage.initStyle(StageStyle.UNDECORATED);*/
       
       try {
            Parent root = App.loadFXML("Landing");
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);

            scene.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    xOffset = event.getSceneX();
                    yOffset = event.getSceneY();
                }
            });
            scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    stage.setX(event.getScreenX() - xOffset);
                    stage.setY(event.getScreenY() - yOffset);
                }
            });

            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            //stage.initOwner(((Node) event.getSource()).getScene().getWindow());
            stage.showAndWait();
          
           
        } catch (IOException ex) {
            new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK).show();
        }
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

    private static class event {

        public event() {
        }
    }
}