package ru.kamyshovcorp.weekplanner.model;

import io.realm.RealmObject;

public class Task extends RealmObject {

    private boolean done;
    private String task;

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }
}
