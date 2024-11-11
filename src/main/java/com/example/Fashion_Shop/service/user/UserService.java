package com.example.Fashion_Shop.service.user;

import com.example.Fashion_Shop.dto.UpdateUserDTO;
import com.example.Fashion_Shop.dto.UserDTO;
import com.example.Fashion_Shop.exception.DataNotFoundException;
import com.example.Fashion_Shop.exception.InvalidPasswordException;
import com.example.Fashion_Shop.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class UserService implements IUserService {

    @Override
    public User createUser(UserDTO userDTO) throws Exception {
        return null;
    }

    @Override
    public String login(String phoneNumber, String password, Long roleId) throws Exception {
        return "";
    }

    @Override
    public User getUserDetailsFromToken(String token) throws Exception {
        return null;
    }

    @Override
    public User getUserDetailsFromRefreshToken(String token) throws Exception {
        return null;
    }

    @Override
    public User updateUser(Long userId, UpdateUserDTO updatedUserDTO) throws Exception {
        return null;
    }

    @Override
    public Page<User> findAll(String keyword, Pageable pageable) throws Exception {
        return null;
    }

    @Override
    public void resetPassword(Long userId, String newPassword) throws InvalidPasswordException, DataNotFoundException {

    }

    @Override
    public void blockOrEnable(Long userId, Boolean active) throws DataNotFoundException {

    }
}
