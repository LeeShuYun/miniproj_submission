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

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import dev.leeshuyun.Lifeguild.services.TaskService;
import dev.leeshuyun.Lifeguild.models.Reward;

import jakarta.json.Json;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonObject;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = "/api/task/reward", consumes = MediaType.APPLICATION_JSON_VALUE)
public class RewardController {
        private final Logger logger = LoggerFactory.getLogger(RewardController.class);
        @Autowired
        private TaskService taskSvc;

        @PutMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<String> addReward(@RequestBody Reward reward) {

                // parse data
                // JsonObject jsonObj = JsonUtil.toJson(json).asJsonObject();
                // Reward reward =
                // Reward.createRewardFromJsonObj(jsonObj.getJsonObject("reward"));
                // String userid = Integer.valueOf(jsonObj.getString("userid"));
                logger.info("/api/reward: adding Reward %s".formatted(reward.toString()));

                int rewardId = taskSvc.addReward(reward);

                JsonObjectBuilder response = Json.createObjectBuilder()
                                .add("rewardid", rewardId);

                return ResponseEntity
                                .status(HttpStatus.OK)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(response.build().toString());
        }

        @PostMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<String> deleteReward(@RequestBody int rewardId) {

                boolean isRewardDeleted = taskSvc.deleteReward(rewardId);

                if (isRewardDeleted) {
                        String payload = Json.createObjectBuilder()
                                        .add("isSuccess", isRewardDeleted)
                                        .build().toString();
                        return ResponseEntity
                                        .status(HttpStatus.OK)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .body(payload);
                }
                return ResponseEntity.notFound().build();
        }

        @PostMapping(path = "/update", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<String> updateReward(@RequestBody String request) {
                logger.info("/api/reward/update >>> " + request);
                JsonObject requestJ = JsonUtil.toJson(request).asJsonObject();

                int arrayid = requestJ.getInt("arrayid");
                logger.info("/api/reward/update: arrayid %d".formatted(arrayid));

                Reward reward = Reward.builder()
                                .rewardid(requestJ.getInt("rewardid"))
                                .title(requestJ.getString("title"))
                                .cost(requestJ.getInt("cost"))
                                .dateCreated(Date.valueOf(requestJ.getString("dateCreated")))
                                .build();
                logger.info("/api/reward/update: updating Reward %s".formatted(reward.toString()));

                boolean isRewardUpdated = taskSvc.updateReward(reward);

                if (isRewardUpdated) {
                        String payload = Json.createObjectBuilder()
                                        .add("arrayid", arrayid)
                                        .build().toString();
                        return ResponseEntity
                                        .status(HttpStatus.OK)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .body(payload);
                }
                return ResponseEntity.notFound().build();
        }

        @PostMapping(path = "/spend", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<String> spendCoin(@RequestBody String json) {
                JsonObject jsonObj = JsonUtil.toJson(json).asJsonObject();
                int amountSpent = Integer.valueOf(jsonObj.getString("coin"));
                String userid = jsonObj.getString("userid");
                boolean isTransactionSuccess = taskSvc.spendCoin(userid, amountSpent);

                String payload = Json.createObjectBuilder()
                                .add("isSuccess", isTransactionSuccess)
                                .build().toString();
                return ResponseEntity
                                .status(HttpStatus.OK)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(payload);
        }
}
