package com.example.Fashion_Shop.service.user;

import com.example.Fashion_Shop.dto.UpdateUserDTO;
import com.example.Fashion_Shop.dto.UserDTO;
import com.example.Fashion_Shop.exception.DataNotFoundException;
import com.example.Fashion_Shop.exception.InvalidPasswordException;
import com.example.Fashion_Shop.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUserService {
    User createUser(UserDTO userDTO) throws Exception;
    String login(String phoneNumber, String password, Long roleId) throws Exception;
    User getUserDetailsFromToken(String token) throws Exception;
    User getUserDetailsFromRefreshToken(String token) throws Exception;
    User updateUser(Long userId, UpdateUserDTO updatedUserDTO) throws Exception;

    Page<User> findAll(String keyword, Pageable pageable) throws Exception;
    void resetPassword(Long userId, String newPassword)
            throws InvalidPasswordException, DataNotFoundException;
    public void blockOrEnable(Long userId, Boolean active) throws DataNotFoundException;
}
