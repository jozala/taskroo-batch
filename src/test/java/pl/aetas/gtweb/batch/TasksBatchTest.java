package pl.aetas.gtweb.batch;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.Test;

public class TasksBatchTest {
    @Test
    public void shouldRun() throws Exception {
        Injector injector = Guice.createInjector(new MyModule());
        TasksBatch tasksBatch = injector.getInstance(TasksBatch.class);

        tasksBatch.sendEmailsToUsersWithDueDateTasks();
    }
}