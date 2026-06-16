package com.zion.pomodorozion;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Task {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    private String title;

    private boolean completed;

    private int completePomodoros;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public int getCompletePomodoros() {
        return completePomodoros;
    }

    public void setCompletePomodoros(int completePomodoros) {
        this.completePomodoros = completePomodoros;
    }
    

}
