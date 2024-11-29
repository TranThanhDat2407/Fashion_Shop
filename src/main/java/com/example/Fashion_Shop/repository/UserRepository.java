package com.example.Fashion_Shop.repository;

import com.example.Fashion_Shop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    User findByEmail(String email);
    User findUserById(Long id);


}
