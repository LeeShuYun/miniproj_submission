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
INSERT INTO userbase(firstname,lastname,email,username,userpassword,userrole,confirmationcode, isemailconfirmed, isgooglelogin, telegram_chatid, dateregistered) -- admin
VALUES ("tester", "admin", "admin@lifeguild.fun", "admin", "lolololol", "ADMIN", 999999, true, false, "460909373", "2023-05-01");
INSERT INTO userbase(firstname,lastname,email,username,userpassword,userrole,confirmationcode, isemailconfirmed,isgooglelogin, telegram_chatid, dateregistered) -- guest acc
VALUES ("testfirstname", "testlastname", "test@test.com", "guest", "testtesttest", "PLAYER", 999999, true, false, "460909373", "2023-05-01");
INSERT INTO userbase(firstname,lastname,email,username,userpassword,userrole,confirmationcode, isemailconfirmed,isgooglelogin, telegram_chatid, dateregistered)
VALUES ("fred", "flintstone", "fred@gmail.com", "fred" , "password", "PLAYER", 999999, true, true, "460909373", "2023-05-01");
INSERT INTO userbase(firstname,lastname,email,username,userpassword,userrole,confirmationcode, isemailconfirmed,isgooglelogin, telegram_chatid, dateregistered)
VALUES ("testtestfirst", "testtest", "email2@gmail.com", "codeMonkey", "testpassword", "MODERATOR", 999999, true, true, "460909373", "2023-05-01");

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
INSERT INTO pets( userid, healingamount, image) VALUES ( 1, 7, "cat.webp");
INSERT INTO pets(userid, healingamount, image) VALUES (  1, 10, "R2D2.avif");
INSERT INTO pets(userid, healingamount, image) VALUES ( 1, 11, "kirby.webp");
INSERT INTO pets(userid, healingamount, image) VALUES ( 1, 16, "dog.webp");
INSERT INTO pets(userid, healingamount, image) VALUES ( 1, 5, "cat.webp");
INSERT INTO pets(userid, healingamount, image) VALUES ( 1, 20, "dog.webp");
INSERT INTO pets(userid, healingamount, image) VALUES ( 1, 11, "kirby.webp");
INSERT INTO pets(userid, healingamount, image) VALUES ( 1, 16, "dog.webp");
INSERT INTO pets(userid, healingamount, image) VALUES ( 1, 5, "cat.webp");
INSERT INTO pets(userid, healingamount, image) VALUES (  1, 20, "dog.webp");
INSERT INTO pets(userid, healingamount, image) VALUES (  1, 16, "dog.webp");
INSERT INTO pets(userid, healingamount, image) VALUES (  1, 5, "cat.webp");
INSERT INTO pets(userid, healingamount, image) VALUES (  1, 20, "dog.webp");
INSERT INTO pets(userid, healingamount, image) VALUES (  1, 11, "kirby.webp");
INSERT INTO pets(userid, healingamount, image) VALUES (  1, 16, "dog.webp");
INSERT INTO pets(userid, healingamount, image) VALUES ( 1, 5, "cat.webp");
INSERT INTO pets(userid, healingamount, image) VALUES (  1, 20, "dog.webp");

-- pets for guest acc
-- when userid  = 0, the pet has no owner and is eligible for adoption. money goes to admin
-- INSERT INTO pets(userid, healingamount, image) VALUES ( "2",  5, "dog.webp");
-- INSERT INTO pets( userid, healingamount, image) VALUES ( "2",  5, "cat.webp");
INSERT INTO pets(userid, healingamount, image) VALUES ( 0, 7, "R2D2.avif");
INSERT INTO pets( userid, healingamount, image) VALUES ( 2, 15, "kirby.avif");
INSERT INTO pets(userid, healingamount, image) VALUES (3, 10, "kirby.avif");
select * from pets where userid =2;
-- update pets
-- set healingamount= 100,
-- image = "kirby.avif"
-- where petid = 19;s

-- update pets 
-- set userid = 2, 
-- healingamount = 10, 
-- image = "kirby.avif"
-- where petid = 16; 

