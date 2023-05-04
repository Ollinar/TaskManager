/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.saklam.taskmanager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.stage.Stage;
/**
 * FXML Controller class
 *
 * @author ACER
 */
public class TodayTaskController implements Initializable {


    @FXML
    private Button btnToday;
    @FXML
    private Button btnUpcoming;
    @FXML
    private Button btnImportant;
    @FXML
    private Button btnTrash;
    @FXML
    private Button addnew;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void goToday(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Today.fxml"));
        Parent root =  loader.load();
        
       Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
       Scene scene = new Scene(root);
       stage.setScene(scene);
    }

    @FXML
    private void goUpcoming(ActionEvent event) {
    }

    @FXML
    private void goImportant(ActionEvent event) {
    }

    @FXML
    private void goTrash(ActionEvent event) {
    }

    @FXML
    private void add(ActionEvent event) {
    }

}
