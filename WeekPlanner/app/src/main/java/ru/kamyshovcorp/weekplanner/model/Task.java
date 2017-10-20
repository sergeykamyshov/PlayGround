package ru.kamyshovcorp.weekplanner.model;

public class Task {

    private boolean mDoneFlag;
    private String mDescription;

    public Task(boolean done, String description) {
        mDoneFlag = done;
        mDescription = description;
    }

    public boolean isDone() {
        return mDoneFlag;
    }

    public void setDone(boolean done) {
        mDoneFlag = done;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }
}
