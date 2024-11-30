package com.example.Fashion_Shop.component;

import com.example.Fashion_Shop.exception.DataNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.slf4j.Logger;

import java.util.Enumeration;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<String> handleDataNotFoundException(DataNotFoundException ex, HttpServletRequest request) {
        logger.warn("Handled DataNotFoundException: {}", ex.getMessage());
        logRequestDetails(request);
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    private void logRequestDetails(HttpServletRequest request) {
        StringBuilder details = new StringBuilder();
        details.append("\n--- Request Details ---\n");
        details.append("Servlet Path: ").append(request.getServletPath()).append("\n");
        details.append("Path Info: ").append(request.getPathInfo()).append("\n");
        details.append("Headers:\n");

        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            details.append(headerName).append(": ").append(request.getHeader(headerName)).append("\n");
        }

        logger.warn(details.toString()); // Ghi toàn bộ thông tin vào log
    }
}
