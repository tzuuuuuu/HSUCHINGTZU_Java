package com.library.back.repository;

import com.library.back.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {
    // 這裡繼承了 JpaRepository，並指定對應的實體是 Book，主鍵型態是 String (也就是 ISBN)
    // 這樣它就自動擁有 findAll(), findById(), save(), delete() 等所有功能了！
}
