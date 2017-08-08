package com.kamyshovcorp.simpletodo.model;

public class Task {

    private String id;
    private String name;
    private String dueDate;

    public Task(String name, String dueDate) {
        this.name = name;
        this.dueDate = dueDate;
    }

    public Task(String id, String name, String dueDate) {
        this.id = id;
        this.name = name;
        this.dueDate = dueDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }
}
