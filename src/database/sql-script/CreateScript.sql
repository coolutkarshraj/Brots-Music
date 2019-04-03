-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema edeals
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `edeals`;

-- -----------------------------------------------------
-- Schema edeals
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `edeals` DEFAULT CHARACTER SET utf8;
USE `edeals`;

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

-- -----------------------------------------------------
-- Table `category`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

-- -----------------------------------------------------
-- Table `product`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `brandId` bigint(20) NOT NULL,
  `categoryId` bigint(20) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `imageUrl` varchar(100) DEFAULT NULL,
  `type` varchar(100) DEFAULT NULL,
  `mrp` double DEFAULT 0.0,
  `price` double DEFAULT 0.0,
  `discountRate` double Default 0.0,
  `mfgDate` datetime DEFAULT NULL,
  `expDate` datetime DEFAULT NULL,
  `color` varchar(200) DEFAULT NULL,
  `smell` varchar(200) DEFAULT NULL,
  `taste` varchar(200) DEFAULT NULL,
  `composition` varchar(10000) DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`brandId`) references `brand`(`id`),
  FOREIGN KEY (`categoryId`) references `category`(`id`)
);

-- -----------------------------------------------------
-- Table `brand`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `brand`;
CREATE TABLE `brand` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

-- -----------------------------------------------------
-- Table `products`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `products`;
CREATE TABLE `products` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `productId` bigint(20) NOT NULL,
  `userId` bigint(20) NOT NULL,
  `quantity` varchar(10) DEFAULT NULL,
  `weight` double DEFAULT 0.0,
  `volume` double DEFAULT 0.0,
  `size` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`productId`) references `product`(`id`),
  FOREIGN KEY (`userId`) references `user`(`id`)
);

-- -----------------------------------------------------
-- Table `orders`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `consumerId` bigint(20) NOT NULL,
  `distributorId` bigint(20) NOT NULL,
  `productId` bigint(20) NOT NULL,
  `quantity` varchar(10) DEFAULT NULL,
  `weight` double DEFAULT 0.0,
  `volume` double DEFAULT 0.0,
  `size` varchar(100) DEFAULT NULL,
  `status` tinyint(4) DEFAULT 0,
  `date` datetime DEFAULT now(),
  `price` double not null default 0,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`productId`) references `product`(`id`),
  FOREIGN KEY (`consumerId`) references `user`(`id`),
  FOREIGN KEY (`distributorId`) references `user`(`id`)
);

-- -----------------------------------------------------
-- Table `inbox`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `inbox`;
CREATE TABLE `inbox` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `senderId` bigint(20) NOT NULL,
  `receiverId` bigint(20) NOT NULL,
  `orderId` bigint(20) NULL,
  `title` varchar(300) DEFAULT NULL,
  `message` varchar(3000) DEFAULT NULL,
  `type` varchar(100) DEFAULT NULL,
  `isVisible` tinyInt(4) DEFAULT NULL,
  `status` tinyInt(4) DEFAULT NULL,
  `date` datetime default now(),
  PRIMARY KEY (`id`),
  FOREIGN KEY (`receiverId`) references `user`(`id`),
  FOREIGN KEY (`orderId`) references `orders`(`id`),
  FOREIGN KEY (`senderId`) references `user`(`id`));
