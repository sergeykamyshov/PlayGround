package ru.kamyshovcorp.weekplanner.model;

public class Task {

    private boolean done;
    private String description;

    public Task(boolean done, String description) {
        this.done = done;
        this.description = description;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
