package dev.leeshuyun.TelegramBot.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.io.StringReader;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;
import lombok.extern.slf4j.Slf4j;

/*
 * The manual Https way of getting a Telegram Bot chat
 */
@Slf4j
@Service
public class TelegramService {

    private final String TELEGRAM_URI = "https://api.telegram.org/bot";

    @Value("${telegram.token}")
    private String TELEGRAM_TOKEN;

    public boolean getUserChatId() {
        return true;
    }

    // sends to unique user's Telegram through Telegram bot api
    public boolean sendMessageToUser(String message, String chatid) {

        String fullURI = UriComponentsBuilder
                .fromUriString(TELEGRAM_URI + TELEGRAM_TOKEN + "/sendMessage")
                .queryParam("chat_id", chatid)
                .queryParam("text", message)
                .toUriString();
        log.info("TelegramBot>> sendMessageToUser(): %s".formatted(fullURI));

        RequestEntity<Void> req = RequestEntity.get(fullURI)
                .accept(MediaType.APPLICATION_JSON)
                .build();
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = null;
        try {
            // executes the req entity and returns a response with json payload body
            resp = template.exchange(req, String.class);
        } catch (RestClientException e) {
            log.error("message" + e.getMessage()
                    + "error" + e.getStackTrace());
            return false;
        }

        String payload = resp.getBody();

        log.info("ALL RIGHT WE GOT THE JSON HOT OFF THE API HERE: " + payload);
        JsonReader reader = Json.createReader(new StringReader(payload));
        JsonObject response = reader.readObject();

        // check for ok reply
        boolean isOk = response.getBoolean("ok", false);
        if (!isOk) {
            log.error("ERROR TelegramService>>> Couldn't successfully send message. message={}",
                    message);
            return !isOk;
        }

        return isOk;
    }

    // allows us to fetch chats for unique users by chatid
    public List<String> getUserMessagesByChatid(String chatid) {
        List<String> userMsg = new ArrayList<String>();
        try {

            String fullURI = UriComponentsBuilder
                    .fromUriString(TELEGRAM_URI + TELEGRAM_TOKEN + "/getUpdates")
                    .queryParam("chat_id", chatid)
                    .toUriString();
            log.info("TelegramBot>> /getUpdates: %s".formatted(fullURI));

            RequestEntity<Void> req = RequestEntity.get(fullURI)
                    .accept(MediaType.APPLICATION_JSON)
                    .build();
            RestTemplate template = new RestTemplate();
            ResponseEntity<String> resp = null;
            try {
                // executes the req entity and returns a response with json payload body
                resp = template.exchange(req, String.class);
            } catch (RestClientException ex) {
                log.error("TelegramService>>> getMessages ERROR");
                return userMsg;
            }

            String payload = resp.getBody();

            log.info("ALL RIGHT WE GOT THE INFO HOT OFF THE API HERE: " + payload);
            JsonReader reader = Json.createReader(new StringReader(payload));
            JsonObject response = reader.readObject();

            boolean isOk = response.getBoolean("ok", false);
            if (!isOk) {
                System.out.println("Couldn't successfully get updates");
            }

            // process the json
            JsonArray result = response.getJsonArray("result");
            for (int i = 1; i < result.size(); i++) {
                JsonObject message = result.getJsonObject(1);
                String textMsg = message.getString("text");
                System.out.println("TelegramBot: message received from telegram>>>" + textMsg);
                userMsg.add(textMsg);
            }
            return userMsg;
        } catch (Exception e) {
            log.error("message: " + e.getMessage());
            return userMsg;
        }

    }
}
