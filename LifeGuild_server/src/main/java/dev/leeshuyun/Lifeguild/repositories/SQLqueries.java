package dev.leeshuyun.Lifeguild.repositories;

public class SQLqueries {
        public static final String SQL_GET_REWARDS_BY_USERID = """
                        select * from rewards where userid = ?;
                                """;

        public static final String SQL_INSERT_HABIT = """
                        INSERT INTO habits(userid, title, is_good_or_bad_habit, difficulty,
                        positive_count, negative_count, date_created)
                        VALUES(?, ?, ?, ?, ?, ?, ?);
                                """;
        public static final String SQL_DELETE_HABIT_BY_HABITID = """
                        delete from habits where habitid = ?;
                        """;

        public static final String SQL_DELETE_TODO_BY_TODOID = """
                        delete from todos where todoid = ?;
                        """;
        public static final String SQL_DELETE_DAILY_BY_DAILYID = """
                        delete from dailies where dailyid = ?;
                        """;

        public static final String SQL_UPDATE_HABIT_BY_HABITID = """
                        update habits set title = ?,
                        is_good_or_bad_habit = ?,
                        difficulty = ?,
                        positive_count = ?,
                        negative_count = ?
                        where habitid = ?;
                                """;
        public static final String SQL_UPDATE_DAILY_BY_DAILYID = """
                        update dailies
                        set title = ?,
                        difficulty = ?,
                        is_complete = ?
                        where dailyid = ?;
                                        """;
        public static final String SQL_UPDATE_REWARD_BY_REWARDID = """
                        update rewards
                        set title = ?,
                        cost = ?
                        where rewardid = ?;
                                                """;
        public static final String SQL_UPDATE_TODO_BY_TODOID = """
                        update todos
                        set title = ?,
                        difficulty = ?,
                        due_date = ?,
                        priority = ?,
                        is_complete = ?
                        where todoid = ?;
                                                        """;

        public static final String SQL_DELETE_REWARD_BY_REWARDID = """
                        delete from rewards where rewardid = ?;
                                        """;
        public static final String SQL_INSERT_TODO = """
                        INSERT INTO todos (userid, title, difficulty, due_date, priority, is_complete, date_created)
                        VALUES(?, ?, ?, ?, ?, ?, ?);
                            """;
        public static final String SQL_INSERT_DAILY = """
                        INSERT INTO dailies(userid, title, difficulty, is_complete,date_created)
                        VALUES(?, ?, ?, ?, ?);
                                """;
        public static final String SQL_INSERT_REWARD = """
                        INSERT INTO rewards(userid,title,cost, date_created)
                        VALUES(?, ?, ?, ?);
                            """;
        public static final String SQL_GET_HABITS_BY_USERID = """
                        select * from habits where userid = ?;
                            """;

        public static final String SQL_GET_TODOS_BY_USERID = """
                        select * from todos where userid = ?;
                                """;
        public static final String SQL_GET_DAILIES_BY_USERID = """
                        select * from dailies where userid = ?;
                        """;

        public static final String SQL_GET_USER_BY_USERID = """
                        select * from userbase where userid = ?
                        """;
        public static final String SQL_UPDATE_USER_WALLET = """
                        update characterdetails set coinwallet = coinwallet + ? where userid = ?;
                                            """;
        public static final String SQL_UPDATE_PET_OWNER = """
                        update pets set userid = ? where petid = ?;
                                                    """;
        public static final String SQL_UPDATE_CHARACTER_PETID = """
                        update characterdetails set currentpetid = ? where userid = ?;
                                """;
        public static final String SQL_GET_CHARACTER_BY_EMAIL = """
                        select * from characterdetails where email = ?;
                                """;
        public static final String SQL_GET_CHARACTER_BY_USERID = """
                        select * from characterdetails where userid = ?;
                                """;
        public static final String SQL_GET_USER_BY_EMAIL = """
                        select * from userbase where email = ?;
                                """;
        public static final String SQL_GET_ALL_PETS_BY_USERID = """
                        select * from pets where userid = ?
                        """;

        public static final String SQL_INSERT_PET = """
                                INSERT INTO pets(userid, healingamount, image) VALUES (?, ?, ?);
                        """;
        public static final String SQL_INSERT_DEFAULT_CHARACTER = """
                                INSERT INTO characterdetails (
                                        userid, health, coinwallet, currentpetid, image_url) VALUES(?,100,100,?,?);
                        """;
        public static final String SQL_UPDATE_USER_EMAIL_CONFIRMED = """
                        update userbase set isemailconfirmed = true where email = ?;
                                """;
        public static final String SQL_INSERT_NEW_USER = """
                        INSERT INTO userbase(firstname,lastname,email,username,userpassword,userrole,confirmationcode, isemailconfirmed, isgooglelogin, telegram_chatid, dateregistered)
                        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);
                                        """;
        public static final String SQL_UPDATE_USER_TELEGRAM_CONFIRMED = """
                        update userbase set telegram_chatid = ? where email = ?;
                                """;
        public static final String SQL_GET_ALL_MARKETPLACEPETS = """
                        select * from petmarketplace;
                                """;
        public static final String SQL_DELETE_PET_FROM_MARKETPLACE = """
                        delete from petmarketplace where petid = ?;
                                        """;
        public static final String SQL_UPDATE_ENTIRE_CHARACTER = """
                        update characterdetails
                        set health = ?,
                        coinwallet = ?,
                        currentpetid = ?,
                        image_url = ?
                        where userid = ?;
                                                """;
        public static final String SQL_UPDATE_PET = """
                        update pets
                        set userid = ?,
                        healingamount = ?,
                        image = ?
                        where petid = ?;
                                                        """;

}
