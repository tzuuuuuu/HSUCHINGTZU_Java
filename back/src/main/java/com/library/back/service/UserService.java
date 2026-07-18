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
    public String loginUser(String phoneNumber, String password) {
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
    
    @Autowired
    private com.library.back.repository.InventoryRepository inventoryRepository;

    @Autowired
    private com.library.back.repository.BorrowingRecordRepository borrowingRecordRepository;

    /**
     * 核心借書功能：同時異動多張表，並開啟 Transaction 交易機制
     */
    @org.springframework.transaction.annotation.Transactional // 👈 就是這個註解！確保交易完整性，防範資料錯亂
    public String borrowBook(Integer userId, Integer inventoryId) {
        
        // 1. 檢查庫存是否存在
        java.util.Optional<com.library.back.entity.Inventory> inventoryOpt = inventoryRepository.findById(inventoryId);
        if (!inventoryOpt.isPresent()) {
            return "借書失敗：找不到該庫存編號！";
        }
        
        com.library.back.entity.Inventory inventory = inventoryOpt.get();
        
        // 2. 檢查書籍是否「在庫」 (如果已經是 出借中、整理中 等，就不能借)
        if (!"在庫".equals(inventory.getStatus())) {
            return "借書失敗：該書籍目前狀態為「" + inventory.getStatus() + "」，無法借閱！";
        }
        
        // 3. 變更書籍狀態為「出借中」
        inventory.setStatus("出借中");
        inventoryRepository.save(inventory); // 更新庫存狀態
        
        // 4. 新增一筆借閱紀錄
        com.library.back.entity.BorrowingRecord record = new com.library.back.entity.BorrowingRecord();
        record.setUserId(userId);
        record.setInventoryId(inventoryId);
        record.setBorrowongTime(java.time.LocalDateTime.now());
        // 剛借書時，還書時間可以先給一個預設值，或是跟著你的 SQL 預設 current_timestamp()
        record.setReturnTime(java.time.LocalDateTime.now()); 
        
        borrowingRecordRepository.save(record); // 寫入紀錄表
        
        return "借書成功！已成功借出庫存編號：" + inventoryId;
    }
}