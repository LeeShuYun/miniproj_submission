package dev.leeshuyun.Lifeguild.models;

import java.time.Instant;

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
public class TransactionPetAdoptionDetails {
    String fromUserId;
    String toUserId;
    String petId;
    int amount;

}
