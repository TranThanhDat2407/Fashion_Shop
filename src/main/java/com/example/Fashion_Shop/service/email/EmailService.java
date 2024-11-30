package com.example.Fashion_Shop.service.email;



import com.example.Fashion_Shop.model.Order;
import com.example.Fashion_Shop.model.User;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;


@AllArgsConstructor
@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendOrderConfirmationEmail(User user, Order order) {
        String subject = "Order Confirmation";
        String body = "<h1>Thank you for your order, " + user.getName() + "!</h1>"
                + "<p>Order ID: " + order.getId() + "</p>"
                + "<p>Total Amount: " + order.getTotalMoney() + "</p>"
                + "<p>Shipping Address: " + order.getShippingAddress() + "</p>";

        sendEmail(user.getEmail(), subject, body);
    }

    private void sendEmail(String to, String subject, String body) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }
}
