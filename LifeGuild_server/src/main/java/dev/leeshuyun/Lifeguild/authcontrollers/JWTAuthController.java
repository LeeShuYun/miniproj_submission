package dev.leeshuyun.Lifeguild.authcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.leeshuyun.Lifeguild.exceptions.RegisteringUserFailedException;
import dev.leeshuyun.Lifeguild.models.User;
import dev.leeshuyun.Lifeguild.utils.MiscUtils;
import dev.leeshuyun.Lifeguild.utils.Utils;
import jakarta.json.Json;
import jakarta.json.JsonObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.glassfish.json.JsonUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.writer.JsonReader;

// @CrossOrigin(origins = "*")
// @CrossOrigin(origins = "https://miniproj-server-production.up.railway.app/")
@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
@RestController
@RequestMapping(path = "/api/v1/auth", consumes = MediaType.APPLICATION_JSON_VALUE)
// @RequiredArgsConstructor // alterate way of dependency injection, trying
public class JWTAuthController {
    private final Logger logger = LoggerFactory.getLogger(JWTAuthController.class);

    // injected authSvc
    @Autowired
    private JWTAuthenticationService authSvc;

    // new user registration
    @PostMapping(path = "/register")
    public ResponseEntity<String> register(@RequestBody String request) {
        log.info("/api/v1/auth/register>> user registration request {}", request);
        // request holds the registration information
        // like firstname lastname email password
        // JsonObject userJson = Utils.stringToJSON(request);
        JsonObject userJson = JsonUtil.toJson(request).asJsonObject();
log.info("userJson look like this: {}", userJson.toString());

        // CHECK IF GOOGL- ah nevermind. next time then.
        // System.out.println(userJson.getBoolean("isgooglelogin"));
        JsonObject jwtToken;
        try {
            // register the user into mySQL DB
            jwtToken = authSvc.registerUser(userJson);
        } catch (RegisteringUserFailedException e) {
            String errorMsg = "Account with email primary key '%s' already exists in database."
                    .formatted(userJson.getJsonObject("body").getString("email"));
            String errorResponse = Json.createObjectBuilder()
                    .add("message", errorMsg)
                    .build()
                    .toString();
            logger.info(errorMsg);
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(errorResponse);
        }

        // return jwttoken
        return ResponseEntity.ok().body(jwtToken.toString());
    }

    @PostMapping(path = "/authenticate")
    public ResponseEntity<String> authenticate(
            @RequestBody JWTAuthenticationRequest request) {
        String response = authSvc.authenticate(request).toString();

        JsonObject jsonResp = Json.createObjectBuilder()
                .add("message", response)
                .build();
        return ResponseEntity.ok().body(jsonResp.toString());
    }


    @PostMapping(path = "/test")
    public ResponseEntity<String> test(@RequestBody String req) {
        System.out.println(req);
        logger.info(req);
        JsonObject jsonResp = Json.createObjectBuilder()
                .add("message", "success! hello from spring boot")
                .add("you sent", req.toString())
                .build();

        return ResponseEntity.ok().body(jsonResp.toString());
    }

    @GetMapping(path = "/test")
    public ResponseEntity<String> getTest() {
        JsonObject jsonResp = Json.createObjectBuilder()
                .add("message", "success! hello from spring boot")
                .build();

        return ResponseEntity.ok().body(jsonResp.toString());
    }
}
