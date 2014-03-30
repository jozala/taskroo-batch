package pl.aetas.gtweb.batch.domain;

import org.joda.time.DateTime;

public class Task {
    private final String title;
    private final DateTime dueDate;

    public Task(String title, DateTime dueDate) {
        this.title = title;
        this.dueDate = dueDate;
    }

    public String getTitle() {
        return title;
    }

    public DateTime getDueDate() {
        return dueDate;
    }
}
