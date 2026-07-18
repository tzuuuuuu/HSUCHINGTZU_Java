package com.library.back.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "book") // 1. 這裡精準對應你的資料表名稱 book (單數)
public class Book {

    @Id // 2. 指定 ISBN 是這張表的主鍵
    @Column(name = "ISBN", length = 20)
    private String isbn;

    @Column(name = "Name", length = 20, nullable = false)
    private String name;

    @Column(name = "Author", length = 10, nullable = false)
    private String author;

    @Column(name = "Introduction", nullable = false, columnDefinition = "TEXT")
    private String introduction;

    // --- 以下是 Getter 和 Setter ---

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
}