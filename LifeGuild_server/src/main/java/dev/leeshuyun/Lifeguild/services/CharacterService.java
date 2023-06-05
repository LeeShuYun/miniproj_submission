package dev.leeshuyun.Lifeguild.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.leeshuyun.Lifeguild.exceptions.UpdateCharacterAndPetException;
import dev.leeshuyun.Lifeguild.models.CharacterDetails;
import dev.leeshuyun.Lifeguild.models.Pet;
import dev.leeshuyun.Lifeguild.repositories.CharacterRepository;
import jakarta.json.JsonObject;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CharacterService {

    @Autowired
    private CharacterRepository charaRepo;

    public CharacterDetails getCharacterByUserId(String userid) {
        return charaRepo.getCharacterByUserId(userid);
    }

    public Pet getPetByUserId(String userid) {
        return charaRepo.getPetByUserid(userid);
    }

    // {"userid":2,"characterid":2,"health":100,
    // "coinwallet":75,"currentpetid":5,"image":"character.avif",
    // "isGameSectionLocked":true,"petimage":"cat.webp",
    // "enemyhealth":50,"enemyimage":"enemy.webp"}
    @Transactional(rollbackFor = UpdateCharacterAndPetException.class)
    public boolean updateCharacterAndPet(JsonObject jsonRequest) throws UpdateCharacterAndPetException {

        CharacterDetails character = CharacterDetails.builder()
                .userid(jsonRequest.getString("userid"))
                .characterid(jsonRequest.getInt("characterid"))
                .health(jsonRequest.getInt("health"))
                .coinwallet(jsonRequest.getInt("coinwallet"))
                .currentpetid(jsonRequest.getString("currentpetid"))
                .imageUrl(jsonRequest.getString("image"))
                .build();

        // TODO - the missing healing bug is affecting here too
        Pet pet = Pet.builder()
                .petid(jsonRequest.getString("currentpetid"))
                .userid(jsonRequest.getString("userid"))
                // .healing(jsonRequest.getInt("healing"))
                .healing(6)
                .image(jsonRequest.getString("petimage"))
                .build();

        log.info("updateCharacterAndPet>> {}", character.toString());
        log.info("updateCharacterAndPet>> {}", pet.toString());

        charaRepo.updateCharacter(character);
        charaRepo.updatePet(pet);

        return true;
    }
}
