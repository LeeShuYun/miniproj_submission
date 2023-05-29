package dev.leeshuyun.Lifeguild.controllers;

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
import dev.leeshuyun.Lifeguild.models.ToDo;

import jakarta.json.Json;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonObject;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = "/api/task/todo", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class ToDoController {
        private final Logger logger = LoggerFactory.getLogger(ToDoController.class);
        @Autowired
        private TaskService taskSvc;

        @PutMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<String> addHabit(@RequestBody ToDo todo) {
                logger.info("PUT add /api/task/todo: {}", todo.toString());

                int todoId = taskSvc.addToDo(todo);

                JsonObjectBuilder objBuilder = Json.createObjectBuilder()
                                .add("todoid", todoId);

                return ResponseEntity
                                .status(HttpStatus.OK)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(objBuilder.build().toString());
        }

        @PostMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<String> deleteToDo(@RequestBody int todoid) {
                logger.info("POST delete /api/task/todo: {}", todoid);

                boolean isSuccess = taskSvc.deleteToDo(todoid);
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
        public ResponseEntity<String> updateToDo(@RequestBody ToDo todo) {
                logger.info("POST update /api/task/todo: {}", todo);

                boolean isSuccess = taskSvc.updateToDo(todo);
                if (isSuccess) {
                        String payload = Json.createObjectBuilder()
                                        .add("isSuccess", isSuccess)
                                        .build().toString();
                        return ResponseEntity
                                        .status(HttpStatus.OK)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .body(payload);
                }
                return ResponseEntity
                                .status(HttpStatus.NOT_FOUND)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body("not successful");

        }

}
