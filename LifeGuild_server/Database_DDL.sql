drop database lifeguild;
create database if not exists lifeguild;
use lifeguild;

create table if not exists userbase(
	userid int auto_increment,
	firstname varchar(200),
	lastname varchar(200),
	email varchar(200) not null unique,
	username varchar(100),
	userpassword varchar(1028) not null,
	userrole varchar(20) not null, -- PLAYER, MODERATOR, ADMIN
    confirmationcode int(6) not null,
    isemailconfirmed boolean default false,
    isgooglelogin varchar(200) not null, -- if has email address, else null no google login
    telegram_chatid varchar(20),
    dateregistered date not null,
	primary key (userid)
);

-- many habits to 1 userid
create table if not exists habits(
	userid int not null,
	habitid int auto_increment, 
	title varchar(500) not null, -- description of habit
	is_good_or_bad_habit varchar(4) not null, -- good, bad, both.  default 'both'
	difficulty varchar(4) not null, -- high, med, low.  default 'low'
	positive_count int, -- how many times we did this positively
	negative_count int,
    date_created date,
	primary key (habitid),
	foreign key(userid) references userbase (userid)
);

-- many todoid to 1 userid
create table if not exists todos(
	userid int not null,
    todoid int auto_increment,
	title varchar(500) not null,
	difficulty varchar(4), -- high, med, low.  default 'low'
	due_date Date, -- format: yyyy-mm-dd
	priority varchar(4), -- priority: high med low, sorted nearest time first then priority
	is_complete boolean,
    date_created date,
	primary key (todoid),
    foreign key (userid) references userbase (userid)
  );

-- many dailies to 1 userid
create table if not exists dailies(
	userid int not null,
    dailyid int auto_increment,
    title varchar(500) not null,
	difficulty varchar(8), -- high, med, low.  default 'low'
	is_complete boolean, -- default false
    date_created date,
    primary key (dailyid),
    foreign key (userid) references userbase (userid)
  );

-- many rewardid to 1 userid
create table if not exists rewards(
	userid int not null,
    rewardid int auto_increment,
	title varchar(500) not null,
	cost int not null,
    date_created date,
    primary key (rewardid),
    foreign key (userid) references userbase (userid)
  );
  
--   create table if not exists recommendedbooks(
-- 	isbn10 varchar(10) default null,
-- 	isbn13 varchar(13) not null,
-- 	title varchar(300) not null,
--     previewUrl varchar(500),
-- 	imgUrl varchar(500),
--     description varchar(500) not null,
-- 	genre varchar(100) not null,
-- 	primary key (isbn13)
--   );
--   
  -- 1 user 1 pet. for now every new acc gets 1 pet dragon
  create table if not exists pets (
	--  petid varchar(8) not null, -- starts from 00000001
    petid int auto_increment,
    userid int not null, 
    -- petname varchar(30) not null default (species),
    -- species varchar(30) not null,
    healingamount int not null,
    image varchar(50) not null,
    primary key (petid)
);

  -- to hold the pets for trade
  create table if not exists petmarketplace (
	--  petid varchar(8) not null, -- starts from 00000001
    petid int not null,
    userid int not null, 
    -- petname varchar(30) not null default (species),
    -- species varchar(30) not null,
    healingamount int not null,
    image varchar(50) not null,
    primary key (petid)
);

select * from userbase where email = "test@test.com";

-- database to draw default pets from. each user who makes an account will get a row of pet inserts for now
  create table if not exists petblueprints (
	petid varchar(8) not null, -- starts from 00000001 and increments. 
    species varchar(30) not null,
    healingamount int not null,
    image varchar(50) not null,
    primary key (petid)
);

-- weapons are the ones with their own critrate 
create table if not exists weapons (
	weaponid varchar(8) not null, -- starts from 00000001 and increments. admin can add more
    weaponname varchar(20) not null,
    weapontype varchar (30) not null,
	baseattack int not null,
    weaponfactor decimal(2,1) not null, 
	critrate int not null,
    primary key(weaponid)
);


-- separation of character account from user to allow possible future separation of responsibilities
-- one character for work life, one character for private life for example. maybe. good to have options
-- each player can be 4 different jobs and switch job at will
-- experience minimum for each job class:
-- novice = level 1 
-- character => unlock at novice level 10 - gains exp by doing tasks - I'll split this up later
-- writer => unlock at novice level 10 - gains exp by writing story campaigns. unlocks the writer campaign upload page
-- reader => unlock at novice level 10 - gains exp by reading a ton of books. gain access to library and book search :D
create table if not exists characterdetails(
	userid int not null,
    characterid int auto_increment,
   --  charactername varchar (100) not null,
    health int not null default 100,
--     experience int not null default 0,
--     mana int not null default 100,
--     novicelevel int not null default 1,
--     readerlevel int not null default 1,
--     characterlevel int not null default 1,
--     writerlevel int not null default 1,
    coinwallet int not null default 0,
--     gachacurrency int not null default 0, -- future impl for fancy equipment and mounts
    currentpetid int,
    -- unlockedpets varchar(300), -- an array of boolean, each letter = 1 pet eg. TFTFTFFF = pet1,3,5 unlocked. 300 pets limit for now
--     currentweaponid varchar(8), -- null = barehand
--     unlockedweapons varchar(300), -- an array of boolean, each letter = 1 weapon eg. TFTFTFFF = weapon1,3,5 unlocked
    image_url varchar(300),
--     date_created date,
    primary key (characterid),
    foreign key (userid) references userbase (userid),
--     foreign key (currentweaponid) references weapons (weaponid),
    foreign key (currentpetid) references pets (petid)
);

-- each one is untradeable but can be unlocked. 1 user to 1 steed 
create table if not exists noblesteeds (
	steedid varchar(8) not null, -- starts from 00000001 and increments. tracked in angular.
    userid int not null,
    speed int not null,
    primary key (steedid)
);

create table if not exists guilds(
	guild_id int not null primary key,
    guild_level int not null, 
    skills varchar(100) not null,
	date_created date
);

create table if not exists guild_to_user(
	user_id int not null primary key,
    guild_id int not null ,
    date_entered date,
    foreign key guild_to_user (guild_id) references guilds (guild_id),
    foreign key guild_to_user (user_id) references userbase(userid)
);

-- userid, health, damag
create table if not exists enemy(
enemyid int not null primary key,
	userid int not null,
    health int not null ,
    damage int not null,
    foreign key enemy (userid) references userbase(userid)
);


select * from characterdetails where userid = 2;