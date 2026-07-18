package com.library.back.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
@Table(name = "user") // 對應資料表 user
public class User {

    @Id // 主鍵
    @GeneratedValue(strategy = GenerationType.IDENTITY) //關鍵修正：讓資料庫自己遞增 ID (1, 2, 3...)，前端不用傳！
    @Column(name = "User_id")
    private Integer userId;

    @Column(name = "Phone_Number", unique = true, nullable = false)
    private String phoneNumber;

    @Column(name = "Password", length = 20, nullable = false)
    private String password;

    @Column(name = "User_Name", length = 10, nullable = false)
    private String userName;

    @Column(name = "Registration_Time", nullable = false, updatable = false)
    private LocalDateTime registrationTime = LocalDateTime.now();

    /*@Column(name = "Last_Login_Time", nullable = false)
    private LocalDateTime lastLoginTime = LocalDateTime.now();*/
    
 // 🎯 關鍵修正：允許新註冊用戶的最後登入時間暫時為空，或者由後端登入時再寫入
    @Column(name = "Last_Login_Time", nullable = true) 
    private LocalDateTime lastLoginTime;

    // --- 以下是 Getter 和 Setter ---

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public LocalDateTime getRegistrationTime() {
        return registrationTime;
    }

    public void setRegistrationTime(LocalDateTime registrationTime) {
        this.registrationTime = registrationTime;
    }

    public LocalDateTime getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(LocalDateTime lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }
}