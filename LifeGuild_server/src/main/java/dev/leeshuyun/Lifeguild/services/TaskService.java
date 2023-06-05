package dev.leeshuyun.Lifeguild.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import dev.leeshuyun.Lifeguild.models.Daily;
import dev.leeshuyun.Lifeguild.models.Habit;
import dev.leeshuyun.Lifeguild.models.Reward;
import dev.leeshuyun.Lifeguild.models.Task;
import dev.leeshuyun.Lifeguild.models.ToDo;
import dev.leeshuyun.Lifeguild.repositories.TaskRepository;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepo;

    public Task getAllTasksByUserId(String userid) {
        List<Habit> habits = taskRepo.getHabitsByUserId(userid);
        List<Daily> dailies = taskRepo.getDailiesByUserId(userid);
        List<ToDo> todos = taskRepo.getTodosByUserId(userid);
        List<Reward> rewards = taskRepo.getRewardsByUserId(userid);

        return new Task(habits, dailies, todos, rewards);
    }

    public boolean spendCoin(String userid, int amountSpent) {
        return taskRepo.spendCoin(userid, amountSpent);
    }

    public int addToDo(ToDo todo) {
        return taskRepo.addToDo(todo);
    }

    public int addHabit(Habit habit) {
        return taskRepo.addHabit(habit);
    }

    public int addReward(Reward reward) {
        return taskRepo.addReward(reward);
    }

    public int addDaily(Daily daily) {
        return taskRepo.addDaily(daily);
    }

    public boolean deleteHabit(int habitid) {
        return taskRepo.deleteHabit(habitid);
    }

    public boolean deleteToDo(int todoid) {
        return taskRepo.deleteToDo(todoid);
    }

    public boolean deleteReward(int rewardId) {
        return taskRepo.deleteReward(rewardId);
    }

    public boolean deleteDaily(int dailyid) {
        return taskRepo.deleteDaily(dailyid);
    }

    public boolean updateHabit(Habit habit) {
        return taskRepo.updateHabit(habit);
    }

    public boolean updateDaily(Daily daily) {
        return taskRepo.updateDaily(daily);
    }

    public boolean updateReward(Reward reward) {
        return taskRepo.updateReward(reward);
    }

    public boolean updateToDo(ToDo todo) {
        return taskRepo.updateToDo(todo);
    }

}
