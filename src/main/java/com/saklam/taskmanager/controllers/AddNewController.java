/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.saklam.taskmanager.controllers;

import com.saklam.taskmanager.App;
import com.saklam.taskmanager.Database;
import com.saklam.taskmanager.models.TaskInfo;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;

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
        LocalDate today = LocalDate.now();
        dteDue.setDayCellFactory(d-> new DateCell(){
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setDisable(item.isBefore(today));
            }
        });

    }

    @FXML
    private void close(ActionEvent event) {
        ((Stage) ((Button) event.getSource()).getScene().getWindow()).close();
    }

    @FXML
    private void saveTask(ActionEvent event) {
        try {
            Alert alrt = new Alert(Alert.AlertType.ERROR, "", ButtonType.OK);
            alrt.setTitle("Invalid Input");
            boolean invalidInp = false;
            if (txtTitle.getText().isBlank()) {
                alrt.setContentText(alrt.getContentText() + "TITLE IS REQUIRED\n");
                invalidInp = true;
            }
            if (txtDesc.getText().isBlank()) {
                alrt.setContentText(alrt.getContentText() + "DESCRIPTION IS REQUIRED\n");
                invalidInp = true;
            }
            if (dteDue.getValue() == null) {
                alrt.setContentText(alrt.getContentText() + "DUE DATE IS REQUIRED\n");
                invalidInp = true;
            }
            if (invalidInp) {
                alrt.show();
                return;
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
            JOptionPane.showMessageDialog(null, "Added Successful","Message",JOptionPane.INFORMATION_MESSAGE);

            Database.insertTask(new TaskInfo(title,desc,due,importance));
            
            ((Stage) ((Button) event.getSource()).getScene().getWindow()).close();
        } catch (SQLException ex) {
            new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK).show();
        }

    }

    @FXML
    private void openQRScanner(ActionEvent event) {
        try {
          
            Parent root = App.loadFXML("QRScanner");
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(((Node) event.getSource()).getScene().getWindow());
            stage.showAndWait();
            
            ((Stage) ((Button) event.getSource()).getScene().getWindow()).close();
            
         
        } catch (IOException ex) {
            new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK).show();new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK).show();
        }
    }

}
