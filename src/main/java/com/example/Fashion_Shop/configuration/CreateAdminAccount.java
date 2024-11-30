package com.example.Fashion_Shop.configuration;

import com.example.Fashion_Shop.model.Role;
import com.example.Fashion_Shop.model.User;
import com.example.Fashion_Shop.repository.RoleRepository;
import com.example.Fashion_Shop.repository.UserRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
@AllArgsConstructor
public class CreateAdminAccount {
    private final PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            String adminEmail = "admin@admin.com";

            // Kiểm tra xem admin đã tồn tại chưa
            if (userRepository.findByEmail(adminEmail).isEmpty()) {

                // Tìm role admin (giả định role admin có ID = 1)
                Role role = roleRepository.findById(1L)
                        .orElseThrow(() -> new IllegalStateException("Role with ID 1 (admin role) does not exist. Please seed roles in the database."));

                // Tạo user admin
                User user = User.builder()
                        .name("admin")
                        .email(adminEmail)
                        .password(passwordEncoder.encode("admin")) // Mã hóa mật khẩu
                        .role(role) // Gán role admin
                        .active(true)
                        .build();

                userRepository.save(user); // Lưu user vào database

                log.warn("Admin user has been created with default " +
                        "email: admin@admin.com, password: admin. " +
                        "Please change the password immediately!");
            }

            log.info("Application initialization completed.");
        };
    }
}
