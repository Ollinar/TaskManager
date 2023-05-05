package com.saklam.taskmanager.controllers;

import com.saklam.taskmanager.Database;
import com.saklam.taskmanager.models.TaskInfo;
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
import java.util.ResourceBundle;

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
    void add(ActionEvent event) {

    }

    @FXML
    void deleteTask(ActionEvent event) {

    }

    @FXML
    void editTask(ActionEvent event) {

    }

    @FXML
    void generateQR(ActionEvent event) {

    }

    @FXML
    void goImportant(ActionEvent event) {
        filter.setPredicate(taskInfo -> taskInfo.getImprotance() == 1 && taskInfo.getStatus().equalsIgnoreCase("Pending"));
        lblHeader.setText("Important Task");
    }

    @FXML
    void goToday(ActionEvent event) {

    }

    @FXML
    void goTrash(ActionEvent event) {
        filter.setPredicate(taskInfo -> taskInfo.getStatus().equalsIgnoreCase("Completed"));
        lblHeader.setText("Completed Task");
    }

    @FXML
    void goUpcoming(ActionEvent event) {
        filter.setPredicate(taskInfo -> taskInfo.getStatus().equalsIgnoreCase("Pending"));
        lblHeader.setText("Impending Task");
    }

    @FXML
    void markAsDone(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colTaskName.setCellValueFactory(new PropertyValueFactory<>("taskName"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("taskDesc"));
        colDue.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        try {
            Database.refreshTaskList(taskList);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage(), ButtonType.OK).showAndWait();
        }
        filter = new FilteredList<>(taskList);
        SortedList<TaskInfo> sorted = new SortedList<>(filter);
        sorted.comparatorProperty().bind(taskTable.comparatorProperty());
        taskTable.setItems(sorted);
        filter.setPredicate(taskInfo -> true);


    }
}
