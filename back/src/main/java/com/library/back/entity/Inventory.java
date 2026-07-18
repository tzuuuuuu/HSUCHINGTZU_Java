package com.library.back.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@Table(name = "inventory")
public class Inventory {

    @Id
    @Column(name = "Inventory_id")
    private Integer inventoryId;

    @Column(name = "ISBN", length = 20, nullable = false)
    private String isbn;
    
    @ManyToOne
    @JoinColumn(name = "ISBN", referencedColumnName = "ISBN", insertable = false, updatable = false)
    private Book book;
    
    @Column(name = "Store_Time", nullable = false)
    private LocalDateTime storeTime = LocalDateTime.now();

    @Column(name = "Status", nullable = false)
    private String status = "在庫";
    
 // --- Getter 和 Setter ---
    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Integer getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Integer inventoryId) {
        this.inventoryId = inventoryId;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public LocalDateTime getStoreTime() {
        return storeTime;
    }

    public void setStoreTime(LocalDateTime storeTime) {
        this.storeTime = storeTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}