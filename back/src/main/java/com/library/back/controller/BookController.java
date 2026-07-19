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
    
    @Autowired
    private com.library.back.repository.BorrowingRecordRepository borrowingRecordRepository;

    @GetMapping("/inventory")
    public List<Inventory> getAllInventory() {
        List<Inventory> inventories = inventoryRepository.findAll();
        List<com.library.back.entity.BorrowingRecord> allRecords = borrowingRecordRepository.findAll();

        // 🎯 動態配對：幫每一本出借中的書，找出是哪個 User 借走的
        for (Inventory inv : inventories) {
            if ("出借中".equals(inv.getStatus())) {
                for (com.library.back.entity.BorrowingRecord record : allRecords) {
                    // 如果紀錄對應這本書，且尚未真正歸還（借書時間與還書時間相同，代表還沒更新還書時間）
                    if (record.getInventoryId().equals(inv.getInventoryId()) && 
                        record.getBorrowongTime().isEqual(record.getReturnTime())) {
                        
                        // 把借閱者的 User_id 填進去！
                        inv.setCurrentBorrowerId(record.getUserId());
                        break;
                    }
                }
            }
        }
        return inventories;
    }
    /*
    // 🎯 1. 獲取所有庫存狀態（供圖書大廳渲染）
    @GetMapping("/inventory")
    public List<Inventory> getAllInventory() {
        return inventoryRepository.findAll();
    }*/

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