package com.itrosys.student_management_system.controller;

import com.itrosys.student_management_system.entity.Users;
import com.itrosys.student_management_system.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signUp")
    public Users register(@RequestBody Users user) {
        return userService.register(user);
    }

    @PostMapping("/signIn")
    public String singIn(@RequestBody Users user) {
        return userService.verify(user);
    }
}
