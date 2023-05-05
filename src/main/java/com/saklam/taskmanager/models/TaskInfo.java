package com.saklam.taskmanager.models;

import java.sql.Date;

public class TaskInfo {
    private int taskID;
    private String taskName;
    private String taskDesc;
    private String status;
    private Date dateAdded;
    private Date dueDate;
    private Date dateCompleted;
    private int improtance;

    public TaskInfo(int taskID, String taskName, String taskDesc, String status, Date dueDate, int improtance) {
        this.taskID = taskID;
        this.taskName = taskName;
        this.taskDesc = taskDesc;
        this.status = status;
        this.dueDate = dueDate;
        this.improtance = improtance;
    }

    public int getImprotance() {
        return improtance;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getDateCompleted() {
        return dateCompleted;
    }

    public void setDateCompleted(Date dateCompleted) {
        this.dateCompleted = dateCompleted;
    }

    public void setImprotance(int improtance) {
        this.improtance = improtance;
    }

    public int getTaskID() {
        return taskID;
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDesc() {
        return taskDesc;
    }

    public void setTaskDesc(String taskDesc) {
        this.taskDesc = taskDesc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }
}
