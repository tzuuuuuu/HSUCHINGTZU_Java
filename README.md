# HSUCHINGTZU_Java

## 圖書借閱系統
### 1.運用到的工具與環境

本專案在開發與運行過程中，由前端、後端、資料庫與運行環境交織而成，以下為整體專案運作所使用的核心工具與環境配置：

#### 核心開發工具與本地伺服器
* **Eclipse IDE**：後端 Java / Spring Boot 的主力開發環境。負責編譯專案、撰寫 Service 業務邏輯、以及管理 JpaRepository
* **Visual Studio Code (VS Code)**：前端 Vue 3 的專案開發環境。提供極佳的 Markdown 撰寫與 Vue 組合式 API 語法高亮支援
* **XAMPP**：本地端伺服器整合軟體。專案主要啟用其 **Apache**（作為網頁伺服器基礎）與 **MySQL**（作為本系統底層的關係型資料庫系統)

#### 運行環境與套件管理器
* **Node.js & npm**：前端 Vue 3 運行的核心環境。利用 npm 進行前端第三方套件（如 Axios、Vue Router）的下載、依賴管理與 Vite 開發伺服器的打包建構
* **Java Development Kit (JDK 17 或更新版本)**：後端 Spring Boot 3 賴以生存的運行環境，提供 Java 編譯器、JVM（Java 虛擬機）及核心類別庫
* **Apache Maven**：後端專案的專案管理與建構自動化工具（Eclipse 內建整合）。透過 `pom.xml` 自動下載並管理 Spring Data JPA、MySQL Connector 等繁複的後端依賴套件

#### 資料庫管理
* **phpMyAdmin**：隨 XAMPP 附帶的網頁版 MySQL 圖形化管理介面。在本專案中扮演資料核心角色，用於建立 `library` 資料庫、設計三張關鍵資料表的欄位架構、勾選正統的 `NULL` 空值約束，以及直接進行測試書籍與新帳號的數據灌入

### 2.資料庫架構設計

本系統底層採用 MySQL 關係型資料庫，設計了三張核心資料表

#### 用戶資料表 (`user`)
儲存系統註冊會員的基本憑證與登入軌跡。

| 欄位名稱 (Column) | 資料型態 (Type) | 屬性 (Attributes) | 說明 (Description) |
| :--- | :--- | :--- | :--- |
| `User_Id` | INT | Primary Key, AI | 用戶唯一識別代號（自動遞增） |
| `Phone_Number` | VARCHAR(20) | Unique, NOT NULL | 登入帳號（手機號碼，不可重複） |
| `Password` | VARCHAR(100) | NOT NULL | 登入密碼 |
| `User_Name` | VARCHAR(50) | NOT NULL | 使用者真實姓名 |
| `Last_Login_Time`| DATETIME | NULL | 最後登入系統時間 |

#### 書籍基本資料表 (`book`)
儲存書籍的純文字基本資訊（不含館藏複本數）。

| 欄位名稱 (Column) | 資料型態 (Type) | 屬性 (Attributes) | 說明 (Description) |
| :--- | :--- | :--- | :--- |
| `ISBN` | VARCHAR(20) | Primary Key | 國際標準書號（書籍唯一識別碼） |
| `Name` | VARCHAR(20) | NOT NULL | 書籍名稱 |
| `Author` | VARCHAR(10) | NOT NULL | 書籍作者 |
| `Introduction` | TEXT | NOT NULL | 內容說明 |

#### 圖書庫存與借閱狀態表 (`inventory`)
儲存圖書館內實體館藏的在架狀態，並與 `book` 表形成多對一（Many-to-One）關聯。

| 欄位名稱 (Column) | 資料型態 (Type) | 屬性 (Attributes) | 說明 (Description) |
| :--- | :--- | :--- | :--- |
| `Inventory_Id` | INT | Primary Key | 實體書在庫庫存編號 |
| `ISBN` | VARCHAR(20) | Foreign Key | 外鍵，對齊 `book.ISBN` 以動態撈取書名 |
| `Store_Time` | DATETIME | DEFAULT CURRENT_TIMESTAMP | 書籍入庫時間 |
| `Status` | VARCHAR(20) | DEFAULT '在庫' | 目前館藏狀態（'在庫' / '出借中'） |

