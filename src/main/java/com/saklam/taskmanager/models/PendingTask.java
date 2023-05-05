package com.saklam.taskmanager.models;

import java.sql.Date;

public class PendingTask extends TaskInfo{
    private Date dueDate;

    public PendingTask(int taskID, String taskName, String taskDesc, String status, Date dateAdded, int improtance, Date dueDate) {
        super(taskID, taskName, taskDesc, status, dateAdded, improtance);
        this.dueDate = dueDate;
    }


    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
}
