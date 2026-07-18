package com.library.back.repository;

import com.library.back.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    // 對應 User 實體，主鍵 User_id 的型態是 Integer
}