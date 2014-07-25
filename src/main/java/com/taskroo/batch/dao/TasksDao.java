package com.taskroo.batch.dao;

import com.taskroo.batch.domain.Task;

import java.util.List;

public interface TasksDao {
    List<Task> getAllDueDateTasksOfUser(Object user);
}
