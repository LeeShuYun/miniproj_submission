package dev.leeshuyun.Lifeguild.models;

import java.sql.Date;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Habit {
    int userid;
    int habitid;
    String title;
    String isGoodorBadHabit; // good, bad, both
    String difficulty; // high low med
    int positiveCount; // counts how many times you did positively
    int negativeCount; // counts how many times you did negatively
    Date dateCreated;

    @Override
    public String toString() {
        return "Habit [userid=%s, habitid=%s, title=%s,isGoodorBadHabit=%s,difficulty=%s, positiveCount=%d, negativeCount=%d, dateCreated=%s]"
                .formatted(userid, habitid, title, isGoodorBadHabit, difficulty, positiveCount, negativeCount,
                        dateCreated.toString());
    }

    public static Habit createDailyFromJsonObj(JsonObject jsonObj) {
        Habit habit = Habit.builder()
                .userid(jsonObj.getInt("userid"))
                .habitid(jsonObj.getInt("habitid"))
                .title(jsonObj.getString("title"))
                .isGoodorBadHabit(jsonObj.getString("isGoodorBadHabit"))
                .difficulty(jsonObj.getString("difficulty"))
                .positiveCount(jsonObj.getInt("positiveCount"))
                .negativeCount(jsonObj.getInt("negativeCount"))
                .dateCreated(Date.valueOf(jsonObj.getString("dateCreated")))
                .build();
        return habit;
    }

    public JsonObjectBuilder toJSONObjBuilder() {
        return Json.createObjectBuilder()
                .add("userid", getUserid())
                .add("habitid", getHabitid())
                .add("title", getTitle())
                .add("isGoodorBadHabit", getIsGoodorBadHabit())
                .add("difficulty", getDifficulty())
                .add("positiveCount", getPositiveCount())
                .add("negativeCount", getNegativeCount())
                .add("dateCreated", getDateCreated().toString());
    }

}
