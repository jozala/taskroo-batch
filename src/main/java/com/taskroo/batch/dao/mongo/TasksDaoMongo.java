package com.taskroo.batch.dao.mongo;

import com.google.inject.Inject;
import com.mongodb.*;
import org.joda.time.DateTime;
import com.taskroo.batch.dao.TasksDao;
import com.taskroo.batch.domain.Task;

import javax.inject.Named;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class TasksDaoMongo implements TasksDao {

    private final DBCollection tasksCollection;

    @Inject
    public TasksDaoMongo(@Named("TasksDBCollection") DBCollection tasksCollection) {
        this.tasksCollection = tasksCollection;
    }

    @Override
    public List<Task> getAllDueDateTasksOfUser(Object userId) {
        if (!(userId instanceof String)) {
            throw new IllegalArgumentException("userId has to be a String to read task from MongoDB");
        }
        DBObject queryByOwnerIdFinishedAndDue = QueryBuilder.start("owner_id").is(userId)
                .and("finished").is(false)
                .and("due_date").lessThanEquals(new Date())
                .get();
        DBCursor dbTasks = tasksCollection.find(queryByOwnerIdFinishedAndDue).sort(new BasicDBObject("due_date", 1));
        List<Task> tasks = new LinkedList<>();
        for (DBObject dbTask : dbTasks) {
            tasks.add(new Task(dbTask.get("title").toString(), new DateTime(dbTask.get("due_date"))));
        }
        return tasks;
    }
}
