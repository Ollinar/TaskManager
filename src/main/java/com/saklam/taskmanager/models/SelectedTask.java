package com.saklam.taskmanager.models;

public class SelectedTask {
    private final SelectedTask INSTANCE = new SelectedTask();
    private TaskInfo selectedTask;
    private SelectedTask(){
    }

    public SelectedTask getINSTANCE() {
        return INSTANCE;
    }

    public TaskInfo getSelectedTask() {
        return selectedTask;
    }

    public void setSelectedTask(TaskInfo selectedTask) {
        this.selectedTask = selectedTask;
    }
}
