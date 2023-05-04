package com.saklam.taskmanager;

import com.saklam.taskmanager.models.TaskInfo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
            PreparedStatement stmnt = conn.prepareStatement("INSERT INTO alltask (taskName,taskDesc,dateAdded,status)VALUES (?,?,?,?)")
            ){
                stmnt.setString(1,taskToInsert.getTaskName());
                stmnt.setString(2,taskToInsert.getTaskDesc());
                stmnt.setDate(3,taskToInsert.getDateAdded());
                stmnt.setString(4,taskToInsert.getStatus());
                stmnt.execute();
        }
    }
}
