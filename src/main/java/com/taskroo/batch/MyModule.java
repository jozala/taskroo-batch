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
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

public class MyModule extends AbstractModule {
    @Override
    protected void configure() {

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

        bind(JavaMailSender.class).toInstance(javaMailSender());

        bind(TasksDao.class).to(TasksDaoMongo.class);
        bind(UsersDao.class).to(UsersDaoMongo.class);
    }

    public JavaMailSender javaMailSender() {

        JavaMailSenderImpl jm = new JavaMailSenderImpl();
        jm.setPort(587);
        jm.setProtocol("smtp");
        jm.setHost("in-v3.mailjet.com");
        jm.setUsername(System.getenv("EMAIL_LOGIN"));
        jm.setPassword(System.getenv("EMAIL_PASSWORD"));

        return jm;
    }
}
