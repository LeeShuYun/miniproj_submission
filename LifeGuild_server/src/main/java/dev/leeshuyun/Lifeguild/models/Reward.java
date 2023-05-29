package dev.leeshuyun.Lifeguild.models;

import java.io.StringReader;
import java.sql.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonReader;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reward {
    int userid;
    int rewardid;
    String title;
    int cost;
    Date dateCreated;

    private static Logger logger = LoggerFactory.getLogger(Reward.class);

    @Override
    public String toString() {
        return "Reward [userid=%d, rewardid=%d, title=%s,cost=%d,dateCreated=%s]"
                .formatted(userid, rewardid, title, cost, dateCreated.toString());
    }

    public static JsonObject stringToJSON(String str) {
        JsonReader reader = Json.createReader(new StringReader(str));
        return reader.readObject();
    }

    public JsonObjectBuilder toJSONObjBuilder() {
        return Json.createObjectBuilder()
                .add("userid", getUserid())
                .add("rewardid", getRewardid())
                .add("title", getTitle())
                .add("cost", getCost())
                .add("dateCreated", getDateCreated().toString());
    }

    public static Reward createRewardFromJsonObj(JsonObject jsonObj) {
        logger.error("createRewardFromJsonObj>> " + jsonObj.getString("dateCreated"));
        Reward reward = Reward.builder()
                .userid(Integer.valueOf(jsonObj.getString("userid")))
                .rewardid(jsonObj.getInt("rewardid"))
                .title(jsonObj.getString("title"))
                .cost(jsonObj.getInt("cost"))
                .dateCreated(Date.valueOf(jsonObj.getString("dateCreated")))
                .build();
        return reward;
    }



}
