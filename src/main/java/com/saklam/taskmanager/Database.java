package com.saklam.taskmanager;

import com.saklam.taskmanager.models.PendingTask;
import com.saklam.taskmanager.models.SelectedTask;
import com.saklam.taskmanager.models.TaskInfo;
import javafx.collections.ObservableList;

import java.sql.*;

public class Database {
    private static final String dbUrl="jdbc:mysql://localhost:3306/taskmanagerdb";
    private static final String dbPass = "";
    private static final String dbUsername = "root";


    private static Connection connectDB() throws SQLException {
        Connection con;
        con = DriverManager.getConnection(dbUrl,dbUsername,dbPass);
        return con;
    }

    public static void insertTask(TaskInfo taskToInsert) throws SQLException {
        try(Connection conn = connectDB();
            ResultSet pendingRes = conn.createStatement().executeQuery("SELECT id FROM status WHERE label = 'Pending'");
            PreparedStatement stmnt = conn.prepareStatement("INSERT INTO tasks (taskName,taskDesc,dueDate,status_id,importance)VALUES (?,?,?,?,?)")
            ){ 
                pendingRes.next();
                int pendingID =  pendingRes.getInt("id");
                stmnt.setString(1,taskToInsert.getTaskName());
                stmnt.setString(2,taskToInsert.getTaskDesc());
                stmnt.setDate(3,taskToInsert.getDueDate());
                stmnt.setInt(4,pendingID);
                stmnt.setInt(5, taskToInsert.getImprotance());
                stmnt.execute();
        }
    }

    public static void refreshTaskList(ObservableList<TaskInfo> taskList) throws SQLException {
        taskList.clear();
        try(Connection conn = connectDB();
            PreparedStatement stmnt = conn.prepareStatement("SELECT t.taskID, t.taskName,t.taskDesc,"
                    + "s.label AS 'status', t.dueDate, t.importance FROM tasks t INNER JOIN status s ON s.id = t.status_id "
                    + "WHERE s.label != 'Deleted'")){
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
            ResultSet deleteIDRes = conn.createStatement().executeQuery("SELECT id FROM status WHERE label = 'Deleted'");
            PreparedStatement logStmnt = conn.prepareStatement("INSERT INTO logs (task_id,operation)VALUES (?,?)");
            PreparedStatement stmnt = conn.prepareStatement("UPDATE tasks SET status_id =? WHERE taskID=?")
                ){
            deleteIDRes.next();
            int deleteStatID = deleteIDRes.getInt("id");
            stmnt.setInt(1, deleteStatID);
            stmnt.setInt(2, taskToDelete.getTaskID());
            stmnt.execute();
            logStmnt.setInt(1, taskToDelete.getTaskID());
            logStmnt.setString(2, "Task Deleted");
            logStmnt.execute();
            
        }
    }
    public static void updateStatusTOCompleted(TaskInfo taskToUpdate) throws SQLException{
        try(Connection conn = connectDB();
            ResultSet completedRes = conn.createStatement().executeQuery("SELECT id FROM status WHERE label = 'Completed'");
            PreparedStatement logStmnt = conn.prepareStatement("INSERT INTO logs (task_id,operation)VALUES (?,?)");
            PreparedStatement stmnt = conn.prepareStatement("UPDATE tasks SET status_id =? WHERE taskID=?")
                ){
            completedRes.next();
            int completedID = completedRes.getInt("id");
            stmnt.setInt(1, completedID);
            stmnt.setInt(2, taskToUpdate.getTaskID());
            stmnt.execute();
            logStmnt.setInt(1, taskToUpdate.getTaskID());
            logStmnt.setString(2, "Task Completed");
            logStmnt.execute();
        }
    }
    public static void editTask(TaskInfo taskToDelete) throws SQLException {
        try(Connection conn = connectDB();
            PreparedStatement logStmnt = conn.prepareStatement("INSERT INTO logs (task_id,operation)VALUES (?,?)");
            PreparedStatement statement = conn.prepareStatement("UPDATE tasks SET taskName = ?, taskDesc = ?, dueDate = ?, importance = ? WHERE taskID = ?")){
            statement.setString(1,taskToDelete.getTaskName());
            statement.setString(2,taskToDelete.getTaskDesc());
            statement.setDate(3,taskToDelete.getDueDate());
            statement.setInt(4,taskToDelete.getImprotance());
            statement.setInt(5,taskToDelete.getTaskID());
            statement.execute();
            logStmnt.setInt(1, taskToDelete.getTaskID());
            logStmnt.setString(2, "Task Edited");
            logStmnt.execute();

        }
    }

}
