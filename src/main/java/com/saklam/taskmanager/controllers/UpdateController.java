/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.saklam.taskmanager.controllers;

import com.saklam.taskmanager.models.SelectedTask;
import com.saklam.taskmanager.models.TaskInfo;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
/**
 * FXML Controller class
 *
 * @author ACER
 */
public class UpdateController implements Initializable {


    @FXML
    private Button btnClose;
    @FXML
    private TextField txtTitle;
    @FXML
    private TextArea txtDesc;
    @FXML
    private DatePicker dteDue;
    @FXML
    private CheckBox chkImp;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        TaskInfo temp = SelectedTask.getINSTANCE().getSelectedTask();
        txtTitle.setText(temp.getTaskName());
        txtDesc.setText(temp.getTaskDesc());
        dteDue.setValue(temp.getDueDate().toLocalDate());
        if (temp.getImprotance() == 1) {
            chkImp.setSelected(true);
        }else{
            chkImp.setSelected(false);
        }
    }    
    
    @FXML
    private void close(ActionEvent event) {
        ((Stage) ((Button) event.getSource()).getScene().getWindow()).close();
    }

    @FXML
    private void save(ActionEvent event) {
        
    }

}
