package com.library.back.controller;

import com.library.back.entity.Inventory;
import com.library.back.repository.InventoryRepository;
import com.library.back.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/books") // 統一書籍模組的基礎路徑
public class BookController {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private UserService userService;

    // 🎯 1. 獲取所有庫存狀態（供圖書大廳渲染）
    @GetMapping("/inventory")
    public List<Inventory> getAllInventory() {
        return inventoryRepository.findAll();
    }

    // 🎯 2. 借書接口：接收前端傳來的用戶 ID 與庫存 ID
    @PostMapping("/borrow")
    public String borrowBook(@RequestParam Integer userId, @RequestParam Integer inventoryId) {
        return userService.borrowBook(userId, inventoryId);
    }

    // 🎯 3. 還書接口：接收前端傳來的庫存 ID
    @PostMapping("/return")
    public String returnBook(@RequestParam Integer inventoryId) {
        return userService.returnBook(inventoryId);
    }
}