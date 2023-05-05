package com.saklam.taskmanager.controllers;

import com.saklam.taskmanager.App;
import com.saklam.taskmanager.Database;
import com.saklam.taskmanager.models.SelectedTask;
import com.saklam.taskmanager.models.TaskInfo;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class TodayTaskController implements Initializable {

    @FXML
    private Button addnew;

    @FXML
    private Button btnImportant;

    @FXML
    private Button btnToday;

    @FXML
    private Button btnTrash;

    @FXML
    private Button btnUpcoming;

    @FXML
    private TableColumn<TaskInfo, String> colDesc;

    @FXML
    private TableColumn<TaskInfo, Date> colDue;

    @FXML
    private TableColumn<TaskInfo, String> colTaskName;
    @FXML
    private TableView<TaskInfo> taskTable;

    @FXML
    private Label lblHeader;

    ObservableList<TaskInfo> taskList = FXCollections.observableArrayList();
    FilteredList<TaskInfo> filter;
    @FXML
    private Button btnEdit;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnMarkDone;
    @FXML
    private Button btnGenQR;
    
    private boolean finishSelected = false;

    private void disableControllButtons() {
        btnMarkDone.setDisable(true);
        btnDelete.setDisable(true);
        btnGenQR.setDisable(true);
        btnEdit.setDisable(true);
    }

    private void enableControllButtons() {
        btnMarkDone.setDisable(false);
        btnDelete.setDisable(false);
        btnGenQR.setDisable(false);
        if (!finishSelected) {
            btnEdit.setDisable(false);
        }
        
    }

    @FXML
    void add(ActionEvent event) {
        try {
            Parent root = App.loadFXML("Addnew");
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(((Node) event.getSource()).getScene().getWindow());
            stage.showAndWait();
            Database.refreshTaskList(taskList);
        } catch (IOException |SQLException ex) {
            new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK).show();
        }
    }

    @FXML
    void deleteTask(ActionEvent event) {
        try {
            Database.deleteTask(SelectedTask.getINSTANCE().getSelectedTask());
            disableControllButtons();
            Database.refreshTaskList(taskList);
            SelectedTask.getINSTANCE().setSelectedTask(null);
            taskTable.getSelectionModel().clearSelection();
            taskTable.setDisable(true);
            taskTable.setDisable(false);
        } catch (SQLException ex) {
            new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK).show();
        }

    }

    @FXML
    void editTask(ActionEvent event) {
        try {
            Parent root = App.loadFXML("Update");
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(((Node) event.getSource()).getScene().getWindow());
            stage.showAndWait();
            Database.refreshTaskList(taskList);
        } catch (IOException | SQLException ex) {
            new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK).show();
        }
    }

    @FXML
    void generateQR(ActionEvent event) {

    }

    @FXML
    void goImportant(ActionEvent event) {
        filter.setPredicate(taskInfo -> taskInfo.getImprotance() == 1 && taskInfo.getStatus().equalsIgnoreCase("Pending"));
        lblHeader.setText("Important Task");
        finishSelected = false;
    }

    @FXML
    void goToday(ActionEvent event) {
        LocalDate dateNow = LocalDate.now();
        Date today = Date.valueOf(dateNow);
        filter.setPredicate(taskInfo -> (taskInfo.getDueDate().compareTo(today) == 0) && taskInfo.getStatus().equalsIgnoreCase("Pending"));
        finishSelected = false;
    }

    @FXML
    void goTrash(ActionEvent event) {
        filter.setPredicate(taskInfo -> taskInfo.getStatus().equalsIgnoreCase("Completed"));
        lblHeader.setText("Completed Task");
        finishSelected = true;
    }

    @FXML
    void goUpcoming(ActionEvent event) {
        filter.setPredicate(taskInfo -> taskInfo.getStatus().equalsIgnoreCase("Pending"));
        lblHeader.setText("Impending Task");
        finishSelected = false;
    }

    @FXML
    void markAsDone(ActionEvent event) {
        try {
            TaskInfo temp = SelectedTask.getINSTANCE().getSelectedTask();
            Database.updateStatusTOCompleted(temp);
            Database.refreshTaskList(taskList);
            disableControllButtons();
            SelectedTask.getINSTANCE().setSelectedTask(null);
            taskTable.getSelectionModel().clearSelection();
            taskTable.setDisable(true);
            taskTable.setDisable(false);
        } catch (SQLException ex) {
            new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK).show();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colTaskName.setCellValueFactory(new PropertyValueFactory<>("taskName"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("taskDesc"));
        colDue.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        try {
            Database.refreshTaskList(taskList);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).showAndWait();
        }
        filter = new FilteredList<>(taskList);
        SortedList<TaskInfo> sorted = new SortedList<>(filter);
        sorted.comparatorProperty().bind(taskTable.comparatorProperty());
        taskTable.setItems(sorted);
        filter.setPredicate(taskInfo -> true);
        disableControllButtons();
        

    }

    @FXML
    private void selectTask(MouseEvent event) {
        TaskInfo selectTask = taskTable.getSelectionModel().getSelectedItem();
        if (selectTask != null) {
            SelectedTask.getINSTANCE().setSelectedTask(selectTask);

            enableControllButtons();
        }

    }
}
