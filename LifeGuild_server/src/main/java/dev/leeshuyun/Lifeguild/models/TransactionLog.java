package dev.leeshuyun.Lifeguild.models;

import java.time.Instant;

import jakarta.json.Json;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonValue;
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
public class TransactionLog {
    String transactionId;
    int fromUserId;
    int toUserId;
    String petId;
    int amount;
    Instant timestamp;

    public JsonObjectBuilder toJSONObjBuilder() {
        return Json.createObjectBuilder()
                .add("transactionId", getTransactionId())
                .add("fromUserId", getFromUserId())
                .add("toUserId", getToUserId())
                .add("petId", getPetId())
                .add("amount", getAmount())
                .add("timestamp", getTimestamp().toString());
    }
}
