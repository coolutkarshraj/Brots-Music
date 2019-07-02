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
  `status` varchar(255) DEFAULT NULL,
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
  `religious` varchar(255) DEFAULT NULL,
  `pets` smallint(3) DEFAULT NULL,
  `smoke` smallint(3) DEFAULT NULL,
   PRIMARY KEY (`id`),
   UNIQUE (`email`),
   unique(`userName`),
   unique(`phone`)
);

DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
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
  `status` varchar(255) DEFAULT NULL,
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
  `religious` varchar(255) DEFAULT NULL,
  `pets` smallint(3) DEFAULT NULL,
  `smoke` smallint(3) DEFAULT NULL,
   PRIMARY KEY (`id`),
   UNIQUE (`email`),
   unique(`userName`),
   unique(`phone`)
);



DROP TABLE IF EXISTS `userProfileeducation`;
CREATE TABLE `userProfileeducation` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `userId` int(11) not NULL,
  `educationTitle` varchar(255) DEFAULT NULL,
  `educationDescription` varchar(255) DEFAULT NULL,
   PRIMARY KEY (`id`),
   FOREIGN KEY f_user_id(`userId`)
   REFERENCES user(`id`)
   ON UPDATE CASCADE
   ON DELETE RESTRICT
);

DROP TABLE IF EXISTS `userProfilePlace`;
CREATE TABLE `userProfilePlace` (
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

DROP TABLE IF EXISTS `userProfilepublicTag`;
CREATE TABLE `userProfilepublicTag` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `userId` int(11) not NULL,
  `publictagTitle` varchar(255) DEFAULT NULL,
  `publictagDescription` varchar(255) DEFAULT NULL,
  `publictagrating` smallint(2) DEFAULT NULL,
  `publictagUrl` smallint(2) DEFAULT NULL,
   PRIMARY KEY (`id`),
   FOREIGN KEY fk_user_id1(`userId`)
   REFERENCES user(`id`)
   ON UPDATE CASCADE
   ON DELETE RESTRICT
);



CREATE TABLE `springfield` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `imageUrl` varchar(350) DEFAULT NULL,
  `name` varchar(350) DEFAULT NULL,
  `overview` longtext DEFAULT NULL,
  `description` longtext DEFAULT NULL,
  `itinerary` longtext DEFAULT NULL,
  `Short_itinerary` longtext DEFAULT NULL,
  `inclusion` longtext DEFAULT NULL,
  `Exclusion` longtext DEFAULT NULL,
  `TNC` longtext DEFAULT NULL,
  `Others` longtext DEFAULT NULL,
  `hm_policy` longtext DEFAULT NULL,
  `about` varchar(350) DEFAULT NULL,
  `followers` int(11) DEFAULT NULL,
  `following` int(11) DEFAULT NULL,
  `StarLevel` int(11) DEFAULT NULL,
  `t_price` int(11) DEFAULT NULL,
  `d_price` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

-- -----------------------------------------------------
-- Table ` List known as continent `
-- -----------------------------------------------------
DROP TABLE IF EXISTS `continents`; 
CREATE TABLE `continents`(
  `id` int(11) NOT NULL auto_increment,
  `springFieldId` int(11) NOT NULL,
  `name` varchar(350) DEFAULT NULL,
  `imageUrl` varchar(350) DEFAULT NULL,
  `overview` longtext DEFAULT NULL,
  `description` longtext DEFAULT NULL,
  `itinerary` longtext DEFAULT NULL,
  `Short_itinerary` longtext DEFAULT NULL,
  `inclusion` longtext DEFAULT NULL,
  `Exclusion` longtext DEFAULT NULL,
  `TNC` longtext DEFAULT NULL,
  `Others` longtext DEFAULT NULL,
  `hm_policy` longtext DEFAULT NULL,
  `about` varchar(350) DEFAULT NULL,
  `package_type` varchar(350) DEFAULT NULL,
  `followers` int(11) DEFAULT NULL,
  `following` int(11) DEFAULT NULL,
  `StarLevel` int(11) DEFAULT NULL,
  `t_price` int(11) DEFAULT NULL,
  `d_price` int(11) DEFAULT NULL,
  `continentsStatus` int(11) DEFAULT NULL,
PRIMARY KEY (`id`)
);


