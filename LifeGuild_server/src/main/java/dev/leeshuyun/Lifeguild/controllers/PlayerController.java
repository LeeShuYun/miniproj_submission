package dev.leeshuyun.Lifeguild.controllers;

import java.util.List;

import org.apache.coyote.Response;
import org.glassfish.json.JsonUtil;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import dev.leeshuyun.Lifeguild.exceptions.TradePetFailedException;
import dev.leeshuyun.Lifeguild.exceptions.UpdateCharacterAndPetException;
import dev.leeshuyun.Lifeguild.models.CharacterDetails;
import dev.leeshuyun.Lifeguild.models.Pet;
import dev.leeshuyun.Lifeguild.models.Task;
import dev.leeshuyun.Lifeguild.models.TransactionLog;
import dev.leeshuyun.Lifeguild.models.TransactionPetAdoptionDetails;
import dev.leeshuyun.Lifeguild.repositories.TransactionLogRepository;
import dev.leeshuyun.Lifeguild.utils.Utils;
import jakarta.json.JsonObjectBuilder;
import lombok.extern.slf4j.Slf4j;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import dev.leeshuyun.Lifeguild.services.CharacterService;
import dev.leeshuyun.Lifeguild.services.PetAdoptionService;
import dev.leeshuyun.Lifeguild.services.TaskService;

@Slf4j
@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = "/api/player", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class PlayerController {

    @Autowired
    private CharacterService charaSvc;

    @Autowired
    private PetAdoptionService petSvc;

    @Autowired
    private TransactionLogRepository txnLogRepo;

    // DONE
    @PostMapping(path = "/{userid}")
    public ResponseEntity<String> getCharacterAndPetDetails(@PathVariable int userid) {
        log.info("/api/player/{userid}: getting chara for userid: %s".formatted(userid));

        CharacterDetails chara = charaSvc.getCharacterByUserId(userid);

        // JsonObjectBuilder arrBuilder = chara.toJSONObjBuilder();
        JsonObjectBuilder objBuilder = Json.createObjectBuilder()
                .add("character", chara.toJSONObjBuilder());

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(objBuilder.build().toString());
    }

    // DONE
    @PutMapping(path = "/tradepet", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> adoptPet(@RequestBody TransactionPetAdoptionDetails txnDetails) {

        log.info("/api/player/tradepet %s".formatted(txnDetails.toString()));

        // log.info("prepared transaction details", txnDetails.toString());
        JsonObjectBuilder response = Json.createObjectBuilder();

        try {
            petSvc.tradePet(txnDetails);
            response.add("isSuccess", true);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response.build().toString());
        } catch (TradePetFailedException tpfe) {

            response.add("error", "trade pet failed");
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response.build().toString());
        }

    }

    // DONE
    // private endpt not for users
    @GetMapping(path = "/transactions/{userid}")
    public ResponseEntity<String> getTransactionLogByUserId(@PathVariable int userid) {
        log.info("/api/player/transactions/{userid} %s".formatted(userid));

        List<TransactionLog> txns = txnLogRepo.getTransactionsByUserid(userid);

        JsonArrayBuilder response = Json.createArrayBuilder();
        txns.stream()
                .forEach(txn -> {
                    response.add(txn.toJSONObjBuilder());
                });
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response.build().toString());
    }

    // DONE
    // gets the tradable pets for the pet marketplace
    @GetMapping(path = "/pets")
    public ResponseEntity<String> getDefaultPets() {
        log.info("/api/player/pets: getting default pets");

        // will return a empty list if no pets available
        List<Pet> pets = petSvc.getDefaultTradablePets();

        JsonArrayBuilder response = Json.createArrayBuilder();

        pets.stream()
                .forEach(v -> {
                    response.add(v.toJSONObjBuilder());
                });

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response.build().toString());

    }

    // TODO
    @PostMapping(path = "/update")
    public ResponseEntity<String> updatePlayer(@RequestBody String payload) {
        log.info("/api/player/update {}", payload);

        JsonObject jsonRequest = Utils.stringToJSON(payload);

        // needs to update two things, character and pet
        JsonObjectBuilder response = Json.createObjectBuilder();

        try {
            boolean isSuccessful = charaSvc.updateCharacterAndPet(jsonRequest);

            response.add("isSuccessful", isSuccessful);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response.build().toString());
        } catch (UpdateCharacterAndPetException ex) {
            log.error("updating character and pet failed", ex.getMessage());
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response.build().toString());
        }

    }

}
