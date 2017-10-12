package ru.kamyshovcorp.weekplanner.model;

import java.util.List;

// TODO: renato to Card?
public class Category {

    private String title;
    private List<Task> tasks;

    public Category(String title, List<Task> tasks) {
        this.title = title;
        this.tasks = tasks;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
