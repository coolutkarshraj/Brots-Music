use `edeals`;

delete from `brand` where id > 0;
delete from `category` where id > 0;
delete from `product` where id > 0;
delete from `products` where id > 0;
delete from `orders` where id > 0;
delete from `user` where id > 0;
delete from `inbox` where id > 0;

-- user
INSERT INTO `user` (`id`,`imageUrl`,`name`,`firstName`,`middleName`,`lastName`,`userType`,`shopType`,`gender`,`dob`,`email`,`phone`,`password`,`lastLogin`,`status`,`city`,`state`,`country`,`address`,`deviceToken`,`isLoggedIn`,`onlineStatus`) VALUES (1,'profile.png','Default User',NULL,NULL,NULL,'2',NULL,'male',NULL,'user@edeals.com',NULL,'1234',NULL,NULL,NULL,NULL,NULL,NULL,'cy3IxpKsXEw:APA91bHql_y-m3a-0Mzv3yoPTb_1QBHJHl7i6Opv4nAboyDI1Myv67TinMks8e_5mg3WxakVUf-vShfkhRGsA_eCJz5g917DzpS5AWA8RbBpr39wALN-RJH1Ya-0EhawQDAvPZh63_Dn',1,0);
INSERT INTO `user` (`id`,`imageUrl`,`name`,`firstName`,`middleName`,`lastName`,`userType`,`shopType`,`gender`,`dob`,`email`,`phone`,`password`,`lastLogin`,`status`,`city`,`state`,`country`,`address`,`deviceToken`,`isLoggedIn`,`onlineStatus`) VALUES (2,NULL,'Shivank Sharma',NULL,NULL,NULL,'1',NULL,'male',NULL,'shivank@edeals.com',NULL,'1234',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0);
INSERT INTO `user` (`id`,`imageUrl`,`name`,`firstName`,`middleName`,`lastName`,`userType`,`shopType`,`gender`,`dob`,`email`,`phone`,`password`,`lastLogin`,`status`,`city`,`state`,`country`,`address`,`deviceToken`,`isLoggedIn`,`onlineStatus`) VALUES (3,NULL,'Utkarsh Raj',NULL,NULL,NULL,'2',NULL,'male',NULL,'utkarsh@edeals.com',NULL,'1234',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0);
INSERT INTO `user` (`id`,`imageUrl`,`name`,`firstName`,`middleName`,`lastName`,`userType`,`shopType`,`gender`,`dob`,`email`,`phone`,`password`,`lastLogin`,`status`,`city`,`state`,`country`,`address`,`deviceToken`,`isLoggedIn`,`onlineStatus`) VALUES (4,NULL,'Manish Bhan',NULL,NULL,NULL,'2',NULL,'male',NULL,'manish@edeals.com',NULL,'1234',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0);
INSERT INTO `user` (`id`,`imageUrl`,`name`,`firstName`,`middleName`,`lastName`,`userType`,`shopType`,`gender`,`dob`,`email`,`phone`,`password`,`lastLogin`,`status`,`city`,`state`,`country`,`address`,`deviceToken`,`isLoggedIn`,`onlineStatus`) VALUES (5,NULL,'Himanshu Sharma',NULL,NULL,NULL,'1',NULL,'male',NULL,'himanshu@edeals.com',NULL,'1234',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0);

-- brand
INSERT INTO `brand` (`id`,`name`) VALUES (1,'Gillette');
INSERT INTO `brand` (`id`,`name`) VALUES (2,'Pepsi');
INSERT INTO `brand` (`id`,`name`) VALUES (3,'Nescafe');
INSERT INTO `brand` (`id`,`name`) VALUES (4,'Pampers');
INSERT INTO `brand` (`id`,`name`) VALUES (5,'Nestle');
INSERT INTO `brand` (`id`,`name`) VALUES (6,'Colgate');
INSERT INTO `brand` (`id`,`name`) VALUES (7,'Philips');
INSERT INTO `brand` (`id`,`name`) VALUES (8,'Nivea');
INSERT INTO `brand` (`id`,`name`) VALUES (9,'Panasonic');
INSERT INTO `brand` (`id`,`name`) VALUES (10,'Coca-Cola');
INSERT INTO `brand` (`id`,`name`) VALUES (11,'Dove');
INSERT INTO `brand` (`id`,`name`) VALUES (12,'Head & Shoulders');
INSERT INTO `brand` (`id`,`name`) VALUES (13,'Maggie');
INSERT INTO `brand` (`id`,`name`) VALUES (14,'Patangali');
INSERT INTO `brand` (`id`,`name`) VALUES (15,'Lays');
INSERT INTO `brand` (`id`,`name`) VALUES (16,'Parle-G');
INSERT INTO `brand` (`id`,`name`) VALUES (17,'Good day');
INSERT INTO `brand` (`id`,`name`) VALUES (18,'Haldiram');
INSERT INTO `brand` (`id`,`name`) VALUES (19,'None');

