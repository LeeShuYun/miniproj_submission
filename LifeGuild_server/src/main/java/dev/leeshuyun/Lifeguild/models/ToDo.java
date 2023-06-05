package dev.leeshuyun.Lifeguild.models;

import java.sql.Date;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
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
public class ToDo {
    String userid;
    int todoid;
    String title;
    String difficulty;
    Date dueDate; // yyyy-mm-dd. sorted nearest time at the top. new ones stay at to
    String priority; // high med low, sorted nearest time first then priority
    Boolean isComplete;
    Date dateCreated;

    // @Override
    // public String toString() {
    // return "ToDo [userid=%d, todoid=%d, title=%s,difficulty=%s,dueDate=%s,
    // priority=%s, isComplete=%s, dateCreated=%s]"
    // .formatted(userid, todoid, title, difficulty, dueDate.toString(), priority,
    // isComplete.toString(),
    // dateCreated.toString());
    // }

    public static ToDo createDailyFromJsonObj(JsonObject jsonObj) {
        ToDo todo = ToDo.builder()
                .userid(jsonObj.getString("userid"))
                .todoid(jsonObj.getInt("todoid"))
                .title(jsonObj.getString("title"))
                .difficulty(jsonObj.getString("difficulty"))
                .dueDate(Date.valueOf(jsonObj.getString("dueDate")))
                .priority(jsonObj.getString("priority"))
                .isComplete(jsonObj.getBoolean("isComplete"))
                .dateCreated(Date.valueOf(jsonObj.getString("dateCreated")))
                .build();
        return todo;
    }

    public JsonObjectBuilder toJSONObjBuilder() {
        return Json.createObjectBuilder()
                .add("userid", getUserid())
                .add("todoid", getTodoid())
                .add("title", getTitle())
                .add("difficulty", getDifficulty())
                .add("dueDate", getDueDate().toString())
                .add("priority", getPriority())
                .add("isComplete", getIsComplete())
                .add("dateCreated", getDateCreated().toString());
    }
}
