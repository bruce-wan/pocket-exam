package com.catalpa.pocket.controller;


import com.catalpa.pocket.model.UserData;
import com.catalpa.pocket.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2018-10-23
 */
@Log4j2
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserData createUser(@RequestBody UserData userData) throws UnsupportedEncodingException {
        return userService.createUser(userData);
    }
}