-- category
INSERT INTO `category` (`id`,`name`) VALUES (1,'Audio & Home Entertainment');
INSERT INTO `category` (`id`,`name`) VALUES (2,'Automotive');
INSERT INTO `category` (`id`,`name`) VALUES (3,'Baby & Mom');
INSERT INTO `category` (`id`,`name`) VALUES (4,'Health & Grocery');
INSERT INTO `category` (`id`,`name`) VALUES (5,'Books & Magazines');
INSERT INTO `category` (`id`,`name`) VALUES (6,'Clothing & Accessories');
INSERT INTO `category` (`id`,`name`) VALUES (7,'edeals Daily');
INSERT INTO `category` (`id`,`name`) VALUES (8,'Fragrances, Beauty & Health');
INSERT INTO `category` (`id`,`name`) VALUES (9,'Games, Consoles & Accessories');
INSERT INTO `category` (`id`,`name`) VALUES (10,'Gift Cards & Coupons');
INSERT INTO `category` (`id`,`name`) VALUES (11,'Kitchen Appliances');
INSERT INTO `category` (`id`,`name`) VALUES (12,'Jewellery');
INSERT INTO `category` (`id`,`name`) VALUES (13,'Laptops & Computer Peripherals');
INSERT INTO `category` (`id`,`name`) VALUES (14,'Mobile Accessories');
INSERT INTO `category` (`id`,`name`) VALUES (15,'Mobile Phones');
INSERT INTO `category` (`id`,`name`) VALUES (16,'Movies & Music');
INSERT INTO `category` (`id`,`name`) VALUES (17,'Musical Instruments');
INSERT INTO `category` (`id`,`name`) VALUES (18,'Sports, Fitness & Outdoors');
INSERT INTO `category` (`id`,`name`) VALUES (19,'Stationery & Office Supplies');
INSERT INTO `category` (`id`,`name`) VALUES (20,'Tools , Hardware & Electricals');
INSERT INTO `category` (`id`,`name`) VALUES (21,'Everything Else');

