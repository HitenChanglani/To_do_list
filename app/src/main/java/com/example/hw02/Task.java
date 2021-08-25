package com.example.hw02;

import java.io.Serializable;
import java.util.Date;
import java.util.GregorianCalendar;

public class Task implements Serializable {
    String taskName, priority;
    GregorianCalendar deadline;

    public Task(String taskName, String priority, GregorianCalendar deadline) {
        this.taskName = taskName;
        this.priority = priority;
        this.deadline = deadline;
    }

}
