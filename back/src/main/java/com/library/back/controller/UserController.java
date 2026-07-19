package com.library.back.controller;

import com.library.back.entity.User;
import com.library.back.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import java.util.Map;
import java.util.HashMap;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/users") // 統一這個 Controller 的基礎網址
public class UserController {

    @Autowired
    private UserService userService;

    // 註冊接口：因為要接收前端傳來的表單資料，要用 Post 協定
    @PostMapping("/register")
    public String register(@RequestBody User user) {
        return userService.registerUser(user);
    }
 // 正式登入接口：改用 POST 協定隱藏密碼，並接收前端傳來的 JSON 物件
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        // 呼叫 Service 尋找使用者
        User loggedInUser = userService.loginUserObject(user.getPhoneNumber(), user.getPassword());
        
        Map<String, Object> response = new HashMap<>();
        if (loggedInUser != null) {
            response.put("status", "success");
            response.put("message", "登入成功！歡迎回來，" + loggedInUser.getUserName());
            response.put("userId", loggedInUser.getUserId()); // 👈 關鍵！把真實的 ID 送給前端
            return ResponseEntity.ok(response);
        } else {
            response.put("status", "fail");
            response.put("message", "手機號碼或密碼錯誤，登入失敗！");
            return ResponseEntity.status(401).body(response);
        }
    }
    /*
    public String login(@RequestBody User user) {
        // 從傳進來的 user 物件中取出前端輸入的手機號碼與密碼
        return userService.loginUser(user.getPhoneNumber(), user.getPassword());
    }*/
    
}