-- -----------------------------------------------------
-- Table ` List known as Countries List `
-- -----------------------------------------------------

CREATE TABLE `countrieslist` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `continentId` int(11) DEFAULT NULL,
  `country_code` varchar(350) NOT NULL default '',
  `country_name` varchar(350) NOT NULL default '',
  `overview` longtext DEFAULT NULL,
  `imageUrl` longtext DEFAULT NULL,
  `description` longtext DEFAULT NULL,
  `itinerary` longtext DEFAULT NULL,
  `Short_itinerary` longtext DEFAULT NULL,
  `inclusion` longtext DEFAULT NULL,
  `Exclusion` longtext DEFAULT NULL,
  `TNC` longtext DEFAULT NULL,
  `Others` longtext DEFAULT NULL,
  `hm_policy` longtext DEFAULT NULL,
  `about` varchar(350) DEFAULT NULL,
  `followers` int(11) DEFAULT NULL,
  `following` int(11) DEFAULT NULL,
   `Star` int(11) DEFAULT NULL,
  `t_price` int(11) DEFAULT NULL,
  `d_price` int(11) DEFAULT NULL,
  `countryStatus` int(11) DEFAULT NULL,
  `adderss` varchar(350) NOT NULL default '',
  `extra_information` longtext NOT NULL default '',
  PRIMARY KEY (`id`)
);

-- -----------------------------------------------------
-- Table ` List known as States `
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `states` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(350) NOT NULL,
  `overview` longtext DEFAULT NULL,
  `description` longtext DEFAULT NULL,
  `country_id` int(11) DEFAULT NULL,
  `imageUrl` varchar(200) DEFAULT NULL,
  `stateStatus` int(11) DEFAULT NULL,
  `adderss` varchar(350) DEFAULT NULL,
  `extra_information` longtext DEFAULT NULL,
  `itinerary` longtext DEFAULT NULL,
  `Short_itinerary` longtext DEFAULT NULL,
  `inclusion` longtext DEFAULT NULL,
  `Exclusion` longtext DEFAULT NULL,
  `TNC` longtext DEFAULT NULL,
  `Others` longtext DEFAULT NULL,
  `hm_policy` longtext DEFAULT NULL,
  `about` varchar(350) DEFAULT NULL,
  `followers` int(11) DEFAULT NULL,
  `following` int(11) DEFAULT NULL,
   `Star` int(11) DEFAULT NULL,
  `t_price` int(11) DEFAULT NULL,
  `d_price` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4121 ;

-- -----------------------------------------------------
-- Table ` List known as cities `
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `cities` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(350) NOT NULL,
  `overview` longtext NOT NULL default '',
  `description` longtext NOT NULL default '',
  `state_id` int(11) NOT NULL DEFAULT '1',
  `adderss` varchar(350) NOT NULL default '',
  `extra_information` varchar(350) NOT NULL default '',
  `imageUrl` varchar(200) Not NULL default '',
  `itinerary` longtext DEFAULT NULL,
  `Short_itinerary` longtext DEFAULT NULL,
  `inclusion` longtext DEFAULT NULL,
  `Exclusion` longtext DEFAULT NULL,
  `TNC` longtext DEFAULT NULL,
  `Others` longtext DEFAULT NULL,
  `hm_policy` longtext DEFAULT NULL,
  `about` varchar(350) DEFAULT NULL,
  `followers` int(11) DEFAULT NULL,
  `following` int(11) DEFAULT NULL,
   `Star` int(11) DEFAULT NULL,
  `t_price` int(11) DEFAULT NULL,
  `d_price` int(11) DEFAULT NULL,
  `citieStatus` int(11) default NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4121 ;

-- -----------------------------------------------------
-- Table ` List known as towns `
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `towns` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `towns_name` varchar(350) NOT NULL,
  `overview` longtext DEFAULT NULL,
  `description` longtext DEFAULT NULL,
  `adderss` varchar(350) DEFAULT NULL,
  `extra_information` longtext DEFAULT NULL,
  `city_id` int(11) DEFAULT NULL,
  `state_id` int(11) DEFAULT NULL,
  `imageUrl` varchar(350) DEFAULT NULL,
  `itinerary` longtext DEFAULT NULL,
  `Short_itinerary` longtext DEFAULT NULL,
  `inclusion` longtext DEFAULT NULL,
  `Exclusion` longtext DEFAULT NULL,
  `TNC` longtext DEFAULT NULL,
  `Others` longtext DEFAULT NULL,
  `hm_policy` longtext DEFAULT NULL,
  `about` varchar(350) DEFAULT NULL,
  `followers` int(11) DEFAULT NULL,
  `following` int(11) DEFAULT NULL,
   `Star` int(11) DEFAULT NULL,
  `t_price` int(11) DEFAULT NULL,
  `d_price` int(11) DEFAULT NULL,
  `townsStatus` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4121 ;