-- should be same as the existing amount of pets owned by admin FOR NOW
INSERT INTO petmarketplace(petid, userid, healingamount, image) VALUES (1, 1, 7, "cat.webp");
INSERT INTO petmarketplace(petid,userid, healingamount, image) VALUES ( 2, 1, 10, "R2D2.avif");
INSERT INTO petmarketplace(petid,userid, healingamount, image) VALUES ( 3, 1, 11, "kirby.webp");
INSERT INTO petmarketplace(petid,userid, healingamount, image) VALUES (4,  1, 16, "dog.webp");
INSERT INTO petmarketplace(petid,userid, healingamount, image) VALUES ( 5, 1, 5, "cat.webp");
INSERT INTO petmarketplace(petid,userid, healingamount, image) VALUES (6,  1, 20, "dog.webp");
INSERT INTO petmarketplace(petid,userid, healingamount, image) VALUES ( 7, 1, 11, "kirby.webp");
INSERT INTO petmarketplace(petid,userid, healingamount, image) VALUES ( 8, 1, 16, "dog.webp");
INSERT INTO petmarketplace(petid,userid, healingamount, image) VALUES ( 9, 1, 5, "cat.webp");
INSERT INTO petmarketplace(petid,userid, healingamount, image) VALUES (10,  1, 20, "dog.webp");
INSERT INTO petmarketplace(petid,userid, healingamount, image) VALUES (11,  1, 16, "dog.webp");
INSERT INTO petmarketplace(petid,userid, healingamount, image) VALUES ( 12, 1, 5, "cat.webp");
INSERT INTO petmarketplace(petid,userid, healingamount, image) VALUES (13,  1, 20, "dog.webp");
INSERT INTO petmarketplace(petid,userid, healingamount, image) VALUES (14,  1, 11, "kirby.webp");
INSERT INTO petmarketplace(petid,userid, healingamount, image) VALUES (15,  1, 16, "dog.webp");
INSERT INTO petmarketplace(petid,userid, healingamount, image) VALUES (16, 1, 5, "cat.webp");
INSERT INTO petmarketplace(petid,userid, healingamount, image) VALUES ( 17, 1, 20, "dog.webp");
select * from petmarketplace;
-- delete from petmarketplace where petid = 15;

-- pet blueprints, do not change
-- acts as storefront
INSERT INTO petblueprints(petid, species, healingamount, image) VALUES ("00000001", "dog", 5, "dog.webp");
INSERT INTO petblueprints(petid, species, healingamount, image) VALUES ("00000002", "cat", 5, "cat.webp");
INSERT INTO petblueprints(petid, species, healingamount, image) VALUES ("00000003", "special", 10, "kirby.avif");
INSERT INTO petblueprints(petid, species, healingamount, image) VALUES ("00000004", "special", 7, "R2D2.avif");
-- INSERT INTO petblueprints(petid, species, healingamount, image) VALUES ("00000005", "dragon", 15, "dragon.avif"); -- starter pet don't need 

select * from pets;
-- delete from pets where petid = "00000010";



INSERT INTO dailies(userid, title, difficulty, is_complete,date_created) 
VALUES(1, "drink 1.5l water", "low", false, "2023-5-11");
INSERT INTO dailies(userid, title, difficulty, is_complete, date_created) 
VALUES(2, "check email", "low", true, "2023-5-11");
INSERT INTO dailies(userid, title, difficulty, is_complete, date_created) 
VALUES(1, "30 minutes exercise", "med", false, "2023-5-11");
INSERT INTO dailies(userid, title, difficulty, is_complete, date_created) 
VALUES(1, "take dog out for a walk", "med", true, "2023-5-11");
INSERT INTO dailies(userid, title, difficulty, is_complete, date_created) 
VALUES(2, "do some code", "low", false, "2023-5-11");

-- select * from dailies;
select * from dailies where userid = 2;
-- update dailies 
-- set title = "new title", 
-- difficulty = "high",
-- is_complete = 1
-- where dailyid = 10; 

