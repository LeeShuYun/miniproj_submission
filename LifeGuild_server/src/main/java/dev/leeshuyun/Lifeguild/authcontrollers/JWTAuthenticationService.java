package dev.leeshuyun.Lifeguild.authcontrollers;

import org.springframework.stereotype.Service;

import dev.leeshuyun.Lifeguild.config.JwtService;
import dev.leeshuyun.Lifeguild.models.Role;
import dev.leeshuyun.Lifeguild.models.User;
import dev.leeshuyun.Lifeguild.repositories.AuthRepository;
import dev.leeshuyun.Lifeguild.services.AuthService;
// import dev.leeshuyun.Lifeguild.repository.SQLUserRepository;
import jakarta.json.JsonObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
// @RequiredArgsConstructor
public class JWTAuthenticationService {

    // injected val
    // private final SQLUserRepository userRepo;
    @Autowired
    private AuthRepository userRepo;
    // private final TokenRepository tokenRepository; //OAuth
    @Autowired
    private  PasswordEncoder passwordEncoder;

    @Autowired
    private  JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthService authSvc;

    // creates user, save user to DB, return the generated jwttoken
    public JsonObject registerUser(JsonObject requ) {
        JsonObject userJson = requ.getJsonObject("body");
        log.info(" registerUser {}", userJson.toString());
        // create userobj with request data
        User user = User.builder()
                .firstname(userJson.getString("firstname"))
                .lastname(userJson.getString("lastname"))
                .email(userJson.getString("email"))
                .username(userJson.getString("username"))
                .userpassword(passwordEncoder.encode(userJson.getString("password")))
                .userrole(Role.PLAYER)
                .confirmationcode(123456)
                .isemailconfirmed(false)
                .isgooglelogin(userJson.getBoolean("isgooglelogin", false))
                .telegramchatid("")
                .build();

        // register our new user and create jwttoken to send back
        // var savedUser = userRepo.registerUser(user);
        var savedUser = authSvc.registerUser(user);
        JsonObject jwtToken = jwtService.generateToken(user);

        // TODO - future OAuth
        // var refreshToken = jwtService.generateRefreshToken(user);
        // saveUserToken(savedUser, jwtToken);
        // return AuthenticationResponse.builder()
        // .token(jwtToken)
        // // .accessToken(jwtToken)
        // // .refreshToken(refreshToken)
        // .build();
        return jwtToken;
    }

    public JsonObject authenticate(JWTAuthenticationRequest request) {
        // checks email and password inside request
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));
        // if the login details are correct
        User user = userRepo.getUserByEmail(request.getEmail())
                .orElseThrow();

        JsonObject jwtToken = jwtService.generateToken(user);
        // return AuthenticationResponse.builder()
        // .token(jwtToken)
        // .build();
        return jwtToken;
    }

}
