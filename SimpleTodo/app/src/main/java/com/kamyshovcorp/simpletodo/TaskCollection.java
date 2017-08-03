package com.kamyshovcorp.simpletodo;

import java.util.ArrayList;
import java.util.List;

public class TaskCollection {

    private static List<String> tasks = new ArrayList<>();

    public static List<String> getTasks() {
        return tasks;
    }

    public static void setTasks(List<String> taskList) {
        tasks = taskList;
    }

    public static void addTask(String task) {
        tasks.add(task);
    }

    public static void removeTask(int position) {
        tasks.remove(position);
    }
}
