package com.catalpa.pocket.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.catalpa.pocket.entity.Platform;
import com.catalpa.pocket.model.ExamData;
import com.catalpa.pocket.model.UserData;
import com.catalpa.pocket.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

/**
 * Created by wanchuan01 on 2018/10/23.
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

    @GetMapping("/{userId}")
    public UserData getUserById(@PathVariable Long userId) {
        return userService.getUserById(userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserData createUser(@RequestAttribute Platform platform, @RequestBody UserData userData) throws UnsupportedEncodingException {
        return userService.createUser(platform.getPlatformId(), userData);
    }

    @GetMapping("/{userId}/exams")
    public Page<ExamData> getUserExams(@RequestAttribute Platform platform,
                                       @PathVariable Long userId,
                                       @RequestParam(required = false) Integer catalog,
                                       @RequestParam(required = false) Integer level,
                                       @RequestParam(defaultValue = "30") Integer days,
                                       @RequestParam(defaultValue = "1") Integer pageNum,
                                       @RequestParam(defaultValue = "10") Integer pageSize) {

        return userService.getUserExams(new Page<>(pageNum, pageSize), platform, userId, catalog, level, days);
    }

    @PostMapping("/{userId}/exams")
    @ResponseStatus(HttpStatus.CREATED)
    public ExamData addUserExams(@RequestAttribute Platform platform, @PathVariable Long userId, @RequestBody ExamData examData) {
        return userService.addUserExams(platform, userId, examData);
    }
}

