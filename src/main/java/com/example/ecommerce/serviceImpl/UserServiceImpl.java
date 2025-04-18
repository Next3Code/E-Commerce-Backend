package com.example.ecommerce.serviceImpl;

import com.example.ecommerce.dto.UserDTO;
import com.example.ecommerce.model.User;
import com.example.ecommerce.repository.UserRepository;
import com.example.ecommerce.response.UserResponse;
import com.example.ecommerce.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public User addUser(UserDTO userDto) {
        User user = new User();
        // converting userDto to user
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        // password encryption starts here
        Base64.Encoder encoder = Base64.getEncoder();   // get encoder from Base64
        // passing byte array to encode so we can get string containing encoded characters
        String encryptedPassword = encoder.encodeToString(userDto.getPassword().getBytes());
        user.setPassword(encryptedPassword);
        user.setCreatedBy(userDto.getCreatedBy());
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = currentDateTime.format(formatter);
        user.setCreatedDate(formattedDateTime);
        userRepository.save(user);
        logger.info("user created successfully");
        return user;
    }

    @Override
    public List<UserResponse> getAllUsers() {
        List<User> userList = userRepository.findAll();
        List<UserResponse> response = new ArrayList<>();
        for (User user : userList) {
            UserResponse userResponse = new UserResponse();
            userResponse.setName(user.getName());
            userResponse.setEmail(user.getEmail());
            userResponse.setCreatedDate(user.getCreatedDate());
            userResponse.setCreatedBy(user.getCreatedBy());
            response.add(userResponse);
        }
        if(!response.isEmpty()) {
            return response;
        }
        return null;
    }

    @Override
    public UserResponse getUserById(Long id) {
        UserResponse userResponse = new UserResponse();
        User user = userRepository.findUserById(id);
        if(user != null) {
            userResponse.setName(user.getName());
            userResponse.setEmail(user.getEmail());
            userResponse.setCreatedBy(user.getCreatedBy());
            userResponse.setCreatedDate(user.getCreatedDate());
            return userResponse;
        }
        logger.info("user not found with given id");
        return null;
    }

    public Integer deleteUser(Long id) {
        User user = userRepository.findUserById(id);
        if(user != null) {
            userRepository.deleteById(id);
            return 1;
        }
        return 0;
    }

    @Override
    public User updateUser(Long id, UserDTO userDto) {
        User user = userRepository.findUserById(id);
        if (user != null) {
            user.setName(userDto.getName());
            user.setEmail(userDto.getEmail());
            userRepository.save(user);
            return user;
        }
        logger.info("user is updated successfully");
        return null;
    }
}
