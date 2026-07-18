package com.library.back.controller;

import com.library.back.entity.Book;
import com.library.back.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {
	
	@Autowired
    private BookRepository bookRepository; // 注入剛剛做好的倉庫

    // 當我們在瀏覽器輸入 http://localhost:8050/books 時會觸發
    @GetMapping("/books")
    public List<Book> getAllBooks() {
        return bookRepository.findAll(); // 自動去資料庫撈出所有的書，並轉成 JSON 格式倒給網頁
	
	/*
	@GetMapping("/hello")
    public String sayHello() {
        return "後端基地連線成功！圖書管理系統準備就緒！";
        */
    }

}
