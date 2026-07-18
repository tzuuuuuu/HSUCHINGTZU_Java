package com.library.back.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "user") // 對應資料表 user
public class User {

    @Id // 主鍵
    @Column(name = "User_id")
    private Integer userId;

    @Column(name = "Phone_Number", unique = true, nullable = false)
    private Integer phoneNumber;

    @Column(name = "Password", length = 20, nullable = false)
    private String password;

    @Column(name = "User_Name", length = 10, nullable = false)
    private String userName;

    @Column(name = "Registration_Time", nullable = false, updatable = false)
    private LocalDateTime registrationTime = LocalDateTime.now();

    @Column(name = "Last_Login_Time", nullable = false)
    private LocalDateTime lastLoginTime = LocalDateTime.now();

    // --- 以下是 Getter 和 Setter ---

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Integer phoneNumber) {
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