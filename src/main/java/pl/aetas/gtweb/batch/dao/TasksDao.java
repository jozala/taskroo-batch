package pl.aetas.gtweb.batch.dao;

import pl.aetas.gtweb.batch.domain.Task;

import java.util.List;

public interface TasksDao {
    List<Task> getAllDueDateTasksOfUser(Object user);
}
