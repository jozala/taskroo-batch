package pl.aetas.gtweb.batch;

import org.joda.time.DateTime;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.inject.Inject;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

public class Email {

    private final JavaMailSender sender;
    private final String recipient;
    private final String content;

    public Email(JavaMailSenderImpl sender, String recipient, String content) {
        this.sender = sender;
        this.recipient = recipient;
        this.content = content;

        sender.setHost("localhost");
    }

    public void send() throws MessagingException {

        MimeMessage message = sender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setSubject("GTWeb due date tasks for " + DateTime.now().toString("yyyy-MM-dd"));
        helper.setTo(recipient);
        helper.setText(content);

        sender.send(message);

    }
}
