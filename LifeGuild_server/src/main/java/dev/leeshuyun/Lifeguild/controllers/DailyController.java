package dev.leeshuyun.Lifeguild.controllers;

import org.glassfish.json.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import dev.leeshuyun.Lifeguild.services.TaskService;
import dev.leeshuyun.Lifeguild.models.Daily;
import dev.leeshuyun.Lifeguild.models.Reward;

import jakarta.json.Json;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonObject;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = "/api/task/daily", consumes = MediaType.APPLICATION_JSON_VALUE)
public class DailyController {
        private final Logger logger = LoggerFactory.getLogger(DailyController.class);
        @Autowired
        private TaskService taskSvc;

        // DONE
        @PutMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<String> addDaily(@RequestBody Daily daily) {
                logger.info("/api/task/daily: adding Daily %s".formatted(daily.toString()));

                int dailyid = taskSvc.addDaily(daily);

                JsonObjectBuilder response = Json.createObjectBuilder()
                                .add("dailyid", dailyid);

                return ResponseEntity
                                .status(HttpStatus.OK)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(response.build().toString());
        }

        // DONE
        @PostMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<String> deleteDaily(@RequestBody int dailyid) {
                logger.info("POST delete /api/task/daily: {}", dailyid);

                boolean isSuccess = taskSvc.deleteDaily(dailyid);
                String payload = Json.createObjectBuilder()
                                .add("isSuccess", isSuccess)
                                .build().toString();
                return ResponseEntity
                                .status(HttpStatus.OK)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(payload);
        }

        // TODO test this
        @PostMapping(path = "/update", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<String> updateDaily(@RequestBody Daily daily) {
                logger.info("POST /api/task/daily/update: {}", daily.toString());

                boolean isSuccess = taskSvc.updateDaily(daily);

                if (isSuccess) {

                        String response = Json.createObjectBuilder()
                                        .add("isSuccess", isSuccess)
                                        .build().toString();
                        return ResponseEntity
                                        .status(HttpStatus.OK)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .body(response);
                }
                return ResponseEntity
                                .status(HttpStatus.NOT_IMPLEMENTED)
                                .contentType(MediaType.APPLICATION_JSON)
                                .build();
        }
}