### 借閱紀錄表 (`borrowing_record`)
記錄所有借書與還書的交易歷史帳目，用於權限防盜與還書時間戳記追蹤。

| 欄位名稱 (Column) | 資料型態 (Type) | 屬性 (Attributes) | 說明 (Description) |
| :--- | :--- | :--- | :--- |
| `Inventory_Id` | INT | Primary Key | 庫存編號（作為主鍵連結該實體書的當前紀錄） |
| `User_Id` | INT | Foreign Key | 借閱者 ID（對齊 `user.User_Id`） |
| `Borrowong_Time` | DATETIME | NOT NULL | 真實借書觸發時間點 |
| `Return_Time` | DATETIME | **DEFAULT NULL** | 歸還時間。**未還時為 NULL**，還書後才動態更新為當前時間。 |

### 3.後端架構及介紹
```text
src/main/java
└── com.library.back
    ├── BackApplication.java
    ├── config
    │   └── CorsConfig.java
    ├── controller
    │   ├── BookController.java
    │   ├── TestController.java
    │   └── UserController.java
    ├── entity
    │   ├── Book.java
    │   ├── BorrowingRecord.java
    │   ├── Inventory.java
    │   └── User.java
    ├── repository
    │   ├── BookRepository.java
    │   ├── BorrowingRecordRepository.java
    │   ├── InventoryRepository.java
    │   └── UserRepository.java
    └── service
        └── UserService.java
```
- **main**
  - `BackApplication.java`
    - 整個 Spring Boot 專案的進入點 (Entry Point)，負責載入所有的 IOC 容器設定並啟動內嵌的 Tomcat 伺服器
- **`config`**
  - `CorsConfig.java`
    - 全域跨域資源共享 (CORS) 配置檔案，負責開啟並允許前端 http://localhost:5173 進行非同步 API 請求，解除瀏覽器的同源政策限制。
- **`controller`**
  - `BookController.java`
    - 處理圖書大廳的所有資料發送與操作請求，包含動態運算並取得完整的庫存明細清單
  - `TestController.java`
    - 測試驗證後端基本連線與環境是否正常
  - `UserController.java`
    - 處理用戶端所有關於會員的請求，包含提供用戶註冊、以及處理回傳自定義 JSON 物件與識別 ID 的動態登入邏輯
- **`entity`**
  - `Book.java`
    - 顯示`book`資料表，儲存書籍基本資訊（ISBN、書名、作者）
  - `BorrowingRecord.java`
    - 顯示`borrowing_record`借閱紀錄表，負責儲存當前的借閱人、庫存編號以及借還時間
  - `Inventory.java`
    - 顯示`inventory`資料表，並透過 @Transient 建立不在資料庫產生實體欄位的虛擬欄位，供即時記憶體計算使用
  - `User.java`
    - 顯示`user`資料表，儲存使用者基本資料與最後登入時間
- **`repository`** : 讓系統無需編寫複雜的 SQL 語法即可自動擁有資料庫新增、修改、刪除、與查詢能力
  - `BookRepository.java`
  - `BorrowingRecordRepository.java`
  - `InventoryRepository.java`
  - `UserRepository.java`
- **`service`**
  - `UserService.java`
    - 負責使用者註冊及登入密碼驗證、借還書、庫存狀態調整

### 4.前端架構與介紹
```text
src
├── App.vue
├── router
│   └── index.js
└── views
    ├── BookListView.vue
    ├── LoginView.vue
    └── RegisterView.vue
```

  - `App.vue`
    - 前端應用的主入口組件。作為一個單頁應用 (SPA) 的大容器，內嵌 `<router-view />` 用於動態渲染當前路由對應的頁面組件
