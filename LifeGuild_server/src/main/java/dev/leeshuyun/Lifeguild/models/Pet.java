package dev.leeshuyun.Lifeguild.models;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
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
public class Pet {
    int petid;
    int userid;
    // String petname;
    // String species;
    int healing;
    String image;

    // custom default Pet constructor
    public Pet(int userid) {
        this.petid = 0;
        this.userid = userid;
        this.healing = 15;
        this.image = "dragon.avif";
    }

    public static Pet createPetFromJsonObj(JsonObject jsonObj) {
        Pet pet = Pet.builder()
                .petid(jsonObj.getInt("petid"))
                .userid(jsonObj.getInt("userid"))
                // .petname(jsonObj.getString("petname"))
                // .species(jsonObj.getString("species"))
                .healing(jsonObj.getInt("healing"))
                .image(jsonObj.getString("image"))
                .build();
        return pet;
    }

    public JsonObjectBuilder toJSONObjBuilder() {
        return Json.createObjectBuilder()
                .add("petId", getPetid())
                .add("userid", getUserid())
                // .add("petname", getPetname())
                // .add("species", getSpecies())
                .add("healing", getHealing())
                .add("image", getImage());
    }

}
