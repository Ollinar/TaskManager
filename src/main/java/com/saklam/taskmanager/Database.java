package com.saklam.taskmanager;

import com.saklam.taskmanager.models.PendingTask;
import com.saklam.taskmanager.models.TaskInfo;
import javafx.collections.ObservableList;

import java.sql.*;

public class Database {
    private static final String dbUrl="jdbc:mysql://localhost:3306/taskmanagerdb";
    private static final String dbPass = "carpio15";
    private static final String dbUsername = "root";


    private static Connection connectDB() throws SQLException {
        Connection con;
        con = DriverManager.getConnection(dbUrl,dbUsername,dbPass);
        return con;
    }

    public static void insertTask(TaskInfo taskToInsert) throws SQLException {
        try(Connection conn = connectDB();
            PreparedStatement stmnt = conn.prepareStatement("INSERT INTO alltask (taskName,taskDesc,dateAdded,status)VALUES (?,?,?,?)")
            ){
                stmnt.setString(1,taskToInsert.getTaskName());
                stmnt.setString(2,taskToInsert.getTaskDesc());
                stmnt.setDate(3,taskToInsert.getDateAdded());
                stmnt.setString(4,taskToInsert.getStatus());
                stmnt.execute();
        }
    }

    public static void refreshTaskList(ObservableList<TaskInfo> taskList) throws SQLException {
        taskList.clear();
        try(Connection conn = connectDB();
            PreparedStatement stmnt = conn.prepareStatement("SELECT * FROM alltask")){
            ResultSet resultSet = stmnt.executeQuery();
            while (resultSet.next()){
                int id = resultSet.getInt("taskID");
                String name = resultSet.getString("taskName");
                String desc = resultSet.getString("taskDesc");
                String status = resultSet.getString("status");
                Date dueDate = resultSet.getDate("dueDate");
                int importance = resultSet.getInt("importance");
                taskList.add(new TaskInfo(id,name,desc,status,dueDate,importance));
            }

        }

    }

    public static void deleteTask(TaskInfo taskToDelete) throws SQLException {
        try(Connection conn = connectDB();
            PreparedStatement stmnt = conn.prepareStatement("DELETE * FROM alltask WHERE teskID = ?")){
            stmnt.setInt(1,taskToDelete.getTaskID());
            stmnt.execute();
        }
    }
}