INSERT INTO rewards(userid, title,cost, date_created) 
VALUES(1, "Watch a show", 30, "2023-5-11");
INSERT INTO rewards(userid, title,cost, date_created) 
VALUES(2, "buy that overly expensive thing that makes you happy", 20, "2023-5-11");
INSERT INTO rewards(userid,title,cost, date_created) 
VALUES(2, "Watch a show", 15, "2023-5-11");
INSERT INTO rewards(userid,title,cost, date_created) 
VALUES(2, "get a snack", 10, "2023-5-11");
INSERT INTO rewards(userid,title,cost, date_created) 
VALUES(1, "take a nice evening walk", 5, "2023-5-11");
INSERT INTO rewards(userid,title,cost,date_created) 
VALUES(1, "buy that overly expensive thing you shouldn't be buying but makes you happy", 20, "2023-5-11");
select * from rewards;
-- select * from rewards where userid = 1;
-- delete from rewards where rewardid = 2;
-- update rewards 
-- set title = "new title", 
-- cost = 20
-- where rewardid = 2; 


INSERT INTO todos (userid, title, difficulty, due_date, priority, is_complete, date_created) 
VALUES(1, "get tickets to the aquarium", "low", "2023-5-15", "med", false, "2023-5-11");
INSERT INTO todos (userid, title, difficulty, due_date, priority, is_complete, date_created) 
VALUES(1, "finish project", "high", "2023-5-28", "high", false, "2023-5-12");
INSERT INTO todos (userid, title, difficulty, due_date, priority, is_complete, date_created) 
VALUES(1, "get groceries", "med", "2023-5-18", "high", false, "2023-5-13");
INSERT INTO todos (userid, title, difficulty, due_date, priority, is_complete, date_created) 
VALUES(2, "finish project", "high", "2023-5-15", "med", false, "2023-5-11");
INSERT INTO todos (userid, title, difficulty, due_date, priority, is_complete, date_created) 
VALUES(2, "read a book", "high", "2023-5-28", "high", false, "2023-5-12");
INSERT INTO todos (userid, title, difficulty, due_date, priority, is_complete, date_created) 
VALUES(2, "get some sleep", "med", "2023-5-18", "high", false, "2023-5-13");

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
VALUES(1, "leaning on elbows", "bad", "low", 2, 5, "2023-5-15");
INSERT INTO habits(userid, title, is_good_or_bad_habit, difficulty, positive_count, negative_count, date_created) 
VALUES(1, "sitting up straight", "good", "low", 3, 1, "2023-5-12");
INSERT INTO habits(userid, title, is_good_or_bad_habit, difficulty, positive_count, negative_count, date_created) 
VALUES(1, "stay focused", "both", "low", 5, 1, "2023-5-10");
INSERT INTO habits(userid, title, is_good_or_bad_habit, difficulty, positive_count, negative_count, date_created) 
VALUES(2, "leaning on elbows", "bad", "low", 2, 5, "2023-5-15");
INSERT INTO habits(userid, title, is_good_or_bad_habit, difficulty, positive_count, negative_count, date_created) 
VALUES(2, "sitting up straight", "good", "low", 3, 1, "2023-5-12");
INSERT INTO habits(userid, title, is_good_or_bad_habit, difficulty, positive_count, negative_count, date_created) 
VALUES(2, "stay focused", "both", "low", 5, 1, "2023-5-10");
-- select * from habits;
-- delete from habits where habitid = 4;
select * from habits where userid = 2;

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
1, 100,
100,  
1,
"character.avif"
);
INSERT INTO characterdetails (
userid, health,
coinwallet,
currentpetid, 
image_url
) VALUES(
2, 100,
100,  
2,
"character.avif"
);

INSERT INTO characterdetails (
userid, health,
coinwallet,
currentpetid, 
image_url
) VALUES(
3, 89,
99,  
3,
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



INSERT INTO enemy(enemyid, userid, health, damage) 
VALUES(1, 1, 99, 10);
INSERT INTO enemy(enemyid, userid, health, damage) 
VALUES(2, 2, 66, 10);