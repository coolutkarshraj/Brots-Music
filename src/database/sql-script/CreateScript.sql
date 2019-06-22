CREATE TABLE IF NOT EXISTS `cities` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(350) NOT NULL,
  `overview` varchar(1000) NOT NULL default '',
  `description` varchar(1000) NOT NULL default '',
  `state_id` int(11) NOT NULL DEFAULT '',
  `adderss` varchar(350) NOT NULL default '',
  `extra_information` varchar(350) NOT NULL default '',
  `imageUrl` varchar(200) Not NULL default '',
  `itinerary` varchar(1000) DEFAULT NULL,
  `Short_itinerary` varchar(1000) DEFAULT NULL,
  `inclusion` varchar(1000) DEFAULT NULL,
  `Exclusion` varchar(1000) DEFAULT NULL,
  `TNC` varchar(1000) DEFAULT NULL,
  `Others` varchar(1000) DEFAULT NULL,
  `hm_policy` varchar(1000) DEFAULT NULL,
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
CREATE TABLE IF NOT EXISTS `cities` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(350) NOT NULL,
  `overview` varchar(1000) NOT NULL default '',
  `description` varchar(1000) NOT NULL default '',
  `country_id` int(11) NOT NULL DEFAULT '',
  `state_id` int(11) NOT NULL DEFAULT '',
  `adderss` varchar(350) NOT NULL default '',
  `extra_information` varchar(350) NOT NULL default '',
  `imageUrl` varchar(200) Not NULL default '',
  `itinerary` varchar(1000) DEFAULT NULL,
  `Short_itinerary` varchar(1000) DEFAULT NULL,
  `inclusion` varchar(1000) DEFAULT NULL,
  `Exclusion` varchar(1000) DEFAULT NULL,
  `TNC` varchar(1000) DEFAULT NULL,
  `Others` varchar(1000) DEFAULT NULL,
  `hm_policy` varchar(1000) DEFAULT NULL,
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
  `overview` varchar(1000) DEFAULT NULL,
  `description` varchar(1000) DEFAULT NULL,
  `adderss` varchar(350) DEFAULT NULL,
  `extra_information` varchar(1000) DEFAULT NULL,
  `city_id` int(11) DEFAULT NULL,
  `state_id` int(11) DEFAULT NULL,
  `imageUrl` varchar(350) DEFAULT NULL,
  `itinerary` varchar(1000) DEFAULT NULL,
  `Short_itinerary` varchar(1000) DEFAULT NULL,
  `inclusion` varchar(1000) DEFAULT NULL,
  `Exclusion` varchar(1000) DEFAULT NULL,
  `TNC` varchar(1000) DEFAULT NULL,
  `Others` varchar(1000) DEFAULT NULL,
  `hm_policy` varchar(1000) DEFAULT NULL,
  `about` varchar(350) DEFAULT NULL,
  `followers` int(11) DEFAULT NULL,
  `following` int(11) DEFAULT NULL,
   `Star` int(11) DEFAULT NULL,
  `t_price` int(11) DEFAULT NULL,
  `d_price` int(11) DEFAULT NULL,
  `townsStatus` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4121 ;

ALTER TABLE towns
  ADD country_id int(11);