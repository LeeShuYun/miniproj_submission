drop database lifeguild;
create database if not exists lifeguild;
use lifeguild;

CREATE TABLE `userbase` (
  `userid` int NOT NULL AUTO_INCREMENT,
  `firstname` varchar(200) DEFAULT NULL,
  `lastname` varchar(200) DEFAULT NULL,
  `email` varchar(200) NOT NULL,
  `username` varchar(100) DEFAULT NULL,
  `userpassword` varchar(1028) NOT NULL,
  `userrole` varchar(20) NOT NULL,
  `confirmationcode` int NOT NULL,
  `isemailconfirmed` tinyint(1) DEFAULT '0',
  `isgooglelogin` varchar(200) NOT NULL,
  `telegram_chatid` varchar(20) DEFAULT NULL,
  `dateregistered` date NOT NULL,
  PRIMARY KEY (`userid`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `pets` (
  `petid` int NOT NULL AUTO_INCREMENT,
  `userid` int NOT NULL,
  `healingamount` int NOT NULL,
  `image` varchar(50) NOT NULL,
  PRIMARY KEY (`petid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



CREATE TABLE `characterdetails` (
  `userid` int NOT NULL,
  `characterid` int NOT NULL AUTO_INCREMENT,
  `health` int NOT NULL DEFAULT '100',
  `coinwallet` int NOT NULL DEFAULT '0',
  `currentpetid` int DEFAULT NULL,
  `image_url` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`characterid`),
  KEY `userid` (`userid`),
  KEY `currentpetid` (`currentpetid`),
  CONSTRAINT `characterdetails_ibfk_1` FOREIGN KEY (`userid`) REFERENCES `userbase` (`userid`),
  CONSTRAINT `characterdetails_ibfk_2` FOREIGN KEY (`currentpetid`) REFERENCES `pets` (`petid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `rewards` (
  `userid` int NOT NULL,
  `rewardid` int NOT NULL AUTO_INCREMENT,
  `title` varchar(500) NOT NULL,
  `cost` int NOT NULL,
  `date_created` date DEFAULT NULL,
  PRIMARY KEY (`rewardid`),
  KEY `userid` (`userid`),
  CONSTRAINT `rewards_ibfk_1` FOREIGN KEY (`userid`) REFERENCES `userbase` (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `todos` (
  `userid` int NOT NULL,
  `todoid` int NOT NULL AUTO_INCREMENT,
  `title` varchar(500) NOT NULL,
  `difficulty` varchar(4) DEFAULT NULL,
  `due_date` date DEFAULT NULL,
  `priority` varchar(4) DEFAULT NULL,
  `is_complete` tinyint(1) DEFAULT NULL,
  `date_created` date DEFAULT NULL,
  PRIMARY KEY (`todoid`),
  KEY `userid` (`userid`),
  CONSTRAINT `todos_ibfk_1` FOREIGN KEY (`userid`) REFERENCES `userbase` (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `dailies` (
  `userid` int NOT NULL,
  `dailyid` int NOT NULL AUTO_INCREMENT,
  `title` varchar(500) NOT NULL,
  `difficulty` varchar(8) DEFAULT NULL,
  `is_complete` tinyint(1) DEFAULT NULL,
  `date_created` date DEFAULT NULL,
  PRIMARY KEY (`dailyid`),
  KEY `userid` (`userid`),
  CONSTRAINT `dailies_ibfk_1` FOREIGN KEY (`userid`) REFERENCES `userbase` (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `habits` (
  `userid` int NOT NULL,
  `habitid` int NOT NULL AUTO_INCREMENT,
  `title` varchar(500) NOT NULL,
  `is_good_or_bad_habit` varchar(4) NOT NULL,
  `difficulty` varchar(4) NOT NULL,
  `positive_count` int DEFAULT NULL,
  `negative_count` int DEFAULT NULL,
  `date_created` date DEFAULT NULL,
  PRIMARY KEY (`habitid`),
  KEY `userid` (`userid`),
  CONSTRAINT `habits_ibfk_1` FOREIGN KEY (`userid`) REFERENCES `userbase` (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `enemy` (
  `enemyid` int NOT NULL AUTO_INCREMENT,
  `userid` int NOT NULL,
  `health` int NOT NULL,
  `damage` int NOT NULL,
  PRIMARY KEY (`enemyid`),
  KEY `enemy` (`userid`),
  CONSTRAINT `enemy_ibfk_1` FOREIGN KEY (`userid`) REFERENCES `userbase` (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `guilds` (
  `guild_id` int NOT NULL,
  `guild_level` int NOT NULL,
  `skills` varchar(100) NOT NULL,
  `date_created` date DEFAULT NULL,
  PRIMARY KEY (`guild_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `guild_to_user` (
  `user_id` int NOT NULL,
  `guild_id` int NOT NULL,
  `date_entered` date DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  KEY `guild_to_user` (`guild_id`),
  CONSTRAINT `guild_to_user_ibfk_1` FOREIGN KEY (`guild_id`) REFERENCES `guilds` (`guild_id`),
  CONSTRAINT `guild_to_user_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `userbase` (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



CREATE TABLE `noblesteeds` (
  `steedid` varchar(8) NOT NULL,
  `userid` int NOT NULL,
  `speed` int NOT NULL,
  PRIMARY KEY (`steedid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `petblueprints` (
  `petid` varchar(8) NOT NULL,
  `species` varchar(30) NOT NULL,
  `healingamount` int NOT NULL,
  `image` varchar(50) NOT NULL,
  PRIMARY KEY (`petid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `petmarketplace` (
  `petid` int NOT NULL,
  `userid` int NOT NULL,
  `healingamount` int NOT NULL,
  `image` varchar(50) NOT NULL,
  PRIMARY KEY (`petid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `weapons` (
  `weaponid` varchar(8) NOT NULL,
  `weaponname` varchar(20) NOT NULL,
  `weapontype` varchar(30) NOT NULL,
  `baseattack` int NOT NULL,
  `weaponfactor` decimal(2,1) NOT NULL,
  `critrate` int NOT NULL,
  PRIMARY KEY (`weaponid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