-- -----------------------------------------------------
-- Table ` List known as places `
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `places` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `place_name` varchar(30) NOT NULL,
  `state_id` int(11) DEFAULT NULL,
  `country_id` varchar(200) DEFAULT NULL,
  `imageUrl` varchar(350) DEFAULT NULL,
  `overview` longtext DEFAULT NULL,
  `description` longtext DEFAULT NULL,
  `adderss` varchar(350) DEFAULT NULL,
  `extra_information` varchar(350) DEFAULT NULL,
  `city_id` int(11) DEFAULT NULL,
  `town_id` int(11) DEFAULT NULL,
  `itinerary` longtext DEFAULT NULL,
  `Short_itinerary` longtext DEFAULT NULL,
  `inclusion` longtext DEFAULT NULL,
  `Exclusion`longtext DEFAULT NULL,
  `TNC` longtext DEFAULT NULL,
  `Others` longtext DEFAULT NULL,
  `hm_policy` longtext DEFAULT NULL,
  `about` varchar(350) DEFAULT NULL,
  `followers` int(11) DEFAULT NULL,
  `following` int(11) DEFAULT NULL,
  `Star` int(11) DEFAULT NULL,
  `t_price` int(11) DEFAULT NULL,
  `d_price` int(11) DEFAULT NULL,
  `placeStatus` int(11) DEFAULT NULL,
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
-- -----------------------------------------------------
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

-- -----------------------------------------------------
-- Table ` UserGallery `
-- -----------------------------------------------------
CREATE TABLE `createUserGallery` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `galleryTile` varchar(100) NOT NULL,
  `galleyIcon` varchar(100) NOT NULL,
  `createdDate` datetime DEFAULT NULL,
  `galleryDescription` varchar(200) DEFAULT NULL,
  `location` int(200) DEFAULT NULL,
  `userId` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=4121;





-- -----------------------------------------------------
-- Table ` User Image Gallery `
-- -----------------------------------------------------
CREATE TABLE `userImageGallery` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `imageTitle` varchar(100) NOT NULL,
  `imageIcon` varchar(100) NOT NULL,
  `createdDate` datetime DEFAULT NULL,
  `total_like` int(11) DEFAULT NULL,
  `total_comment` int(200) DEFAULT NULL,
  `total_share` int(11) NOT NULL,
  `userId` int(11) NOT NULL,
  `gallery_Id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `isBookMarked` int(11) NOT NULL,
   PRIMARY KEY (`id`),
   FOREIGN KEY fk_gallery_id(`gallery_Id`)
   REFERENCES createUserGallery(`id`)
   ON UPDATE CASCADE
   ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=4121;


CREATE TABLE `booking_enquiry` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `customerId` int(11) DEFAULT NULL,
  `from` varchar(350) NOT NULL default '',
  `destination` varchar(350) NOT NULL default '',
  `fromdate` varchar(1000) DEFAULT NULL,
  `todate` varchar(350) DEFAULT NULL,
  `guest` varchar(1000) DEFAULT NULL,
  `Budget` varchar(1000) DEFAULT NULL,
  `note` varchar(1000) DEFAULT NULL,
  `transportprefered` varchar(1000) DEFAULT NULL,
  `isconfirmed` int(11) DEFAULT NULL,
  `Others` varchar(1000) DEFAULT NULL,
  `extra_information` varchar(1000) NOT NULL default '',
  PRIMARY KEY (`id`)
);

CREATE TABLE `book_tour` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `customerId` int(11) DEFAULT NULL,
  `from` varchar(350) NOT NULL default '',
  `destination` varchar(350) NOT NULL default '',
  `fromdate` varchar(1000) DEFAULT NULL,
  `todate` varchar(350) DEFAULT NULL,
  `guest` varchar(1000) DEFAULT NULL,
  `Budget` varchar(1000) DEFAULT NULL,
  `note` varchar(1000) DEFAULT NULL,
  `transportprefered` varchar(1000) DEFAULT NULL,
  `isconfirmed` int(11) DEFAULT NULL,
  `Others` varchar(1000) DEFAULT NULL,
  `extra_information` varchar(1000) NOT NULL default '',
  `traveller_name` varchar(1000) NOT NULL default '',
  `traveller_age` varchar(1000) NOT NULL default '',
  `traveller_gender` varchar(1000) NOT NULL default '',
  `traveller_room` varchar(1000) NOT NULL default '',
  `category` varchar(1000) NOT NULL default '',
  PRIMARY KEY (`id`)
);

CREATE TABLE `revivew` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `imageUrl` varchar(350) DEFAULT NULL,
  `review` varchar(350) DEFAULT NULL,
  `rating` int(11) DEFAULT NULL,
  `dest_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `others` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

