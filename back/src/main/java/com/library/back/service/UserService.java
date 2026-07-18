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
    
    //處理使用者登入驗證邏輯
    public String loginUser(Integer phoneNumber, String password) {
        // 1. 先用手機號碼去資料庫找人
        Optional<User> userOpt = userRepository.findByPhoneNumber(phoneNumber);
        
        // 2. 如果根本找不到這個手機號碼
        if (!userOpt.isPresent()) {
            return "登入失敗：該手機號碼尚未註冊！";
        }
        
        User user = userOpt.get();
        
        // 3. 比對密碼是否正確 (注意：字串比對在 Java 要用 .equals())
        if (!user.getPassword().equals(password)) {
            return "登入失敗：密碼錯誤！";
        }
        
        // 4. 密碼正確！更新最後登入時間 (Last_Login_Time)
        user.setLastLoginTime(java.time.LocalDateTime.now());
        userRepository.save(user); // 儲存更新時間
        
        return "登入成功！歡迎回來，" + user.getUserName() + " 🚀";
    }
}