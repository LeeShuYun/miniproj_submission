package dev.leeshuyun.Lifeguild.controllers;

import java.sql.Date;

import org.glassfish.json.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import dev.leeshuyun.Lifeguild.services.TaskService;
import dev.leeshuyun.Lifeguild.models.Habit;
import dev.leeshuyun.Lifeguild.models.Reward;

import jakarta.json.Json;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonObject;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = "/api/task/habit", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class HabitController {
        private final Logger logger = LoggerFactory.getLogger(HabitController.class);
        @Autowired
        private TaskService taskSvc;

        @PutMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<String> addHabit(@RequestBody Habit habit) {
                logger.info("PUT add /api/task/habit: {}", habit.toString());

                int habitId = taskSvc.addHabit(habit);

                JsonObjectBuilder objBuilder = Json.createObjectBuilder()
                                .add("habitid", habitId);

                return ResponseEntity
                                .status(HttpStatus.OK)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(objBuilder.build().toString());
        }

        @PostMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<String> deleteHabit(@RequestBody int habitid) {
                logger.info("POST delete /api/task/habit: {}", habitid);

                boolean isSuccess = taskSvc.deleteHabit(habitid);
                String payload = Json.createObjectBuilder()
                                .add("isSuccess", isSuccess)
                                .build().toString();
                return ResponseEntity
                                .status(HttpStatus.OK)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(payload);
        }

        // DONE
        @PostMapping(path = "/update", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<String> editHabit(@RequestBody Habit habit) {
                logger.info("POST updating habit {}", habit);

                boolean isSuccess = taskSvc.updateHabit(habit);

                JsonObjectBuilder objBuilder = Json.createObjectBuilder();
                if (isSuccess) {
                        // objBuilder.add("rowsEdited");

                        return ResponseEntity
                                        .status(HttpStatus.OK)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .body(objBuilder.build().toString());

                }
                return ResponseEntity
                                .status(HttpStatus.NOT_FOUND)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(objBuilder.build().toString());
        }

}
