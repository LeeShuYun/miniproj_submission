use lifeguild;

-- INSERT INTO recommendedbooks (isbn10, isbn13, title, summary, genre)
-- VALUES (null, "9781491910771", "Head First Java", 
-- "Head First Java is a complete learning experience in Java and object-oriented programming. 
-- With this book, you'll learn the Java language with a unique method that goes beyond how-to manuals 
-- and helps you become a great programmer. Through puzzles, mysteries, and soul-searching interviews with 
-- famous Java objects, you'll quickly get up to speed on Java's fundamentals and advanced topics 
-- including lambdas, streams, generics, threading, networking, and the dreaded desktop GUI.", "Programming");
-- INSERT INTO recommendedbooks (isbn10, isbn13, title, summary, genre)
-- VALUES (null, "9780062658753", "Charlotte's Web", 
-- "The novel tells the story of a livestock pig named Wilbur and his friendship with a barn spider named Charlotte. 
-- When Wilbur is in danger of being slaughtered by the farmer, Charlotte writes messages praising Wilbur 
-- such as 'Some Pig' and 'Humble' in her web in order to persuade the farmer to let him live. ", "Children's");
-- INSERT INTO recommendedbooks (isbn10, isbn13, title, summary, genre)
-- VALUES ("0224020404", "9780142410387", "The BFG", 
-- "A young orphan girl, Sophie, gets taken away to a faraway land populated by Giants and Dreams.", "Children's, Fantasy");
-- select * from recommendedbooks;

-- userbase
INSERT INTO userbase(userid, firstname,lastname,email,username,userpassword,userrole,confirmationcode, isemailconfirmed, isgooglelogin, telegram_chatid, dateregistered) -- admin
VALUES ("98c2ba96", "tester", "admin", "admin@lifeguild.fun", "admin", "lolololol", "ADMIN", 999999, true, false, "460909373", "2023-05-01");
INSERT INTO userbase(userid, firstname,lastname,email,username,userpassword,userrole,confirmationcode, isemailconfirmed,isgooglelogin, telegram_chatid, dateregistered) -- guest acc
VALUES ("98c2bce4","testfirstname", "testlastname", "test@test.com", "guest", "testtesttest", "PLAYER", 999999, true, false, "460909373", "2023-05-01");

select * from userbase where email = "test@test.com";

select * from userbase
inner join pets
on userbase.userid = pets.userid;
-- select * from userbase where userid = 2;
-- update userbase set isemailconfirmed = false where email = "leeshuyun.art@gmail.com";
-- update userbase set telegram_chatid = "123456789" where email = "fred@gmail.com";
-- select userid from userbase where email ="support@lifeguild.fun";
-- select * from userbase where username ="testusername";
-- delete from characterdetails where userid =20;
-- delete from userbase where userid = 20;

-- pets for admin - these correspond to the marketplace for now
INSERT INTO pets(petid, userid, healingamount, image) VALUES ("68bdd3d7", "98c2ba96", 7, "cat.webp");
INSERT INTO pets(petid, userid, healingamount, image) VALUES ("5179d4f2", "98c2ba96", 10, "R2D2.avif");
INSERT INTO pets(petid, userid, healingamount, image) VALUES ( "33bb78ce", "98c2ba96", 11, "kirby.webp");
INSERT INTO pets(petid, userid, healingamount, image) VALUES ( "c5888e55", "98c2ba96", 16, "dog.webp");
INSERT INTO pets(petid, userid, healingamount, image) VALUES ( "434c78a8", "98c2ba96", 5, "cat.webp");
INSERT INTO pets(petid, userid, healingamount, image) VALUES ( "b1760c15", "98c2ba96", 20, "dog.webp");
INSERT INTO pets(petid,userid, healingamount, image) VALUES ( "73c97545", "98c2ba96", 11, "kirby.webp");
INSERT INTO pets(petid,userid, healingamount, image) VALUES ( "e182bd43", "98c2ba96", 16, "dog.webp");
INSERT INTO pets(petid,userid, healingamount, image) VALUES ( "f492b894", "98c2ba96", 5, "cat.webp");
INSERT INTO pets(petid,userid, healingamount, image) VALUES (  "d4abcb5c",  "98c2ba96", 20, "dog.webp");
INSERT INTO pets(petid,userid, healingamount, image) VALUES (  "6ba14988", "98c2ba96", 16, "dog.webp");
INSERT INTO pets(petid,userid, healingamount, image) VALUES (  "2441acd2", "98c2ba96", 5, "cat.webp");
INSERT INTO pets(petid,userid, healingamount, image) VALUES (  "aed33dfd", "98c2ba96", 20, "dog.webp");
INSERT INTO pets(petid,userid, healingamount, image) VALUES (  "6c578c7e", "98c2ba96", 11, "kirby.webp"); 
INSERT INTO pets(petid,userid, healingamount, image) VALUES (  "a451fc82", "98c2ba96", 16, "dog.webp");
INSERT INTO pets(petid,userid, healingamount, image) VALUES ( "845cc815", "98c2ba96", 5, "cat.webp");
INSERT INTO pets(petid,userid, healingamount, image) VALUES (  "08eada77", "98c2ba96", 20, "dog.webp");


-- when userid  = 0, the pet has no owner and is eligible for adoption. money goes to admin
-- INSERT INTO pets(userid, healingamount, image) VALUES ( "2",  5, "dog.webp");
-- INSERT INTO pets( userid, healingamount, image) VALUES ( "2",  5, "cat.webp");
INSERT INTO pets(petid, userid, healingamount, image) VALUES ( "2858ef00", "98c2ba96", 7, "R2D2.avif"); -- admin
INSERT INTO pets(petid, userid, healingamount, image) VALUES ( "595001fa", "98c2bce4", 15, "kirby.avif"); -- demo player
INSERT INTO pets(petid, userid, healingamount, image) VALUES ("12bf3aab", "98c2ba96", 10, "kirby.avif"); -- admin
select * from pets where userid = "98c2bce4";
-- update pets
-- set healingamount= 100,
-- image = "kirby.avif"
-- where petid = 19;

-- update pets 
-- set userid = 2, 
-- healingamount = 10, 
-- image = "kirby.avif"
-- where petid = 16; 

-- should be same as the existing amount of pets owned by admin FOR NOW
INSERT INTO petmarketplace(petid, userid, healingamount, image) VALUES ("68bdd3d7", "98c2ba96", 7, "cat.webp");
INSERT INTO petmarketplace(petid, userid, healingamount, image) VALUES ("5179d4f2", "98c2ba96", 10, "R2D2.avif");
INSERT INTO petmarketplace(petid, userid, healingamount, image) VALUES ( "33bb78ce", "98c2ba96", 11, "kirby.webp");
INSERT INTO petmarketplace(petid, userid, healingamount, image) VALUES ( "c5888e55", "98c2ba96", 16, "dog.webp");
INSERT INTO petmarketplace(petid, userid, healingamount, image) VALUES ( "434c78a8", "98c2ba96", 5, "cat.webp");
INSERT INTO petmarketplace(petid, userid, healingamount, image) VALUES ( "b1760c15", "98c2ba96", 20, "dog.webp");
INSERT INTO petmarketplace(petid,userid, healingamount, image) VALUES ( "73c97545", "98c2ba96", 11, "kirby.webp");
INSERT INTO petmarketplace(petid,userid, healingamount, image) VALUES ( "e182bd43", "98c2ba96", 16, "dog.webp");
INSERT INTO petmarketplace(petid,userid, healingamount, image) VALUES ( "f492b894", "98c2ba96", 5, "cat.webp");
INSERT INTO petmarketplace(petid,userid, healingamount, image) VALUES (  "d4abcb5c",  "98c2ba96", 20, "dog.webp");
INSERT INTO petmarketplace(petid,userid, healingamount, image) VALUES (  "6ba14988", "98c2ba96", 16, "dog.webp");
INSERT INTO petmarketplace(petid,userid, healingamount, image) VALUES (  "2441acd2", "98c2ba96", 5, "cat.webp");
INSERT INTO petmarketplace(petid,userid, healingamount, image) VALUES (  "aed33dfd", "98c2ba96", 20, "dog.webp");
INSERT INTO petmarketplace(petid,userid, healingamount, image) VALUES (  "6c578c7e", "98c2ba96", 11, "kirby.webp"); 
INSERT INTO petmarketplace(petid,userid, healingamount, image) VALUES (  "a451fc82", "98c2ba96", 16, "dog.webp");
INSERT INTO petmarketplace(petid,userid, healingamount, image) VALUES ( "845cc815", "98c2ba96", 5, "cat.webp");
INSERT INTO petmarketplace(petid,userid, healingamount, image) VALUES (  "08eada77", "98c2ba96", 20, "dog.webp");
select * from petmarketplace;
-- delete from petmarketplace where petid = 15;

-- pet blueprints, do not change
-- use to generate new pets
INSERT INTO petblueprints(blueprintid, species, healingamount, image) VALUES ("00000001", "dog", 5, "dog.webp");
INSERT INTO petblueprints(blueprintid, species, healingamount, image) VALUES ("00000002", "cat", 5, "cat.webp");
INSERT INTO petblueprints(blueprintid, species, healingamount, image) VALUES ("00000003", "special", 10, "kirby.avif");
INSERT INTO petblueprints(blueprintid, species, healingamount, image) VALUES ("00000004", "special", 7, "R2D2.avif");
INSERT INTO petblueprints(blueprintid, species, healingamount, image) VALUES ("00000005", "dragon", 15, "dragon.avif");

select * from pets;
-- delete from pets where petid = "00000010";




INSERT INTO dailies(userid, title, difficulty, is_complete,date_created) 
VALUES("98c2ba96", "drink 1.5l water", "low", false, "2023-5-11");
INSERT INTO dailies(userid, title, difficulty, is_complete, date_created) 
VALUES("98c2bce4", "check email", "low", true, "2023-5-11");
INSERT INTO dailies(userid, title, difficulty, is_complete, date_created) 
VALUES("98c2ba96", "30 minutes exercise", "med", false, "2023-5-11");
INSERT INTO dailies(userid, title, difficulty, is_complete, date_created) 
VALUES("98c2ba96", "take dog out for a walk", "med", true, "2023-5-11");
INSERT INTO dailies(userid, title, difficulty, is_complete, date_created) 
VALUES("98c2bce4", "do some code", "low", false, "2023-5-11");

-- select * from dailies;
select * from dailies where userid = "98c2bce4";
-- update dailies 
-- set title = "new title", 
-- difficulty = "high",
-- is_complete = 1
-- where dailyid = 10; 

INSERT INTO rewards(userid, title,cost, date_created) 
VALUES("98c2ba96", "Watch a show", 30, "2023-5-11");
INSERT INTO rewards(userid, title,cost, date_created) 
VALUES("98c2bce4", "buy that overly expensive thing that makes you happy", 20, "2023-5-11");
INSERT INTO rewards(userid,title,cost, date_created) 
VALUES("98c2bce4", "Watch a show", 15, "2023-5-11");
INSERT INTO rewards(userid,title,cost, date_created) 
VALUES("98c2bce4", "get a snack", 10, "2023-5-11");
INSERT INTO rewards(userid,title,cost, date_created) 
VALUES("98c2ba96", "take a nice evening walk", 5, "2023-5-11");
INSERT INTO rewards(userid,title,cost,date_created) 
VALUES("98c2ba96", "buy that overly expensive thing you shouldn't be buying but makes you happy", 20, "2023-5-11");
select * from rewards;
-- select * from rewards where userid = 1;
-- delete from rewards where rewardid = 2;
-- update rewards 
-- set title = "new title", 
-- cost = 20
-- where rewardid = 2; 


INSERT INTO todos (userid, title, difficulty, due_date, priority, is_complete, date_created) 
VALUES("98c2ba96", "get tickets to the aquarium", "low", "2023-5-15", "med", false, "2023-5-11");
INSERT INTO todos (userid, title, difficulty, due_date, priority, is_complete, date_created) 
VALUES("98c2ba96", "finish project", "high", "2023-5-28", "high", false, "2023-5-12");
INSERT INTO todos (userid, title, difficulty, due_date, priority, is_complete, date_created) 
VALUES("98c2ba96", "get groceries", "med", "2023-5-18", "high", false, "2023-5-13");
INSERT INTO todos (userid, title, difficulty, due_date, priority, is_complete, date_created) 
VALUES("98c2bce4", "finish project", "high", "2023-5-15", "med", false, "2023-5-11");
INSERT INTO todos (userid, title, difficulty, due_date, priority, is_complete, date_created) 
VALUES("98c2bce4", "read a book", "high", "2023-5-28", "high", false, "2023-5-12");
INSERT INTO todos (userid, title, difficulty, due_date, priority, is_complete, date_created) 
VALUES("98c2bce4", "get some sleep", "med", "2023-5-18", "high", false, "2023-5-13");

select * from todos;
-- select * from todos where userid = 1;
-- update todos 
-- set title = "new title", 
-- difficulty = "high",
-- due_date = "2023-05-25",
-- priority = "high",
-- is_complete = true
-- where todoid = 2; 

INSERT INTO habits(userid, title, is_good_or_bad_habit, difficulty, positive_count, negative_count, date_created) 
VALUES("98c2ba96", "leaning on elbows", "bad", "low", 2, 5, "2023-5-15");
INSERT INTO habits(userid, title, is_good_or_bad_habit, difficulty, positive_count, negative_count, date_created) 
VALUES("98c2ba96", "sitting up straight", "good", "low", 3, 1, "2023-5-12");
INSERT INTO habits(userid, title, is_good_or_bad_habit, difficulty, positive_count, negative_count, date_created) 
VALUES("98c2ba96", "stay focused", "both", "low", 5, 1, "2023-5-10");
INSERT INTO habits(userid, title, is_good_or_bad_habit, difficulty, positive_count, negative_count, date_created) 
VALUES("98c2bce4", "leaning on elbows", "bad", "low", 2, 5, "2023-5-15");
INSERT INTO habits(userid, title, is_good_or_bad_habit, difficulty, positive_count, negative_count, date_created) 
VALUES("98c2bce4", "sitting up straight", "good", "low", 3, 1, "2023-5-12");
INSERT INTO habits(userid, title, is_good_or_bad_habit, difficulty, positive_count, negative_count, date_created) 
VALUES("98c2bce4", "stay focused", "both", "low", 5, 1, "2023-5-10");
-- select * from habits;
-- delete from habits where habitid = 4;
select * from habits where userid = "98c2bce4";

-- update habits 
-- set title = "new title", 
-- is_good_or_bad_habit = "good",
-- difficulty = "high",
-- positive_count = 5,
-- negative_count = 6
-- where habitid = 30; 

-- weapons
-- INSERT INTO weapons(weaponid,weaponname,weapontype,baseattack ,weaponfactor,critrate) 
-- VALUES("00000001", "Falchion", "Sword", 150,1.5,4 );

-- register user 
-- INSERT INTO characterdetails (
-- userid, charactername, health, experience, mana, 
-- novicelevel, readerlevel, characterlevel, writerlevel,
-- coinwallet, gachacurrency, 
-- currentpetid, unlockedpets, currentweaponid, unlockedweapons,
-- imageUrl,
-- date_created
-- ) VALUES(
-- 1, "admin", 100, 0, 50, 
-- 12, 1, 1, 1,
-- 100, 30, 
-- "00000001", "TTTTFF", "00000001", "TTTTFF",
-- "character.avif",
-- "2023-05-16"
-- );


INSERT INTO characterdetails (
userid, health,
coinwallet,
currentpetid, 
image_url
) VALUES(
"98c2ba96", 100,
100,  
"6c578c7e",
"character.avif"
);
INSERT INTO characterdetails (
userid, health,
coinwallet,
currentpetid, 
image_url
) VALUES(
"98c2bce4", 100,
100,  
"595001fa",
"character.avif"
);

-- spend coins on reward
select * from characterdetails;
-- update characterdetails 
-- set health = 90,
-- coinwallet = 120,
-- currentpetid = 3,
-- image_url = "character.avif"
-- where userid = 2;
-- update characterdetails set coinwallet = coinwallet + 10 where userid = 1;
-- update characterdetails set currentpetid = 4 where userid = 2;
-- select * from characterdetails where userid =1;
-- delete from characterdetails where userid = 21;

-- to trade a pet from another player, find petid and then update money. if pet id search fails, revert everything 
-- update characterdetails set coinwallet = coinwallet + 5 where userid = 1; 
-- update characterdetails set coinwallet = coinwallet - 5 where userid = 2; 
-- update pets set userid = "2" where petid = "00000003"; -- trade R2D2
-- select * from characterdetails c inner join pets p on c.userid = p.userid;

-- select u.userid, c.characterid, c.health, c.coinwallet, c.currentpetid, c.image_url 
-- from userbase u 
-- inner join characterdetails c
-- on u.userid = c.userid
-- where u.email = "test@test.com";

-- select * from characterdetails;
-- select * from pets where userid = 1;
select * from characterdetails;

-- update characterdetails 
-- set health = 999,
-- coinwallet = 999,
-- currentpetid= 2, 
--  image_url = "character.avif"
-- where userid = 2;

select * from userbase where userid = "99b6c089";

-- only one enemy per user for now 
INSERT INTO enemy(enemyid, userid, health, damage) 
VALUES(1, "98c2ba96", 99, 10);
INSERT INTO enemy(enemyid, userid, health, damage) 
VALUES(2, "98c2bce4", 66, 10);