- `router`
  - `index.js`
    - 前端路由配置中心。負責定義網址路徑與組件的映射關係（例如 `/` 對應登入頁、`/books` 對應大廳頁），控制頁面跳轉的核心邏輯
- `views`
  - `BookListView.vue`
    - 書籍清單頁，可供使用者執行借書還書功能，可登出並連至登入頁面
  - `LoginView.vue`
    - 使用者登入頁面，輸入電話及密碼，登入成功後連至書籍清單頁
  - `RegisterView.vue`
    - 使用者註冊頁面，輸入想註冊的電話號碼、姓名及密碼，註冊成功後連至登入頁面
   
### 5.操作說明

請按照以下步驟依序配置並啟動系統，即可在本地環境順利運行：

1. **啟動本地環境與資料庫伺服器**
   * 開啟 **XAMPP** 控制面板。
   * 將 **Apache** 及 **MySQL** 皆點擊 **【Start】** 啟動。
   * 點擊 MySQL 旁的 **【Admin】** 按鈕，進入 phpMyAdmin 資料庫管理頁面。

2. **匯入資料庫結構與初始數據**
   * 在 phpMyAdmin 中建立一個名為 `library` 的新資料庫。
   * 點選上方的 **「匯入 (Import)」** 功能。
   * 選擇本專案 `/DB` 資料夾底下的 `.sql` 資料庫備份檔案，並點擊執行匯入。

3. **匯入並開啟後端專案**
   * 使用 **Eclipse IDE** 開啟並匯入名為 `back` 的後端專案資料夾。

4. **啟動後端 Spring Boot 服務**
   * 在專案目錄中找到 **`BackApplication.java`**。
   * 點擊右鍵 ➔ **Run As** ➔ **Java Application** 啟動服務。
   * 當主控台 (Console) 刷出以下日誌，即代表後端順利啟動成功：
   
   ![後端啟動成功畫面](https://github.com/user-attachments/assets/e3bcc42b-952b-40e7-835b-e867441f7eb9)

5. **開啟前端專案**
   * 使用 **VS Code (Visual Studio Code)** 開啟名為 `front` 的前端專案資料夾。

6. **安裝依賴並啟動前端服務**
   * 在 VS Code 中開啟內建終端機 (Terminal)。
   * 輸入以下指令啟動 Vite 開發伺服器（若首次執行可先輸入 `npm install`）：
     ```bash
     npm run dev
     ```
   * 當終端機看見以下綠色網址提示，即代表前端服務啟動成功：
   
   ![前端啟動成功畫面](https://github.com/user-attachments/assets/3cd50f30-6275-4576-82f1-2ae2c95dfe2b)

7. **瀏覽器存取與操作測試**
   * 打開瀏覽器，在網址列輸入：[http://localhost:5173/](http://localhost:5173/)。
   * 系統將會自動引導進入**會員登入頁面**，此時即可開始進行動態登入、智慧借還書等完整功能操作。
  
### 6.成果展示
- 會員登入頁面
  - 輸入電話及密碼登入
  ![會員登入頁面](https://github.com/user-attachments/assets/cac16e57-c33c-4b29-96ad-49c0ad1c58a9)

- 註冊畫面
  - 若您還沒有帳號，可在此輸入電話姓名及密碼進行註冊，註冊成功後回直接進入會員登入頁面可進行登入
  ![書籍庫存不同型態](https://github.com/user-attachments/assets/1eb2334d-e9b7-4d37-9873-48afb11b3730)

- 書籍清單頁面
  -可進行借書還書及登出的操作
  ![書籍清單頁面](https://github.com/user-attachments/assets/b5fa1597-a00e-42e4-8c0c-6313b24cd2da)

- 書籍庫存不同型態
  - 若已有其他使用者借出，該書籍會顯示已借出且不能操作，若有綠色按鈕表示該書籍可以借閱，若為黃色按鈕表示你已借閱，可以歸還
  ![書籍庫存不同型態](https://github.com/user-attachments/assets/c8cb9c0c-e86c-4690-83b0-3dc8839c0039)





