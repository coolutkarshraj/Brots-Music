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
-- -----------------------------------------------------
-- Table `user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `imageUrl` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `firstName` varchar(255) DEFAULT NULL,
  `middleName` varchar(255) DEFAULT NULL,
  `lastName` varchar(255) DEFAULT NULL,
  `userName` varchar(255) DEFAULT NULL,
  `referalCode` char(10) DEFAULT NULL,
  `gender` smallint(2) DEFAULT NULL,
  `dob` datetime DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `lastLogin` datetime DEFAULT NULL,
  `status` smallint(2) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `about` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `userStatus` varchar(255) DEFAULT NULL,
  `languages` varchar(255) DEFAULT NULL,
  `deviceToken` varchar(255) DEFAULT NULL,
  `isLoggedIn` tinyint(2) not null DEFAULT 0,
  `onlineStatus` tinyint(2) not null DEFAULT 0,
  `interstedIn` tinyint(2) not null DEFAULT 0,
  `adhar_number` varchar(255) DEFAULT NULL,
  `followers` int(11) DEFAULT NULL,
  `following` int(11) DEFAULT NULL,
  `level` int(11) DEFAULT NULL,
  `isBloodDonated` varchar(255) DEFAULT NULL,
  `isVolunterinComunityServices` varchar(255) DEFAULT NULL,
  `wantToHelpPeople` varchar(255) DEFAULT NULL,
   PRIMARY KEY (`id`),
   UNIQUE (`email`),
   unique(`userName`),
   unique(`phone`)
);

-- -----------------------------------------------------
-- Table user Profile Places,Education,
-- -----------------------------------------------------

DROP TABLE IF EXISTS `userProfilePlace`;
CREATE TABLE `userProfilePlaace` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `userId` int(11) not NULL,
  `title` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
   FOREIGN KEY fk_user_id(`userId`)
   REFERENCES user(`id`)
   ON UPDATE CASCADE
   ON DELETE RESTRICT
);

DROP TABLE IF EXISTS `userProfileeducation`;
CREATE TABLE `userProfileeducation` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `userId` int(11) not NULL,
  `educationTitle` varchar(255) DEFAULT NULL,
  `educationDescription` varchar(255) DEFAULT NULL,
   PRIMARY KEY (`id`),
   FOREIGN KEY fk_user_id(`userId`)
   REFERENCES user(`id`)
   ON UPDATE CASCADE
   ON DELETE RESTRICT
);

DROP TABLE IF EXISTS `userProfilepublicTag`;
CREATE TABLE `userProfilepublicTag` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `userId` int(11) not NULL,
  `publictagTitle` varchar(255) DEFAULT NULL,
  `publictagDescription` varchar(255) DEFAULT NULL,
  `publictagrating` smallint(2) DEFAULT NULL,
   PRIMARY KEY (`id`),
   FOREIGN KEY fk_user_id(`userId`)
   REFERENCES user(`id`)
   ON UPDATE CASCADE
   ON DELETE RESTRICT
);
-- -----------------------------------------------------
-- Table `World Places List known as SpringField Like Country,Continent,State`
-- -----------------------------------------------------

DROP TABLE IF EXISTS `springField`;
CREATE TABLE `springField` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `imageUrl` varchar(200) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `about` varchar(350) DEFAULT NULL,
  `description` varchar(350) DEFAULT NULL,
 `followers` varchar(200) DEFAULT NULL,
 `following` varchar(200) DEFAULT NULL,
 `StarrLevel` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)

);

-- -----------------------------------------------------
-- Table ` List known as continent `
-- -----------------------------------------------------

DROP TABLE IF EXISTS `continents`; 
CREATE TABLE `continents`(
`id` int(11) NOT NULL auto_increment,
`continents_name` varchar(100) NOT NULL default '',
`continents_image` varchar(200) Not NULL default '',
`Star` varchar(5) NOT NULL default '',
`continentsStatus` varchar(5) NOT NULL default '',
PRIMARY KEY (`id`)
) ;


-- -----------------------------------------------------
-- Table ` List known as Countries List `
-- -----------------------------------------------------


DROP TABLE IF EXISTS `countriesList`;
CREATE TABLE `countriesList` (
`id` int(11) NOT NULL auto_increment,
`country_code` varchar(2) NOT NULL default '',
`country_name` varchar(100) NOT NULL default '',
`country_image` varchar(200) Not NULL default '',
`Star` varchar(5) NOT NULL default '',
`countryStatus` varchar(5) NOT NULL default '',
`continentId` varchar(50) NOT NULL default '',
PRIMARY KEY (`id`)
) ;


-- -----------------------------------------------------
-- Table ` List known as States `
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `states` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) NOT NULL,
  `country_id` int(11) NOT NULL DEFAULT '1',
  `state_image` varchar(200) Not NULL default '',
  `Star` varchar(5) NOT NULL default '',
  `stateStatus` varchar(5) NOT NULL default '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4121 ;

-- -----------------------------------------------------
-- Table ` List known as cities `
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `cities` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) NOT NULL,
  `state_id` int(11) NOT NULL DEFAULT '1',
  `city_image` varchar(200) Not NULL default '',
  `Star` varchar(5) NOT NULL default '',
  `stateStatus` varchar(5) NOT NULL default '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4121 ;

-- -----------------------------------------------------
-- Table ` List known as towns `
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `towns` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `towns_name` varchar(30) NOT NULL,
  `city_id` int(11) NOT NULL DEFAULT '1',
  `state_id` int(11) NOT NULL DEFAULT '0',
  `towns_image` varchar(200) Not NULL default '',
  `Star` varchar(5) NOT NULL default '',
  `townsStatus` varchar(5) NOT NULL default '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4121 ;

-- -----------------------------------------------------
-- Table ` List known as places `
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `places` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `places` varchar(30) NOT NULL,
  `state_id` int(11) NOT NULL DEFAULT '1',
  `country_id` varchar(200) Not NULL default '',
  `places_image` varchar(200) Not NULL default '',
  `city_id` varchar(11) NOT NULL default '',
  `star` varchar(11) NOT NULL default '',
  `townsStatus` varchar(5) NOT NULL default '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4121 ;

-- -----------------------------------------------------
-- Table ` BestOffer `
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `bestOffer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(100) NOT NULL,
  `image` varchar(100) NOT NULL,
  `description` varchar(100) NOT NULL,
  `city_id` int(11),
  `state_id` int(200) ,
  `country_id` int(11) ,
  `continent_id` int(11) ,
  `discount` int(11) NOT NULL default '0',
  `star` int(11) NOT NULL default '0',
  `discountStatus` int(5) NOT NULL default '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4121 ;

-- -----------------------------------------------------
-- Table ` Place Information `
-- -----------------------------------------------------v
CREATE TABLE IF NOT EXISTS `placeInformation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `currentLatLng` varchar(30) NOT NULL,
  `Address` varchar(300) NOT NULL DEFAULT '1',
  `number` varchar(5) NOT NULL default '',
  `websiteUrl` varchar(5) NOT NULL default '',
  `time` varchar(5) NOT NULL default '',
  `place_id` varchar(5) NOT NULL default '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4121 ;
