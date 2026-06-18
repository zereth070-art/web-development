package com.zion.pomodorozion;

public class TaskCreateDTO {
    
    private String title;
    private int estimatedPomodoros;

    public TaskCreateDTO() {
    }

    public TaskCreateDTO(String title, int estimatedPomodoros) {
        this.title = title;
        this.estimatedPomodoros = estimatedPomodoros;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getEstimatedPomodoros() {
        return estimatedPomodoros;
    }

    public void setEstimatedPomodoros(int estimatedPomodoros) {
        this.estimatedPomodoros = estimatedPomodoros;
    }
    
}
