package com.library.back.controller;

import com.library.back.entity.Book;
import com.library.back.entity.User;
import com.library.back.repository.BookRepository;
import com.library.back.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {
	
	@Autowired
    private BookRepository bookRepository; // 注入剛剛做好的倉庫
	
	@Autowired
    private UserService userService; // 注入剛剛做好的使用者業務層

    // 當我們在瀏覽器輸入 http://localhost:8050/books 時會觸發
    @GetMapping("/books")
    public List<Book> getAllBooks() {
        return bookRepository.findAll(); // 自動去資料庫撈出所有的書，並轉成 JSON 格式倒給網頁
    }
    
 // 🔥 新增的測試功能：只要在瀏覽器輸入這個網址，就會幫我們自動註冊一個王小明！
    @GetMapping("/test-register")
    public String testRegister() {
        User mockUser = new User();
        mockUser.setUserId(1);
        mockUser.setPhoneNumber(912345678);
        mockUser.setPassword("myPassword123");
        mockUser.setUserName("王小明");
        
        return userService.registerUser(mockUser);
    }
    
	/*
	@GetMapping("/hello")
    public String sayHello() {
        return "後端基地連線成功！圖書管理系統準備就緒！";
        
    }*/

}
