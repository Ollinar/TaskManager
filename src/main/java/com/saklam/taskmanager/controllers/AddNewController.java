/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.saklam.taskmanager.controllers;

import com.saklam.taskmanager.Database;
import com.saklam.taskmanager.models.TaskInfo;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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

/**
 * FXML Controller class
 *
 * @author ACER
 */
public class AddNewController implements Initializable {

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

    }

    @FXML
    private void close(ActionEvent event) {
        ((Stage) ((Button) event.getSource()).getScene().getWindow()).close();
    }

    @FXML
    private void saveTask(ActionEvent event) {
        try {
            Alert alrt = new Alert(Alert.AlertType.ERROR, "", ButtonType.OK);
            boolean invalidInp = false;
            if (txtTitle.getText().isBlank()) {
                alrt.setContentText(alrt.getContentText() + "Empty Title, Please Enter");
                invalidInp = true;
            }
            if (txtDesc.getText().isBlank()) {
                alrt.setContentText(alrt.getContentText() + "Empty Title, Please Enter");
                invalidInp = true;
            }
            if (dteDue.getValue() == null) {
                alrt.setContentText(alrt.getContentText() + "Empty Due Date, Please Enter");
                invalidInp = true;
            }
            String title = txtTitle.getText();
            String desc = txtDesc.getText();
            Date due = Date.valueOf(dteDue.getValue());
            int importance;
            if (chkImp.isSelected()) {
                importance = 1;
            }else{
                importance = 0;
            }
            Database.insertTask(new TaskInfo(title,desc,due,importance));
            
            ((Stage) ((Button) event.getSource()).getScene().getWindow()).close();
        } catch (SQLException ex) {
            new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK).show();
        }

    }

}
