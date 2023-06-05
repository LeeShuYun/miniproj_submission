package dev.leeshuyun.Lifeguild.controllers;

import org.glassfish.json.JsonUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import dev.leeshuyun.Lifeguild.models.Task;
import dev.leeshuyun.Lifeguild.utils.Utils;
import jakarta.json.JsonObjectBuilder;

import lombok.extern.slf4j.Slf4j;
import jakarta.json.JsonObject;

import dev.leeshuyun.Lifeguild.services.TaskService;

/*
 * tasks only, rewards, habits, todos and dailys are elsewhere
 */
@Slf4j
@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = "/api/task", consumes = MediaType.APPLICATION_JSON_VALUE)
public class TaskController {

    @Autowired
    private TaskService taskSvc;

    // trying to hide the userid from url
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAllTasks(@RequestBody String userid) {

        // parse data
        JsonObject userId = JsonUtil.toJson(userid).asJsonObject();
        log.info("/api/task: getting tasks from mySQL for userid: %s".formatted(userId.getString("userid")));

        Task task = taskSvc.getAllTasksByUserId(userId.getString("userid"));

        JsonObjectBuilder arrBuilder = Utils.taskToJsonObjBuilder(task);

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(arrBuilder.build().toString());
    }

}
