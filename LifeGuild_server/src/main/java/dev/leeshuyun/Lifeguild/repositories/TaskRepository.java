package dev.leeshuyun.Lifeguild.repositories;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import java.sql.PreparedStatement;
import java.sql.Statement;

import dev.leeshuyun.Lifeguild.models.Daily;
import dev.leeshuyun.Lifeguild.models.Habit;
import dev.leeshuyun.Lifeguild.models.Reward;
import dev.leeshuyun.Lifeguild.models.Task;
import dev.leeshuyun.Lifeguild.models.ToDo;
import lombok.extern.slf4j.Slf4j;

import static dev.leeshuyun.Lifeguild.repositories.SQLqueries.*;

@Slf4j
@Repository
public class TaskRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Create GeneratedKeyHolder object
    GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();

    public List<Habit> getHabitsByUserId(String userid) {
        SqlRowSet rs = jdbcTemplate.queryForRowSet(SQL_GET_HABITS_BY_USERID, userid);
        List<Habit> habits = new ArrayList<Habit>();
        while (rs.next()) {
            Habit habit = new Habit();
            habit.setUserid(rs.getString("userid"));
            habit.setHabitid(rs.getInt("habitid"));
            habit.setTitle(rs.getString("title"));
            habit.setIsGoodorBadHabit(rs.getString("is_good_or_bad_habit"));
            habit.setDifficulty(rs.getString("difficulty"));
            habit.setPositiveCount(rs.getInt("positive_count"));
            habit.setNegativeCount(rs.getInt("negative_count"));
            habit.setDateCreated(rs.getDate("date_created"));
            log.info(habit.toString());
            habits.add(habit);
        }
        return habits;
    }

    public List<ToDo> getTodosByUserId(String userid) {
        SqlRowSet rs = jdbcTemplate.queryForRowSet(SQL_GET_TODOS_BY_USERID, userid);
        List<ToDo> todos = new ArrayList<ToDo>();

        while (rs.next()) {
            ToDo todo = new ToDo();
            todo.setUserid(rs.getString("userid"));
            todo.setTodoid(rs.getInt("todoid"));
            todo.setTitle(rs.getString("title"));
            todo.setDifficulty(rs.getString("difficulty"));
            todo.setDueDate(rs.getDate("due_date"));
            todo.setPriority(rs.getString("priority"));
            todo.setIsComplete(rs.getBoolean("is_complete"));
            todo.setDateCreated(rs.getDate("date_created"));
            log.info(todo.toString());
            todos.add(todo);
            System.out.println(todo.toString());
        }
        return todos;
    }

    public List<Daily> getDailiesByUserId(String userid) {
        SqlRowSet rs = jdbcTemplate.queryForRowSet(SQL_GET_DAILIES_BY_USERID, userid);
        List<Daily> dailies = new ArrayList<Daily>();
        while (rs.next()) {
            Daily daily = new Daily();
            daily.setUserid(rs.getString("userid"));
            daily.setDailyid(rs.getInt("dailyid"));
            daily.setTitle(rs.getString("title"));
            daily.setDifficulty(rs.getString("difficulty"));
            daily.setIsComplete(rs.getBoolean("is_complete"));
            daily.setDateCreated(rs.getDate("date_created"));
            log.info("dailies being fetcheed by userid", daily.toString());
            dailies.add(daily);
            System.out.println(daily.toString());
        }
        return dailies;
    }

    public List<Reward> getRewardsByUserId(String userid) {
        SqlRowSet rs = jdbcTemplate.queryForRowSet(SQL_GET_REWARDS_BY_USERID, userid);
        List<Reward> rewards = new ArrayList<Reward>();

        while (rs.next()) {
            Reward reward = new Reward();
            reward.setUserid(rs.getString("userid"));
            reward.setRewardid(rs.getInt("rewardid"));
            reward.setTitle(rs.getString("title"));
            reward.setCost(rs.getInt("cost"));
            reward.setDateCreated(rs.getDate("date_created"));
            log.info(reward.toString());
            rewards.add(reward);
        }
        return rewards;
    }

    public int addHabit(Habit habit) {
        log.info("inserting Habit: %s".formatted(habit.toJSONObjBuilder().build().toString()));

        int rowsAffected = jdbcTemplate.update(conn -> {
            PreparedStatement preparedStatement = conn.prepareStatement(SQL_INSERT_HABIT,
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, habit.getUserid());
            preparedStatement.setString(2, habit.getTitle());
            preparedStatement.setString(3, habit.getIsGoodorBadHabit());
            preparedStatement.setString(4, habit.getDifficulty());
            preparedStatement.setInt(5, habit.getPositiveCount());
            preparedStatement.setInt(6, habit.getNegativeCount());
            preparedStatement.setDate(7, new Date(System.currentTimeMillis()));

            return preparedStatement;

        }, generatedKeyHolder);

        // Get auto-incremented ID
        Integer id = generatedKeyHolder.getKey().intValue();

        log.info("rowsAffected = {}, habitid={}", rowsAffected, id);
        return id;
    }

    public int addReward(Reward reward) {
        log.info("inserting Reward: %s".formatted(reward.toJSONObjBuilder().build().toString()));
        int rowsAffected = jdbcTemplate.update(conn -> {
            PreparedStatement preparedStatement = conn.prepareStatement(SQL_INSERT_REWARD,
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, reward.getUserid());
            preparedStatement.setString(2, reward.getTitle());
            preparedStatement.setInt(3, reward.getCost());
            preparedStatement.setDate(4, new Date(System.currentTimeMillis()));

            return preparedStatement;

        }, generatedKeyHolder);
        Integer id = generatedKeyHolder.getKey().intValue();
        log.info("rowsAffected = {}, id={}", rowsAffected, id);

        return id;
    }

    public int addToDo(ToDo todo) {
        log.info("inserting ToDo: %s".formatted(todo.toString()));
        int rowsAffected = jdbcTemplate.update(conn -> {
            PreparedStatement preparedStatement = conn.prepareStatement(SQL_INSERT_TODO,
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, todo.getUserid());
            preparedStatement.setString(2, todo.getTitle());
            preparedStatement.setString(3, todo.getDifficulty());
            preparedStatement.setDate(4, todo.getDueDate());
            preparedStatement.setString(5, todo.getPriority());
            preparedStatement.setBoolean(6, todo.getIsComplete());
            preparedStatement.setDate(7, new Date(System.currentTimeMillis()));

            return preparedStatement;

        }, generatedKeyHolder);
        Integer id = generatedKeyHolder.getKey().intValue();
        log.info("rowsAffected = {}, id={}", rowsAffected, id);

        return id;
    }

    public int addDaily(Daily daily) {
        log.info("inserting daily: %s".formatted(daily.toString()));
        int rowsAffected = jdbcTemplate.update(conn -> {
            PreparedStatement preparedStatement = conn.prepareStatement(SQL_INSERT_DAILY,
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, daily.getUserid());
            preparedStatement.setString(2, daily.getTitle());
            preparedStatement.setString(3, daily.getDifficulty());
            preparedStatement.setBoolean(4, daily.getIsComplete());
            preparedStatement.setDate(5, new Date(System.currentTimeMillis()));

            return preparedStatement;

        }, generatedKeyHolder);
        Integer id = generatedKeyHolder.getKey().intValue();
        log.info("rowsAffected = {}, id={}", rowsAffected, id);

        return id;
    }

    public boolean spendCoin(String userid, int amountSpent) {

        return true;
    }

    public boolean deleteReward(int rewardId) {
        jdbcTemplate.update(SQL_DELETE_REWARD_BY_REWARDID, rewardId);
        log.info("reward with id {} deleted", rewardId);
        return true;
    }

    public boolean deleteHabit(int habitid) {
        jdbcTemplate.update(SQL_DELETE_HABIT_BY_HABITID, habitid);
        log.info("habit with id {} deleted", habitid);
        return true;
    }

    public boolean deleteToDo(int todoid) {
        jdbcTemplate.update(SQL_DELETE_TODO_BY_TODOID, todoid);
        log.info("todo with id {} deleted", todoid);
        return true;
    }

    public boolean deleteDaily(int dailyid) {
        jdbcTemplate.update(SQL_DELETE_DAILY_BY_DAILYID, dailyid);
        log.info("daily with id {} deleted", dailyid);
        return true;
    }

    public boolean updateDaily(Daily daily) throws DataAccessException {
        int rowsaffected;
        try {
            rowsaffected = jdbcTemplate.update(SQL_UPDATE_DAILY_BY_DAILYID,
                    daily.getTitle(),
                    daily.getDifficulty(),
                    daily.getIsComplete(),
                    daily.getDailyid());
        } catch (DataAccessException dex) {
            log.error(SQL_DELETE_DAILY_BY_DAILYID, dex.getMessage());
            return false;
        }
        return rowsaffected > 0 ? true : false;
    }

    public boolean updateReward(Reward reward) throws DataAccessException {
        int rowsaffected;
        try {
            rowsaffected = jdbcTemplate.update(SQL_UPDATE_REWARD_BY_REWARDID,
                    reward.getTitle(),
                    reward.getCost(),
                    reward.getRewardid());
        } catch (DataAccessException dex) {
            log.error(SQL_UPDATE_REWARD_BY_REWARDID, dex.getMessage());
            return false;
        }
        return rowsaffected > 0 ? true : false;
    }

    // TODO
    public boolean updateHabit(Habit habit) {
        log.info("updating habit {} ", habit.toString());
        int rowsaffected;
        try {
            rowsaffected = jdbcTemplate.update(SQL_UPDATE_HABIT_BY_HABITID,
                    habit.getTitle(),
                    habit.getIsGoodorBadHabit(),
                    habit.getDifficulty(),
                    habit.getPositiveCount(),
                    habit.getNegativeCount(),
                    habit.getHabitid());
        } catch (DataAccessException dex) {
            log.error(SQL_UPDATE_REWARD_BY_REWARDID, dex.getMessage());
            return false;
        }
        return rowsaffected > 0 ? true : false;
    }

    public boolean updateToDo(ToDo todo) {
        int rowsAffected;
        log.info("todo {} updated", todo.toString());
        try {
            rowsAffected = jdbcTemplate.update(SQL_UPDATE_TODO_BY_TODOID,
                    todo.getTitle(),
                    todo.getDifficulty(),
                    todo.getDueDate(),
                    todo.getPriority(),
                    todo.getIsComplete(),
                    todo.getTodoid());
        } catch (DataAccessException dex) {
            log.error(SQL_UPDATE_TODO_BY_TODOID, dex.getMessage());
            return false;
        }
        return rowsAffected > 0 ? true : false;
    }

}
