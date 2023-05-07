/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.saklam.taskmanager.controllers;

import com.saklam.taskmanager.Database;
import com.saklam.taskmanager.models.SelectedTask;
import com.saklam.taskmanager.models.TaskInfo;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
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
        try {
            String title = txtTitle.getText();
            String desc = txtDesc.getText();
            Date due = Date.valueOf(dteDue.getValue());
            int importance;
            if (chkImp.isSelected()) {
                importance = 1;
            }else{
                importance = 0;
            }
            JOptionPane.showMessageDialog(null, "Update Successful","Message",JOptionPane.INFORMATION_MESSAGE);

            Database.insertTask(new TaskInfo(title,desc,due,importance));
            
            ((Stage) ((Button) event.getSource()).getScene().getWindow()).close();
        } catch (SQLException ex) {
            new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK).show();
        }

    
    }

}
