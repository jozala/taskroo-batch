package pl.aetas.gtweb.batch;

import com.google.inject.AbstractModule;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public class MyModule extends AbstractModule {
    @Override
    protected void configure() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/aetas_gtweb");
        dataSource.setUsername("mariusz");
        dataSource.setPassword("fckgwrhqq2");
        dataSource.setDefaultReadOnly(true);

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        bind(JdbcTemplate.class).toInstance(jdbcTemplate);
    }
}
