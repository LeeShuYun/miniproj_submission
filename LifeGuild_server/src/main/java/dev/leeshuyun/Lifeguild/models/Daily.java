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
public class Daily {
    int userid;
    int dailyid;
    String title; // description
    String difficulty; // high med low
    Boolean isComplete; // resets everyday
    Date dateCreated;
    /*
     * {
     * "dailies": []
     * }
     */

    @Override
    public String toString() {
        return "Daily [userid=%d,dailyid=%d,title=%s, difficulty=%s, isComplete=%s, dateCreated=%s]"
                .formatted(userid, dailyid, title, difficulty, isComplete.toString(), dateCreated.toString());
    }

    public static Daily createDailyFromJsonObj(JsonObject jsonObj) {
        Daily daily = Daily.builder()
                .userid(Integer.valueOf(jsonObj.getString("userid")))
                .dailyid(Integer.valueOf(jsonObj.getInt("dailyid")))
                .title(jsonObj.getString("title"))
                .difficulty(jsonObj.getString("difficulty"))
                .isComplete(jsonObj.getBoolean("isComplete"))
                .dateCreated(Date.valueOf(jsonObj.getString("dateCreated")))
                .build();
        return daily;
    }

    public JsonObjectBuilder toJSONObjBuilder() {
        return Json.createObjectBuilder()
                .add("userid", getUserid())
                .add("dailyid", getDailyid())
                .add("title", getTitle())
                .add("difficulty", getDifficulty())
                .add("isComplete", getIsComplete())
                .add("dateCreated", getDateCreated().toString());
    }
}