ALTER TABLE cities
ADD country_id int(11) default null;


ALTER TABLE towns
  ADD country_id int(11);

 ALTER TABLE user
  ADD isBlockedUserr int(11); 

  CREATE TABLE IF NOT EXISTS `userQuery` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL,
  `Title` varchar(350) NOT NULL default '',
  `desription` varchar(1000) NOT NULL default '',
  `adminReverted` int(11) NOT NULL DEFAULT 0,
  `email` varchar(350) NOT NULL default '',
  `imageUrl` varchar(350) NOT NULL default '',
  `adminimage` varchar(350) NOT NULL default '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4121 ;

  CREATE TABLE IF NOT EXISTS `adminQuery` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL,
  `Title` varchar(350) NOT NULL default '',
  `desription` varchar(1000) NOT NULL default '',
  `email` varchar(350) NOT NULL default '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4121 ;

-- -----------------------------------------------------
-- Table ` User BookMark Image Gallery `
-- -----------------------------------------------------


 CREATE TABLE `userBookMarkedImage` (
  `bookMarkId` int(11) NOT NULL AUTO_INCREMENT,
  `isBookMarked` varchar(100) NOT NULL,
  `user_Id` int(11) NOT NULL,
  `imageId` int DEFAULT NULL,
   PRIMARY KEY (`bookMarkId`),
   FOREIGN KEY fk_image_id(`imageId`)
   REFERENCES userImageGallery(`id`)
   ON UPDATE CASCADE
   ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=4121;

-- -----------------------------------------------------
-- Table Destination Image Gallery `
-- -----------------------------------------------------

CREATE TABLE `springFieldGallery` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `springFieldId` int(11) DEFAULT NULL,
  `title` varchar(350) NOT NULL default '',
  `description` varchar(350) NOT NULL default '',
  `imageUrl` varchar(350) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `continentgallery` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `continentId` int(11) DEFAULT NULL,
  `title` varchar(350) NOT NULL default '',
  `description` varchar(350) NOT NULL default '',
  `imageUrl` varchar(350) DEFAULT NULL,
  PRIMARY KEY (`id`)
);


CREATE TABLE `countryGallery` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `countryId` int(11) DEFAULT NULL,
  `title` varchar(350) NOT NULL default '',
  `description` varchar(350) NOT NULL default '',
  `imageUrl` varchar(350) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `stateGallery` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `stateId` int(11) DEFAULT NULL,
  `title` varchar(350) NOT NULL default '',
  `description` varchar(350) NOT NULL default '',
  `imageUrl` varchar(350) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `cityGalley` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cityId` int(11) DEFAULT NULL,
  `title` varchar(350) NOT NULL default '',
  `description` varchar(350) NOT NULL default '',
  `imageUrl` varchar(350) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `townsGalley` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `townId` int(11) DEFAULT NULL,
  `title` varchar(350) NOT NULL default '',
  `description` varchar(350) NOT NULL default '',
  `imageUrl` varchar(350) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `placeGalley` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `placeId` int(11) DEFAULT NULL,
  `title` varchar(350) NOT NULL default '',
  `description` varchar(350) NOT NULL default '',
  `imageUrl` varchar(350) DEFAULT NULL,
  PRIMARY KEY (`id`)
);