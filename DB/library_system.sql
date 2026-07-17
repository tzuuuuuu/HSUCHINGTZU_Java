-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- 主機： 127.0.0.1
-- 產生時間： 2026-07-18 00:56:02
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

-- --------------------------------------------------------

--
-- 資料表結構 `borrowing_record`
--

CREATE TABLE `borrowing_record` (
  `User_id` int(10) NOT NULL,
  `Inventory_id` int(10) NOT NULL,
  `Borrowong_Time` datetime NOT NULL DEFAULT current_timestamp(),
  `Return_Time` datetime NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

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

-- --------------------------------------------------------

--
-- 資料表結構 `user`
--

CREATE TABLE `user` (
  `User_id` int(10) NOT NULL,
  `Phone_Number` int(10) NOT NULL,
  `Password` varchar(20) NOT NULL,
  `User_Name` varchar(10) NOT NULL,
  `Registration_Time` datetime NOT NULL DEFAULT current_timestamp(),
  `Last_Login_Time` datetime NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

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
  ADD KEY `ISBN` (`ISBN`);

--
-- 資料表索引 `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`User_id`),
  ADD UNIQUE KEY `Phone_Number` (`Phone_Number`);

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
