package com.library.back.controller;

import com.library.back.entity.User;
import com.library.back.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users") // 統一這個 Controller 的基礎網址
public class UserController {

    @Autowired
    private UserService userService;

    // 註冊接口：因為要接收前端傳來的表單資料，要用 Post 協定
    @PostMapping("/register")
    public String register(@RequestBody User user) {
        return userService.registerUser(user);   
    }
    
    
}