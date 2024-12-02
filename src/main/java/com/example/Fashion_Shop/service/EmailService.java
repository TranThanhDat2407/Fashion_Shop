package com.example.Fashion_Shop.service;

import com.example.Fashion_Shop.model.Order;
import com.example.Fashion_Shop.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
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

    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }
}
