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
import org.springframework.web.bind.annotation.RestController;

import dev.leeshuyun.Lifeguild.exceptions.TradePetFailedException;
import dev.leeshuyun.Lifeguild.models.CharacterDetails;
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
import dev.leeshuyun.Lifeguild.services.TelegramService;

/*
 * for now the telegram bot accesses the same endpoints that angular does
 * and gets character data only. 
 * Later it will dispense reminders for the tasks that you've set
 */
@Slf4j
@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = "/api/telegram", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class TelegramBotController {

    @Autowired
    TelegramService tbot;

    // TODO
    @PostMapping(path = "/{userid}")
    public ResponseEntity<String> getReminder(@PathVariable String userid) {
        log.info("/api/telegram/{userid}: getting chara for userid: %s".formatted(userid));

        // List<Reminder> reminders = ;

        JsonObjectBuilder objBuilder = Json.createObjectBuilder();
        // .add("character", chara.toJSONObjBuilder());

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(objBuilder.build().toString());
    }
}
