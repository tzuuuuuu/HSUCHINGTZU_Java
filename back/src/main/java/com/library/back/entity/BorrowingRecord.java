package com.library.back.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "borrowing_record")
public class BorrowingRecord {

    @Column(name = "User_id", nullable = false)
    private Integer userId;

    @Id // 暫時讓 JPA 當作識別證過關
    @Column(name = "Inventory_id", nullable = false)
    private Integer inventoryId;

    @Column(name = "Borrowong_Time", nullable = false)
    private LocalDateTime borrowongTime = LocalDateTime.now();

    @Column(name = "Return_Time", nullable = true)
    private LocalDateTime returnTime;

    // --- Getter and Setter ---

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Integer inventoryId) {
        this.inventoryId = inventoryId;
    }

    public LocalDateTime getBorrowongTime() {
        return borrowongTime;
    }

    public void setBorrowongTime(LocalDateTime borrowongTime) {
        this.borrowongTime = borrowongTime;
    }

    public LocalDateTime getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(LocalDateTime returnTime) {
        this.returnTime = returnTime;
    }
}