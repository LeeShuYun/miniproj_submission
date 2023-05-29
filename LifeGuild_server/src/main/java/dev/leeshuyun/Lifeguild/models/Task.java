package dev.leeshuyun.Lifeguild.models;

import java.io.StringReader;
import java.util.List;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    List<Habit> habits;
    List<Daily> dailies;
    List<ToDo> todos;
    List<Reward> rewards;

    /*
     * {
     * "habits" : [
     * {
     * title: string;
     * isGoodorBadHabit: string;
     * difficulty: string;
     * positiveCount: number;
     * negativeCount: number;
     * }
     * ],
     * * "todos" : [
     * {
     * title: string,
     * difficulty: string,
     * dueDate: Date,
     * priority: string,
     * isComplete: boolean,
     * }
     * ],
     * }
     * 
     */
    // @Override
    // public String toString() {
    // JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
    // String habitsStr = habits.stream()
    // .forEach(habit -> {
    // habit.toString();
    // })
    // .toList();
    // String dailiesStr = ;
    // String todosStr = ;
    // String rewardsStr = ;
    // return "Task{habits=%s, dailies=%s, todos=%s, rewards=%s}"
    // .formatted(habitsStr , dailiesStr, todosStr, rewardsStr);
    // }

    public static JsonObject toJSON(String str) {
        JsonReader reader = Json.createReader(new StringReader(str));
        return reader.readObject();
    }

    // public static Task createTaskFromJsonStr(String str) {
    // ArrayList<Habit> habits = str.getString("lastname");
    // ArrayList<Habit> dailies = str.getString("lastname");
    // ArrayList<Habit> todos = str.getString("lastname");
    // ArrayList<Habit> rewards = str.getString("lastname");

    // Task task = Task.builder()
    // .habits(habits)
    // .dailies(dailies)
    // .todos(todos)
    // .rewards(rewards)
    // .build();
    // return task;
    // }

    // public static Task createTaskFromJsonObj(JsonObject str) {
    // JsonObject json = toJSON(str);
    // }

    // public JsonObject taskToJSON() {
    // return Json.createObjectBuilder()
    // .add("habits", getHabits())
    // .add("dailies", getDailies())
    // .add("todos", getTodos())
    // .add("rewards", getRewards())
    // .build();
    // }
}
