-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- 主機： 127.0.0.1
-- 產生時間： 2026-07-19 13:20:36
-- 伺服器版本： 10.4.28-MariaDB
-- PHP 版本： 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- 資料庫： `library_system`
--

-- --------------------------------------------------------

--
-- 資料表結構 `book`
--

CREATE TABLE `book` (
  `ISBN` varchar(20) NOT NULL,
  `Name` varchar(20) NOT NULL,
  `Author` varchar(10) NOT NULL,
  `Introduction` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- 傾印資料表的資料 `book`
--

INSERT INTO `book` (`ISBN`, `Name`, `Author`, `Introduction`) VALUES
('9786263538610', '一千種藍', '千先蘭', '在疾速奔馳、沒有誰願意為誰停留的世界，讓我們開始練習緩．速．前．行'),
('9789571279753', '一次讀懂商業經典', '湯姆巴特勒', '集結五十本有趣的理論、真實的案例以及發人深省的商業書籍'),
('9789577415332', '在樹下傳達神諭的貓', '青山美智子', '一旦察覺神諭的意義，頓時感覺整顆心變得穩暖輕盈'),
('9789864540563', '新制多益單字大全', 'DavidCho', '30天激增300分的多益字彙'),
('9789869537827', '小王子', '翁端聖戴克思修伯里', '真正重要的東西，眼睛是看不見的。');

-- --------------------------------------------------------

--
-- 資料表結構 `borrowing_record`
--

CREATE TABLE `borrowing_record` (
  `User_id` int(10) NOT NULL,
  `Inventory_id` int(10) NOT NULL,
  `Borrowong_Time` datetime NOT NULL DEFAULT current_timestamp(),
  `Return_Time` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- 傾印資料表的資料 `borrowing_record`
--

INSERT INTO `borrowing_record` (`User_id`, `Inventory_id`, `Borrowong_Time`, `Return_Time`) VALUES
(3, 101, '2026-07-19 05:13:52', NULL),
(4, 102, '2026-07-19 04:45:50', '2026-07-19 05:11:48'),
(5, 105, '2026-07-19 05:10:28', '2026-07-19 05:11:04'),
(5, 104, '2026-07-19 05:10:10', '2026-07-19 05:11:03'),
(2, 103, '2026-07-19 05:14:53', '2026-07-19 05:15:08');

-- --------------------------------------------------------

--
-- 資料表結構 `inventory`
--

CREATE TABLE `inventory` (
  `Inventory_id` int(10) NOT NULL,
  `ISBN` varchar(20) NOT NULL,
  `Store_Time` datetime NOT NULL DEFAULT current_timestamp(),
  `Status` enum('在庫','出借中','整理中','遺失','毀損','廢棄') NOT NULL DEFAULT '在庫'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- 傾印資料表的資料 `inventory`
--

INSERT INTO `inventory` (`Inventory_id`, `ISBN`, `Store_Time`, `Status`) VALUES
(101, '9789571279753', '2026-07-18 20:43:33', '出借中'),
(102, '9789577415332', '2026-07-19 01:08:12', '在庫'),
(103, '9789869537827', '2026-07-19 12:55:54', '在庫'),
(104, '9786263538610', '2026-07-19 13:00:49', '在庫'),
(105, '9789864540563', '2026-07-19 13:05:34', '在庫');

-- --------------------------------------------------------

--
-- 資料表結構 `user`
--

CREATE TABLE `user` (
  `User_id` int(10) NOT NULL,
  `Phone_Number` varchar(10) NOT NULL,
  `Password` varchar(20) NOT NULL,
  `User_Name` varchar(10) NOT NULL,
  `Registration_Time` datetime NOT NULL DEFAULT current_timestamp(),
  `Last_Login_Time` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- 傾印資料表的資料 `user`
--

INSERT INTO `user` (`User_id`, `Phone_Number`, `Password`, `User_Name`, `Registration_Time`, `Last_Login_Time`) VALUES
(1, '0912345678', 'myPassword123', '王小明', '2026-07-18 13:00:11', '2026-07-19 05:05:57'),
(2, '0900000000', 'test123', 'test', '2026-07-18 16:38:03', '2026-07-19 05:14:34'),
(3, '0903679605', '111213011', '徐', '2026-07-19 02:27:56', '2026-07-19 05:13:36'),
(4, '0912123123', '12345', 'Emily', '2026-07-19 04:45:35', '2026-07-19 05:11:45'),
(5, '0941111177', '411411', 'Ka', '2026-07-19 05:09:58', '2026-07-19 05:11:00'),
(6, '0966555444', '654', 'tzu', '2026-07-19 05:17:15', NULL);

--
-- 已傾印資料表的索引
--

--
-- 資料表索引 `book`
--
ALTER TABLE `book`
  ADD PRIMARY KEY (`ISBN`);

--
-- 資料表索引 `borrowing_record`
--
ALTER TABLE `borrowing_record`
  ADD KEY `User_id` (`User_id`),
  ADD KEY `Inventory_id` (`Inventory_id`);

--
-- 資料表索引 `inventory`
--
ALTER TABLE `inventory`
  ADD PRIMARY KEY (`Inventory_id`),
  ADD KEY `ISBN` (`ISBN`);

--
-- 資料表索引 `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`User_id`),
  ADD UNIQUE KEY `Phone_Number` (`Phone_Number`);

--
-- 在傾印的資料表使用自動遞增(AUTO_INCREMENT)
--

--
-- 使用資料表自動遞增(AUTO_INCREMENT) `user`
--
ALTER TABLE `user`
  MODIFY `User_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- 已傾印資料表的限制式
--

--
-- 資料表的限制式 `inventory`
--
ALTER TABLE `inventory`
  ADD CONSTRAINT `inventory_ibfk_1` FOREIGN KEY (`ISBN`) REFERENCES `book` (`ISBN`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
