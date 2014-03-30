package pl.aetas.gtweb.batch.dao;

import org.joda.time.DateTime;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import pl.aetas.gtweb.batch.domain.Task;
import pl.aetas.gtweb.batch.domain.User;

import javax.inject.Inject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class TasksDao {
    private final JdbcTemplate jdbcTemplate;

    @Inject
    public TasksDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Task> getAllDueDateTasksOfUser(long user) {
            return jdbcTemplate.query("SELECT title, dueDate FROM tasks WHERE owner_id = ? AND dueDate <= NOW() AND finished = FALSE ORDER BY dueDate ASC", new RowMapper<Task>() {
            @Override
            public Task mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Task(rs.getString("title"), new DateTime(rs.getDate("dueDate")));
            }
        }, user);
    }
}
