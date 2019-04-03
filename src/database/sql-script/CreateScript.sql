-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema edeals
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `high_mountains`;

-- -----------------------------------------------------
-- Schema edeals
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `high_mountains` DEFAULT CHARACTER SET utf8;
USE `high_mountains`;

-- -----------------------------------------------------
-- Table `user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `imageUrl` varchar(200) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `firstName` varchar(100) DEFAULT NULL,
  `middleName` varchar(100) DEFAULT NULL,
  `lastName` varchar(100) DEFAULT NULL,
  `userType` varchar(100) DEFAULT NULL,
  `shopType` varchar(100) DEFAULT NULL,
  `gender` varchar(10) DEFAULT NULL,
  `dob` datetime DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `phone` varchar(100) DEFAULT NULL,
  `password` varchar(200) DEFAULT NULL,
  `lastLogin` datetime DEFAULT NULL,
  `status` varchar(200) DEFAULT NULL,
  `city` varchar(200) DEFAULT NULL,
  `state` varchar(200) DEFAULT NULL,
  `country` varchar(200) DEFAULT NULL,
  `address` varchar(200) DEFAULT NULL,
  `deviceToken` varchar(200) DEFAULT NULL,
  `isLoggedIn` tinyint(4) not null DEFAULT 0,
  `onlineStatus` tinyint(4) not null DEFAULT 0,
  PRIMARY KEY (`id`)
);



