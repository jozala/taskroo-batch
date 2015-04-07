package com.taskroo.batch;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import com.google.inject.spring.SpringIntegration;
import com.mongodb.DBCollection;
import com.taskroo.batch.dao.TasksDao;
import com.taskroo.batch.dao.UsersDao;
import com.taskroo.batch.dao.mongo.TasksDaoMongo;
import com.taskroo.batch.dao.mongo.UsersDaoMongo;
import com.taskroo.mongo.CollectionsFactory;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

public class MyModule extends AbstractModule {
    @Override
    protected void configure() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/aetas_gtweb");
        dataSource.setUsername("mariusz");
        dataSource.setPassword("fckgwrhqq2");
        dataSource.setDefaultReadOnly(true);

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:/mongo-connector-context.xml");
        SpringIntegration.bindAll(binder(), applicationContext);

        CollectionsFactory collectionsFactory = applicationContext.getBean(CollectionsFactory.class);
        DBCollection tasksCollection = collectionsFactory.getCollection("tasks");
        DBCollection usersCollection = collectionsFactory.getCollection("users");

        bind(DBCollection.class)
                .annotatedWith(Names.named("TasksDBCollection"))
                .toInstance(tasksCollection);

        bind(DBCollection.class)
                .annotatedWith(Names.named("UsersDBCollection"))
                .toInstance(usersCollection);

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        bind(JdbcTemplate.class).toInstance(jdbcTemplate);
        bind(JavaMailSender.class).toInstance(javaMailSender());

        bind(TasksDao.class).to(TasksDaoMongo.class);
        bind(UsersDao.class).to(UsersDaoMongo.class);
    }

    public JavaMailSender javaMailSender() {

        JavaMailSenderImpl jm = new JavaMailSenderImpl();
        jm.setPort(587);
        jm.setProtocol("smtp");
        jm.setHost("in-v3.mailjet.com");
        jm.setUsername(System.getProperty("EMAIL_LOGIN"));
        jm.setPassword(System.getProperty("EMAIL_PASSWORD"));

        return jm;
    }
}
