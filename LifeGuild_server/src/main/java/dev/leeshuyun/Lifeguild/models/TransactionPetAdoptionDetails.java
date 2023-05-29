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
    int fromUserId;
    int toUserId;
    int petId;
    int amount;

}
