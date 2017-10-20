package ru.kamyshovcorp.weekplanner.model;

import java.util.List;

public class Card {

    private String mTitle;
    private List<Task> mTasks;

    public Card(String title, List<Task> tasks) {
        mTitle = title;
        mTasks = tasks;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public List<Task> getTasks() {
        return mTasks;
    }

    public void setTasks(List<Task> tasks) {
        mTasks = tasks;
    }
}
