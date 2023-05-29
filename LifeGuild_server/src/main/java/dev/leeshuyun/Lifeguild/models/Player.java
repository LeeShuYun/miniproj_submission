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
import lombok.ToString;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Player {
    int userid;
    int characterid;
    int health;
    int coinwallet;
    int currentpetid;
    String image;
    boolean isGameSectionLocked;
    String petimage;
    int pethealing;
    int enemyhealth;
    String enemyimage;

    private static Logger logger = LoggerFactory.getLogger(Reward.class);

    public static JsonObject stringToJSON(String str) {
        JsonReader reader = Json.createReader(new StringReader(str));
        return reader.readObject();
    }

}
