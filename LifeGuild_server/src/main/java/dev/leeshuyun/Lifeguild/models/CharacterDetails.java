package dev.leeshuyun.Lifeguild.models;

import jakarta.json.Json;
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
public class CharacterDetails {
    int userid;
    int characterid;
    // String charactername;
    int health;
    // int experience;
    // int mana;
    // int novicelevel;
    // int readerlevel;
    // int characterlevel;
    // int writerlevel;
    int coinwallet;
    int currentpetid;
    String imageUrl;
    // Weapon currentweapon;

    // default character custom constructor
    // this should be the only place we see this default
    public CharacterDetails(int characterid, int userid, int petid) {
        this.userid = userid;
        this.characterid = characterid;
        this.health = 100;
        this.coinwallet = 100; // let me just give you some money to start off with... it's too sad
        this.currentpetid = petid;
        this.imageUrl = "character.avif";
    }

    public JsonObjectBuilder toJSONObjBuilder() {
        return Json.createObjectBuilder()
                .add("userid", getUserid())
                .add("characterid", getCharacterid())
                .add("health", getHealth())
                .add("coinwallet", getCoinwallet())
                .add("currentpetid", getCurrentpetid())
                .add("imageUrl", "character.avif");
    }

}
