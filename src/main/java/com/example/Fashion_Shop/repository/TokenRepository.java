package com.example.Fashion_Shop.repository;


import com.example.Fashion_Shop.model.Token;
import com.example.Fashion_Shop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TokenRepository extends JpaRepository<Token, Long> {
    List<Token> findByUser(User user);
    Token findByToken(String token);
    Token findByRefreshToken(String token);
}

