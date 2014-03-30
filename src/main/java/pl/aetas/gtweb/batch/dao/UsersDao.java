package pl.aetas.gtweb.batch.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import pl.aetas.gtweb.batch.domain.User;

import javax.inject.Inject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UsersDao {
    private JdbcTemplate jdbcTemplate;

    @Inject
    public UsersDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<User> getEnabledUsersList() {
        return jdbcTemplate.query("SELECT id, email FROM users WHERE enabled = TRUE", new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet resultSet, int i) throws SQLException {
                return new User(resultSet.getLong("id"), resultSet.getString("email"));
            }
        });
    }
}
