package com.example.ecommerce.controller;

import com.example.ecommerce.dto.UserDTO;
import com.example.ecommerce.model.User;
import com.example.ecommerce.response.UserResponse;
import com.example.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/greet")
    public String greetMessage() {
        return "Hi, Good Morning!";
    }

    // create new user
    @PostMapping("/createUser")
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDto) {
        UserResponse userResponse = new UserResponse();
        User user = userService.addUser(userDto);
        if(user != null) {
            // converting user to userResponse
            userResponse.setName(user.getName());
            userResponse.setEmail(user.getEmail());
            userResponse.setCreatedBy(user.getCreatedBy());
            userResponse.setCreatedDate(user.getCreatedDate());
            return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
    }

    // get all users
    @GetMapping("")
    public ResponseEntity<?> getUsers(){
        List<UserResponse> userResponseList = userService.getAllUsers();
        if(!userResponseList.isEmpty()) {
            return new ResponseEntity<>(userResponseList, HttpStatus.FOUND);
        }
        return new ResponseEntity<>("User list is empty", HttpStatus.NOT_FOUND);
    }

    // get user by id
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        UserResponse userResponse = userService.getUserById(id);
        if(userResponse != null) {
            return new ResponseEntity<>(userResponse, HttpStatus.FOUND);
        }
        return new ResponseEntity<>("User with ID " + id + " not found", HttpStatus.NOT_FOUND);
    }

    // delete user by id
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        Integer result = userService.deleteUser(id);
        if(result == 1) {
            return new ResponseEntity<>("User with ID " + id + " deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("User with ID " + id + " not found", HttpStatus.NOT_FOUND);
    }

    // update user data
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserDTO userDto) {
        User user = userService.updateUser(id, userDto);
        if(user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>("User with ID " + id + " not found", HttpStatus.NOT_FOUND);
    }

}
