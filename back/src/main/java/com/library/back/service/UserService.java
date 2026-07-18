package com.library.back.service;

import com.library.back.entity.User;
import com.library.back.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * 處理使用者註冊邏輯
     */
    public String registerUser(User user) {
        // 1. 檢查手機號碼是否已經存在 (因為你的資料庫 Phone_Number 是 UNIQUE)
        // 注意：這裡我們需要去 Repository 額外寫一個找手機的方法，我們先用虛擬碼邏輯，等一下去 Repository 補上
        
        // 為了不讓畫面現在噴錯，我們直接來幫 Repository 加上自訂查詢！
        // 我們先做基礎儲存，等一下帶你回 Repository 補齊手機查詢。
        
        try {
            userRepository.save(user);
            return "註冊成功！";
        } catch (Exception e) {
            return "註冊失敗，可能原因：手機號碼已重複或資料格式錯誤。";
        }
    }
}