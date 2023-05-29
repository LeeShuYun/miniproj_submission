package dev.leeshuyun.Lifeguild.utils;

import java.io.StringReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import dev.leeshuyun.Lifeguild.models.Review;
import dev.leeshuyun.Lifeguild.models.Task;
import jakarta.json.JsonObject;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;

public class Utils {
    private final static Logger logger = LoggerFactory.getLogger(Utils.class);

    public static JsonObjectBuilder reviewToJson(Review review) {
        // System.out.println("reviewToJson()>>>" + review.getTitle());
        return Json.createObjectBuilder()
                .add("title", review.getTitle())
                .add("rating", review.getRating())
                .add("byline", review.getByline())
                .add("headline", review.getHeadline())
                .add("summary", review.getSummary())
                .add("reviewURL", review.getReviewURL())
                .add("image", review.getImage())
                .add("commentCount", review.getCommentCount());
    }

    public static JsonObjectBuilder taskToJsonObjBuilder(Task task) {

        logger.info("taskToJsonObjBuilder>> unpacking tasks"
                .formatted());

        JsonArrayBuilder habitArrBuilder = Json.createArrayBuilder();
        JsonArrayBuilder dailyArrBuilder = Json.createArrayBuilder();
        JsonArrayBuilder todoArrBuilder = Json.createArrayBuilder();
        JsonArrayBuilder rewardArrBuilder = Json.createArrayBuilder();
        task.getHabits().stream()
                .forEach(habit -> {
                    habitArrBuilder.add(habit.toJSONObjBuilder());
                });
        task.getDailies().stream()
                .forEach(daily -> {
                    dailyArrBuilder.add(daily.toJSONObjBuilder());
                });
        task.getTodos().stream()
                .forEach(todo -> {
                    todoArrBuilder.add(todo.toJSONObjBuilder());
                });
        task.getRewards().stream()
                .forEach(reward -> {
                    rewardArrBuilder.add(reward.toJSONObjBuilder());
                });

        return Json.createObjectBuilder()
                .add("habits", habitArrBuilder)
                .add("dailies", dailyArrBuilder)
                .add("todos", todoArrBuilder)
                .add("rewards", rewardArrBuilder);
    }

    public static JsonObject stringToJSON(String str) {
        JsonReader reader = Json.createReader(new StringReader(str));
        return reader.readObject();
    }
}
