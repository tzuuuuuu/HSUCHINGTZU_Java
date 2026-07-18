package com.library.back.repository;

import com.library.back.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    // 對應 User 實體，主鍵 User_id 的型態是 Integer
	
	// 只要寫出這個名字，JPA 就會自動幫你生成 SQL: SELECT * FROM user WHERE Phone_Number = ?
    // 並且會自動防範 SQL Injection！
    Optional<User> findByPhoneNumber(String phoneNumber);
}