package com.taskroo.batch.dao.mysql;

import org.joda.time.DateTime;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import com.taskroo.batch.dao.TasksDao;
import com.taskroo.batch.domain.Task;

import javax.inject.Inject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class TasksDaoMySql implements TasksDao {
    private final JdbcTemplate jdbcTemplate;

    @Inject
    public TasksDaoMySql(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Task> getAllDueDateTasksOfUser(Object userId) {
        if (!(userId instanceof Long)) {
            throw new IllegalArgumentException("userId has to be Long to get task from mysql");
        }

        return jdbcTemplate.query("SELECT title, dueDate FROM tasks WHERE owner_id = ? AND dueDate <= NOW() AND finished = FALSE ORDER BY dueDate ASC", new RowMapper<Task>() {
            @Override
            public Task mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Task(rs.getString("title"), new DateTime(rs.getDate("dueDate")));
            }
        }, userId);
    }
}
