package com.example.ecommerce.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

	/**
	 * This is temporary controller method to test the app.
	 * @return
	 */
    @GetMapping("/greet")
    public String greetMessage() {
        return "Hi, Good Morning!";
    }
}
