package com.mugisius.DailyNoteApi.controllers;

import com.mugisius.DailyNoteApi.data.SecurityUser;
import com.mugisius.DailyNoteApi.data.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/users")
public class UserController {

    private final UserDetailsManager userDetailsManager;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserDetailsManager userDetailsManager, PasswordEncoder passwordEncoder) {
        this.userDetailsManager = userDetailsManager;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping
    public void addUser(
            @RequestBody User user
    ) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        SecurityUser securityUser = new SecurityUser(user);
        userDetailsManager.createUser(securityUser);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public String handleException(IllegalArgumentException e) { return e.getMessage(); }
}
