package com.library.back.service;

import com.library.back.entity.User;
import com.library.back.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * 處理使用者註冊邏輯
     */
    public String registerUser(User user) {
    	// 1. 先用前端傳來的手機號碼，去資料庫查看看有沒有這個人
        Optional<User> existingUser = userRepository.findByPhoneNumber(user.getPhoneNumber());
        
        // 2. 如果找到了，代表手機號碼被註冊過了，直接攔截並退回！
        if (existingUser.isPresent()) {
            return "註冊失敗：該手機號碼已被註冊！";
        }
        
        try {
            userRepository.save(user);
            return "註冊成功！";
        } catch (Exception e) {
            return "註冊失敗，可能原因：手機號碼已重複或資料格式錯誤。";
        }
    }
}