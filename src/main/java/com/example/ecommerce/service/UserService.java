package com.example.ecommerce.service;

import com.example.ecommerce.dto.UserDTO;
import com.example.ecommerce.model.User;
import com.example.ecommerce.response.UserResponse;

import java.util.List;

public interface UserService {

    User addUser(UserDTO userDto);

    List<UserResponse> getAllUsers();

    UserResponse getUserById(Long id);

    Integer deleteUser(Long id);

    User updateUser(Long id, UserDTO userDto);
}
