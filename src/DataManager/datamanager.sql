-- phpMyAdmin SQL Dump
-- version 4.4.15.5
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: 2016-04-15 08:20:40
-- 服务器版本： 5.6.21-log
-- PHP Version: 5.4.13

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `datamanager`
--

-- --------------------------------------------------------

--
-- 表的结构 `t_data`
--

CREATE TABLE IF NOT EXISTS `t_data` (
  `data_id` int(11) NOT NULL,
  `item_id` int(11) NOT NULL,
  `content` varchar(1024) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `t_data`
--

INSERT INTO `t_data` (`data_id`, `item_id`, `content`) VALUES
(1, 1, '1'),
(1, 2, '1'),
(1, 3, '1'),
(2, 1, '2'),
(2, 2, '2'),
(2, 3, '2');

-- --------------------------------------------------------

--
-- 表的结构 `t_item`
--

CREATE TABLE IF NOT EXISTS `t_item` (
  `item_id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `order_num` int(11) DEFAULT NULL,
  `maxlength` int(11) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `t_item`
--

INSERT INTO `t_item` (`item_id`, `name`, `type`, `order_num`, `maxlength`) VALUES
(1, '编号', 0, 1, 1000),
(2, '名称', 0, 0, 100),
(3, '字段名', 0, 100, 100),
(5, '字段2', 1, 2, NULL),
(6, '备注', 1, 90, NULL);

-- --------------------------------------------------------

--
-- 表的结构 `t_othername`
--

CREATE TABLE IF NOT EXISTS `t_othername` (
  `othername_id` int(11) NOT NULL,
  `item_id` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `t_othername`
--

INSERT INTO `t_othername` (`othername_id`, `item_id`, `name`) VALUES
(7, 2, ''),
(10, 1, '别名1'),
(11, 1, '别名1'),
(12, 1, '别名2'),
(15, 3, ''),
(16, 6, ''),
(17, 5, '');

-- --------------------------------------------------------

--
-- 表的结构 `t_validate`
--

CREATE TABLE IF NOT EXISTS `t_validate` (
  `validate_id` int(11) NOT NULL,
  `item_id` int(11) DEFAULT NULL,
  `validate_item` varchar(255) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `t_validate`
--

INSERT INTO `t_validate` (`validate_id`, `item_id`, `validate_item`) VALUES
(7, 2, ''),
(10, 1, '验证1'),
(11, 1, '验证1'),
(12, 1, '验证2'),
(15, 3, ''),
(16, 6, ''),
(17, 5, '');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `t_data`
--
ALTER TABLE `t_data`
  ADD PRIMARY KEY (`data_id`,`item_id`),
  ADD KEY `FK_Reference_2` (`item_id`);

--
-- Indexes for table `t_item`
--
ALTER TABLE `t_item`
  ADD PRIMARY KEY (`item_id`),
  ADD KEY `AK_UQ_ItemName` (`name`);

--
-- Indexes for table `t_othername`
--
ALTER TABLE `t_othername`
  ADD PRIMARY KEY (`othername_id`),
  ADD KEY `FK_Reference_1` (`item_id`);

--
-- Indexes for table `t_validate`
--
ALTER TABLE `t_validate`
  ADD PRIMARY KEY (`validate_id`),
  ADD KEY `FK_Reference_4` (`item_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `t_item`
--
ALTER TABLE `t_item`
  MODIFY `item_id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT for table `t_othername`
--
ALTER TABLE `t_othername`
  MODIFY `othername_id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=18;
--
-- AUTO_INCREMENT for table `t_validate`
--
ALTER TABLE `t_validate`
  MODIFY `validate_id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=18;
--
-- 限制导出的表
--

--
-- 限制表 `t_data`
--
ALTER TABLE `t_data`
  ADD CONSTRAINT `FK_Reference_2` FOREIGN KEY (`item_id`) REFERENCES `t_item` (`item_id`);

--
-- 限制表 `t_othername`
--
ALTER TABLE `t_othername`
  ADD CONSTRAINT `FK_Reference_1` FOREIGN KEY (`item_id`) REFERENCES `t_item` (`item_id`);

--
-- 限制表 `t_validate`
--
ALTER TABLE `t_validate`
  ADD CONSTRAINT `FK_Reference_4` FOREIGN KEY (`item_id`) REFERENCES `t_item` (`item_id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
