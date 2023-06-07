package dev.leeshuyun.Lifeguild.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

import jakarta.json.Json;
import jakarta.json.JsonObjectBuilder;
import org.glassfish.json.JsonUtil;
import jakarta.json.JsonObject;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.embedded.jetty.JettyWebServer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.leeshuyun.Lifeguild.models.UserLoginDetails;
import dev.leeshuyun.Lifeguild.auth.JwtService;
import dev.leeshuyun.Lifeguild.exceptions.EmailConfirmationException;
import dev.leeshuyun.Lifeguild.exceptions.RegisteringUserFailedException;
import dev.leeshuyun.Lifeguild.models.Account;
import dev.leeshuyun.Lifeguild.models.CharacterDetails;
import dev.leeshuyun.Lifeguild.models.Pet;
import dev.leeshuyun.Lifeguild.services.AuthService;

@CrossOrigin("*")
@Slf4j
@RestController
@RequestMapping(path = "/api/auth", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {
        private final Logger logger = LoggerFactory.getLogger(AuthController.class);

        @Autowired
        private AuthService authSvc;

        @Autowired
        private JwtService jwtSvc;

        @PostMapping(path = "/login", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<String> loginUser(@RequestBody String payload) {
                logger.info("/api/auth/login >>> " + payload);
                JsonObject request = JsonUtil.toJson(payload).asJsonObject();

                String email = request.getString("email");
                String password = request.getString("password");
                logger.info("email: {} password: {}" + email, password);
                // fetch user and check
                UserLoginDetails loginDetails = UserLoginDetails.builder()
                                .email(email)
                                .password(password)
                                .build();
                // find the user
                Optional<Account> account = authSvc.getUserByEmailAndPassword(loginDetails);
                if (account.isEmpty()) {
                        return ResponseEntity.notFound().build();
                }

                // get the details, return character details and pet details
                Account acc = account.get();
                String jwt = jwtSvc.generateToken(account.get().getUser());
                CharacterDetails chara = acc.getCharacterDetails();
                JsonObjectBuilder charaDetailsBuilder = chara.toJSONObjBuilder();
                Pet pet = acc.getCurrentpet();
                JsonObjectBuilder petDetailsBuilder = pet.toJSONObjBuilder();
                log.info("userCharacter= {}, userPet = {}",
                                chara.toString(),
                                pet.toString());
                JsonObjectBuilder responseBody = Json.createObjectBuilder()
                                .add("character", charaDetailsBuilder)
                                .add("pet", petDetailsBuilder)
                                .add("jwt", jwt);

                return ResponseEntity.ok().body(responseBody.build().toString());

        }

        // done, not tested jwt version
        @PutMapping(path = "/register/confirm-email", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<String> confirmEmail(@RequestBody String payload) {
                logger.info("/api/auth/register/confirm-email >>> " + payload);
                JsonObject jsonObj = JsonUtil.toJson(payload).asJsonObject();
                JsonObjectBuilder response = Json.createObjectBuilder();
                try {
                        String jwtToken = authSvc.confirmUserEmailWithCode(jsonObj);
                        response.add("jwt", jwtToken);
                        log.info("email confirmed, responding with jwt token");
                        return ResponseEntity.ok().body(response.build().toString());
                } catch (EmailConfirmationException e) {
                        response.add("isUserConfirmed", false);
                        return ResponseEntity
                                        .status(HttpStatus.NOT_FOUND)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .body(response.build().toString());

                }
        }

        // done and tested
        @PutMapping(path = "/register", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<String> registerNewUser(@RequestBody String body) {
                logger.info("POST /api/auth/register: %s".formatted(body));

                JsonObject jsonObj = JsonUtil.toJson(body).asJsonObject();

                // register user
                try {
                        String userid = authSvc.registerUser(jsonObj);
                        JsonObjectBuilder jsonB = Json.createObjectBuilder()
                                        .add("message", "email sent")
                                        .add("userid", userid);

                        return ResponseEntity.ok().body(jsonB.build().toString());
                } catch (RegisteringUserFailedException e) {
                        JsonObject errorReply = Json.createObjectBuilder()
                                        .add("error", "failed")
                                        .add("message", "Did you register with the same email before?")
                                        .build();
                        return ResponseEntity.status(400).body(errorReply.toString());
                }

        }

}