-- product
INSERT INTO `product` (`id`,`brandId`,`categoryId`,`name`,`type`,`mrp`,`price`,`discountRate`,`mfgDate`,`expDate`,`color`,`smell`,`taste`,`composition`) VALUES (1,11,8,'soap','bathsoap',34,0,0,NULL,'2018-02-20 00:00:00',NULL,NULL,NULL,NULL);
INSERT INTO `product` (`id`,`brandId`,`categoryId`,`name`,`type`,`mrp`,`price`,`discountRate`,`mfgDate`,`expDate`,`color`,`smell`,`taste`,`composition`) VALUES (2,11,8,'soap','kitchen',40,0,0,NULL,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `product` (`id`,`brandId`,`categoryId`,`name`,`type`,`mrp`,`price`,`discountRate`,`mfgDate`,`expDate`,`color`,`smell`,`taste`,`composition`) VALUES (3,6,8,'tooth paste',NULL,60,0,0,NULL,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `product` (`id`,`brandId`,`categoryId`,`name`,`type`,`mrp`,`price`,`discountRate`,`mfgDate`,`expDate`,`color`,`smell`,`taste`,`composition`) VALUES (4,18,4,'namkeen',NULL,150,0,0,NULL,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `product` (`id`,`brandId`,`categoryId`,`name`,`type`,`mrp`,`price`,`discountRate`,`mfgDate`,`expDate`,`color`,`smell`,`taste`,`composition`) VALUES (5,11,8,'shampoo',NULL,120,0,0,NULL,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `product` (`id`,`brandId`,`categoryId`,`name`,`type`,`mrp`,`price`,`discountRate`,`mfgDate`,`expDate`,`color`,`smell`,`taste`,`composition`) VALUES (6,11,8,'conditioner',NULL,108,0,0,NULL,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `product` (`id`,`brandId`,`categoryId`,`name`,`type`,`mrp`,`price`,`discountRate`,`mfgDate`,`expDate`,`color`,`smell`,`taste`,`composition`) VALUES (7,14,4,'noodles',NULL,12,0,0,NULL,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `product` (`id`,`brandId`,`categoryId`,`name`,`type`,`mrp`,`price`,`discountRate`,`mfgDate`,`expDate`,`color`,`smell`,`taste`,`composition`) VALUES (8,19,4,'suger',NULL,38,0,0,NULL,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `product` (`id`,`brandId`,`categoryId`,`name`,`type`,`mrp`,`price`,`discountRate`,`mfgDate`,`expDate`,`color`,`smell`,`taste`,`composition`) VALUES (9,14,4,'salt',NULL,10,0,0,NULL,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `product` (`id`,`brandId`,`categoryId`,`name`,`type`,`mrp`,`price`,`discountRate`,`mfgDate`,`expDate`,`color`,`smell`,`taste`,`composition`) VALUES (10,14,4,'rice',NULL,48,0,0,NULL,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `product` (`id`,`brandId`,`categoryId`,`name`,`type`,`mrp`,`price`,`discountRate`,`mfgDate`,`expDate`,`color`,`smell`,`taste`,`composition`) VALUES (11,14,4,'pulse',NULL,110,0,0,NULL,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `product` (`id`,`brandId`,`categoryId`,`name`,`type`,`mrp`,`price`,`discountRate`,`mfgDate`,`expDate`,`color`,`smell`,`taste`,`composition`) VALUES (12,14,4,'flour',NULL,25,0,0,NULL,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `product` (`id`,`brandId`,`categoryId`,`name`,`type`,`mrp`,`price`,`discountRate`,`mfgDate`,`expDate`,`color`,`smell`,`taste`,`composition`) VALUES (13,6,21,'tooth brush',NULL,15,0,0,NULL,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `product` (`id`,`brandId`,`categoryId`,`name`,`type`,`mrp`,`price`,`discountRate`,`mfgDate`,`expDate`,`color`,`smell`,`taste`,`composition`) VALUES (14,15,4,'chips',NULL,10,0,0,NULL,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `product` (`id`,`brandId`,`categoryId`,`name`,`type`,`mrp`,`price`,`discountRate`,`mfgDate`,`expDate`,`color`,`smell`,`taste`,`composition`) VALUES (15,16,4,'biscuit',NULL,15,0,0,NULL,NULL,NULL,NULL,NULL,NULL);

-- products
INSERT INTO `products` (`id`,`productId`,`userId`,`quantity`,`weight`,`volume`,`size`) VALUES (1,1,1,'3',500,NULL,NULL);
INSERT INTO `products` (`id`,`productId`,`userId`,`quantity`,`weight`,`volume`,`size`) VALUES (2,2,1,'1',NULL,20000,NULL);
INSERT INTO `products` (`id`,`productId`,`userId`,`quantity`,`weight`,`volume`,`size`) VALUES (3,3,1,'5',200,NULL,NULL);
INSERT INTO `products` (`id`,`productId`,`userId`,`quantity`,`weight`,`volume`,`size`) VALUES (4,4,1,'10',100,NULL,NULL);
INSERT INTO `products` (`id`,`productId`,`userId`,`quantity`,`weight`,`volume`,`size`) VALUES (5,5,1,'1',2000,NULL,NULL);
INSERT INTO `products` (`id`,`productId`,`userId`,`quantity`,`weight`,`volume`,`size`) VALUES (6,6,1,'1',0,900,NULL);
INSERT INTO `products` (`id`,`productId`,`userId`,`quantity`,`weight`,`volume`,`size`) VALUES (7,7,1,NULL,0,800,NULL);
INSERT INTO `products` (`id`,`productId`,`userId`,`quantity`,`weight`,`volume`,`size`) VALUES (8,8,1,'4',4000,NULL,NULL);
INSERT INTO `products` (`id`,`productId`,`userId`,`quantity`,`weight`,`volume`,`size`) VALUES (9,9,1,'12',200,NULL,NULL);
INSERT INTO `products` (`id`,`productId`,`userId`,`quantity`,`weight`,`volume`,`size`) VALUES (10,10,1,'1',0,300,NULL);
INSERT INTO `products` (`id`,`productId`,`userId`,`quantity`,`weight`,`volume`,`size`) VALUES (11,11,1,'1',0,1800,NULL);

-- orders
INSERT INTO `orders` (`id`,`consumerId`,`distributorId`,`productId`,`quantity`,`weight`,`volume`,`size`,`status`,`date`,`price`) VALUES (1,1,1,1,'3',200,0,NULL,0,'2018-04-05 16:01:51',45);
INSERT INTO `orders` (`id`,`consumerId`,`distributorId`,`productId`,`quantity`,`weight`,`volume`,`size`,`status`,`date`,`price`) VALUES (2,1,1,9,'3',1000,0,NULL,0,'2018-04-05 16:01:51',200);
INSERT INTO `orders` (`id`,`consumerId`,`distributorId`,`productId`,`quantity`,`weight`,`volume`,`size`,`status`,`date`,`price`) VALUES (3,1,1,15,'2',100,0,NULL,0,'2018-04-05 16:01:51',300);
INSERT INTO `orders` (`id`,`consumerId`,`distributorId`,`productId`,`quantity`,`weight`,`volume`,`size`,`status`,`date`,`price`) VALUES (4,1,1,14,'2',50,0,NULL,0,'2018-04-05 16:01:51',100);
INSERT INTO `orders` (`id`,`consumerId`,`distributorId`,`productId`,`quantity`,`weight`,`volume`,`size`,`status`,`date`,`price`) VALUES (5,1,1,15,'2',100,0,NULL,0,'2018-04-05 16:01:51',60);
INSERT INTO `orders` (`id`,`consumerId`,`distributorId`,`productId`,`quantity`,`weight`,`volume`,`size`,`status`,`date`,`price`) VALUES (6,2,1,14,'2',50,0,NULL,0,'2018-04-05 16:01:51',55);

-- inbox
INSERT INTO `inbox` (`id`,`senderId`,`receiverId`,`orderId`,`title`,`message`,`type`,`isVisible`,`status`,`date`) VALUES (1,1,1,1,'Payment received','Payment of @1 has been received succesfully from @2','1',1,1,'2018-04-05 16:06:40');
INSERT INTO `inbox` (`id`,`senderId`,`receiverId`,`orderId`,`title`,`message`,`type`,`isVisible`,`status`,`date`) VALUES (2,1,1,NULL,'Stock outdated','Maggi is going out of stock, Please update stock','2',1,1,'2018-04-05 16:06:40');
INSERT INTO `inbox` (`id`,`senderId`,`receiverId`,`orderId`,`title`,`message`,`type`,`isVisible`,`status`,`date`) VALUES (3,1,1,NULL,'Trends','New product in trend near you','3',1,1,'2018-04-05 16:06:40');
INSERT INTO `inbox` (`id`,`senderId`,`receiverId`,`orderId`,`title`,`message`,`type`,`isVisible`,`status`,`date`) VALUES (4,1,1,2,'Order delivered','Your order has been delivered successfully','5',1,1,'2018-04-05 16:06:40');
INSERT INTO `inbox` (`id`,`senderId`,`receiverId`,`orderId`,`title`,`message`,`type`,`isVisible`,`status`,`date`) VALUES (5,1,1,NULL,'Stock outdated','Dove is going out of stock','2',1,1,'2018-04-05 16:06:40');
INSERT INTO `inbox` (`id`,`senderId`,`receiverId`,`orderId`,`title`,`message`,`type`,`isVisible`,`status`,`date`) VALUES (6,1,1,NULL,'Stock outdated','Biscuits is out of stock','2',1,1,'2018-04-05 16:06:40');
INSERT INTO `inbox` (`id`,`senderId`,`receiverId`,`orderId`,`title`,`message`,`type`,`isVisible`,`status`,`date`) VALUES (7,1,1,3,'Order delivered','5 maggi packets has been deliverd successfully','5',1,1,'2018-04-05 16:06:40');
INSERT INTO `inbox` (`id`,`senderId`,`receiverId`,`orderId`,`title`,`message`,`type`,`isVisible`,`status`,`date`) VALUES (8,1,1,5,'New order','New Order of 5 maggies','4',1,1,'2018-04-05 16:06:41');