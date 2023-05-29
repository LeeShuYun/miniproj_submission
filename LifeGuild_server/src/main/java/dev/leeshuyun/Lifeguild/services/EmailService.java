package dev.leeshuyun.Lifeguild.services;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
    Logger logger = LoggerFactory.getLogger(EmailService.class);
    @Autowired
    private JavaMailSender mailSender;

    // works!
    public void sendSimpleEmail(String toEmail, String subject,
            String simpleText) {
        logger.info("sending simpleEmail to %s".formatted(toEmail));
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(simpleText);

        mailSender.send(message);
        logger.info("email sent");
    }

    public void sendHtmlEmail(
            String toEmail, String subject,
            String fullHtmlEmailText)
            throws MessagingException {
        logger.info("sending htmlEmail to %s".formatted(toEmail));

        MimeMessage message = mailSender.createMimeMessage();
        message.setRecipients(
                MimeMessage.RecipientType.TO, toEmail);
        message.setSubject(subject);

        message.setContent(fullHtmlEmailText, "text/html; charset=utf-8");

        mailSender.send(message);
    }

    // TODO - for next time
    // public void sendBulkHTMLTemplate(String... email) {
    // String recipient = "john.doe@example.com";
    // String subject = "Hello, ${firstName}!";
    // String template = "Hello, ${firstName}!\n\n"
    // + "This is a message just for you, ${firstName} ${lastName}. "
    // + "We hope you're having a great day!\n\n"
    // + "Best regards,\n"
    // + "The Spring Boot Team";
    // for (String string : email) {
    // // setting custom template variables
    // Map<String, Object> variables = new HashMap<>();
    // variables.put("firstName", "John");
    // variables.put("lastName", "Doe");
    // // sendEmail(recipient, subject, template, variables);
    // }
    // }
}