package dev.leeshuyun.Lifeguild.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {
  String userid;
  CharacterDetails characterDetails;
  Pet currentpet; // yes we can only have 1 pet for now
  Task task;

  @Override
  public String toString() {
    return "Account [userid=%s, characterDetails=%s, currentpet=%s, task=%]"
        .formatted(userid, characterDetails.toString(), currentpet.toString(), "task has no toString");
  }
}
