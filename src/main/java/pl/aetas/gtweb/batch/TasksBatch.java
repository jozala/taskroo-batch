package pl.aetas.gtweb.batch;


import com.google.inject.Guice;
import com.google.inject.Injector;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import pl.aetas.gtweb.batch.dao.TasksDao;
import pl.aetas.gtweb.batch.dao.UsersDao;
import pl.aetas.gtweb.batch.domain.Task;
import pl.aetas.gtweb.batch.domain.User;

import javax.inject.Inject;
import javax.mail.MessagingException;
import java.util.LinkedList;
import java.util.List;

public class TasksBatch
{
    private final TasksDao tasksDao;
    private final UsersDao usersDao;
    private final JavaMailSenderImpl sender;

    public static void main(String[] args) throws MessagingException {
        Injector injector = Guice.createInjector(new MyModule());
        TasksBatch tasksBatch = injector.getInstance(TasksBatch.class);

        tasksBatch.sendEmailsToUsersWithDueDateTasks();
    }

    @Inject
    public TasksBatch(TasksDao tasksDao, UsersDao usersDao, JavaMailSenderImpl sender) {
        this.tasksDao = tasksDao;
        this.usersDao = usersDao;
        this.sender = sender;
    }

    public void sendEmailsToUsersWithDueDateTasks() throws MessagingException {
        List<Email> emails = new LinkedList<>();
        List<User> users = usersDao.getEnabledUsersList();
        System.out.println("" + users.size() + " users found.");
        for (User user : users) {
            List<Task> tasks = tasksDao.getAllDueDateTasksOfUser(user.getId());
            System.out.println("" + tasks.size() + " tasks found for user " + user.getEmail());
            if (tasks.size() > 0) {
                emails.add(createEmailWithUserTasks(user, tasks));
            }
        }

        System.out.println("Sending emails...");
        for (Email email : emails) {
            email.send();
        }
        System.out.println("Sending emails finished.");
    }

    private Email createEmailWithUserTasks(User user, List<Task> tasks) {
        String emailContent = "These tasks are due today or overdue:\n";
        for (Task task : tasks) {
            emailContent += "- " + task.getTitle() + " (" + task.getDueDate().toString("yyyy-MM-dd") + ")\n";
        }
        emailContent += "\nYours truly,\nGTWeb";
        return new Email(sender, user.getEmail(), emailContent);
    }
}
