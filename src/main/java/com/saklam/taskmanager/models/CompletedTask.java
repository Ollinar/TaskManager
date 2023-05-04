package com.saklam.taskmanager.models;

import java.sql.Date;

public class CompletedTask extends PendingTask{
    private Date dateCompleted;

    public CompletedTask(int taskID, String taskName, String taskDesc, String status, Date dateAdded, Date dueDate, Date dateCompleted) {
        super(taskID, taskName, taskDesc, status, dateAdded, dueDate);
        this.dateCompleted = dateCompleted;
    }

    public Date getDateCompleted() {
        return dateCompleted;
    }

    public void setDateCompleted(Date dateCompleted) {
        this.dateCompleted = dateCompleted;
    }
}
