package dev.leeshuyun.Lifeguild.services;

import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.leeshuyun.Lifeguild.models.UserLoginDetails;
import dev.leeshuyun.Lifeguild.auth.JwtService;
import dev.leeshuyun.Lifeguild.exceptions.EmailConfirmationException;
import dev.leeshuyun.Lifeguild.exceptions.RegisteringUserFailedException;
import dev.leeshuyun.Lifeguild.models.Account;
import dev.leeshuyun.Lifeguild.models.CharacterDetails;
import dev.leeshuyun.Lifeguild.models.Pet;
import dev.leeshuyun.Lifeguild.models.Role;
import dev.leeshuyun.Lifeguild.models.User;
import dev.leeshuyun.Lifeguild.repositories.AuthRepository;
import dev.leeshuyun.Lifeguild.repositories.CharacterRepository;
import jakarta.json.JsonObject;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AuthService {

    @Autowired
    private AuthRepository authRepo;

    @Autowired
    private CharacterRepository charaRepo;

    @Autowired
    private EmailService emailSvc;

    @Autowired
    private JwtService jwtSvc;

    /*
     * >2 if success since userid auto increments, 0 if failed
     */
    @Transactional(rollbackFor = RegisteringUserFailedException.class)
    public String registerUser(JsonObject payload) throws RegisteringUserFailedException {
        String userid = UUID.randomUUID().toString().substring(0, 8);

        try {
            log.info("AuthSvc:registerUser()>>> body=%s".formatted(payload));
            payload.getString("email");
        } catch (NullPointerException nulle) {
            log.error("no email found %s".formatted(nulle.getMessage()));
            throw new RegisteringUserFailedException("no email in payload");
        }
        String email = payload.getString("email");
        // check if user has registered before.
        try {
            authRepo.getUserByEmail(email);
            log.error("user already exists, aborting registration");
            // throw new RegisteringUserFailedException();
        } catch (EmptyResultDataAccessException e) {
            log.info("AuthSvc>>> user with email %s does not exist. Making new Acc..."
                    .formatted(email));

            // generate the code for user to confirm email with
            Random rand = new SecureRandom();
            int confirmationCode = rand.nextInt(100000, 999999);

            // make acc
            User user = User.builder()
                    .userid(userid)
                    .firstname(payload.getString("firstname"))
                    .lastname(payload.getString("lastname"))
                    .email(payload.getString("email"))
                    .username(payload.getString("username"))
                    .userpassword(payload.getString("password"))
                    .userrole(Role.PLAYER)
                    .confirmationcode(confirmationCode)
                    .isemailconfirmed(false)
                    .isgooglelogin(payload.getBoolean("isgooglelogin"))
                    .build();

            // insert new user into db
            authRepo.registerUser(user);

            // userid 1 is admin, 2 is default user, so any new user is >2

            // register user success
            // insert a new default character and default pet for them
            String petid = UUID.randomUUID().toString().substring(0, 8);
            Pet starterPet = charaRepo.addDefaultPet(userid, petid);

            charaRepo.addDefaultCharacterWithPet(userid, starterPet.getPetid());

            // new user has no tasks, so leave that empty

            // compose confirmation email
            log.info("AuthService>>> sending email to %s, confirmation code %d".formatted(email, confirmationCode));
            String filePath = "src/main/java/dev/leeshuyun/Lifeguild/models/email-template.html";
            String htmlEmailTemplate = readFile(filePath);
            String subject = "Welcome to LifeGuild, please confirm your email";

            // replace placeholder values
            htmlEmailTemplate = htmlEmailTemplate.replace("${firstname}",
                    payload.getString("firstname"));
            String finishedEmail = htmlEmailTemplate.replace("${confirmationcode}",
                    confirmationCode + "");

            // send email
            try {
                emailSvc.sendHtmlEmail(email, subject, finishedEmail);
                log.info("AuthService>>> email sent! Registering User is success");
            } catch (MessagingException me) {
                log.error("failed to send email to registering user=%s"
                        .formatted(user.toString()));
                throw new RegisteringUserFailedException(
                        "email failed to send for %s".formatted(user.toString()));
            }

        }
        // throw new RegisteringUserFailedException(
        // "AuthSvc: registerUser>>> nothing happened??? your code
        // broke".formatted(email));
        return userid;
    }

    public Optional<Account> getUserByEmailAndPassword(UserLoginDetails loginDetails) {
        User user = authRepo.getUserByEmail(loginDetails.getEmail());
        log.info("AuthSvc>getUserByEmail> {}", user.toString());
        // compare passwords
        if (user.getUserpassword().equals(loginDetails.getPassword())) {
            log.info("correct password, logging in user", loginDetails.toString());

            // fetch character, pets, userid
            String userid = user.getUserid();
            Account account = new Account();
            account.setCharacterDetails(charaRepo.getCharacterByUserId(userid));
            account.setCurrentpet(charaRepo.getPetByUserid(userid));
            account.setUser(user);
            return Optional.of(account);
        } else {
            log.error("user doesn't exist");
            return Optional.empty();
        }
    }

    public User getUserByEmailOnly(String email) {
        return authRepo.getUserByEmail(email);
    }

    // returns jwt string
    public String confirmUserEmailWithCode(JsonObject jsonObj)
            throws NullPointerException, ClassCastException, EmailConfirmationException {
        log.info("bodyJ {}", jsonObj.toString());
        try {
            log.info("confirmUserEmailWithCode: code={}, body={}", jsonObj.getString("code"),
                    jsonObj.getString("email"));
        } catch (NullPointerException nulle) {
            // log.error("payload doesn't have email or code");
            throw new EmailConfirmationException("payload doesn't have email or code");
        } catch (ClassCastException classe) {
            // log.error("payload doesn't have email or code");
            throw new EmailConfirmationException("payload doesn't have email or code");
        }

        int confirmCode = Integer.valueOf(jsonObj.getString("code"));

        String email = jsonObj.getString("email");

        if (confirmCode > 999999 || confirmCode < 100000) {
            throw new EmailConfirmationException("code given is not 6 digit");
        }

        // fetch user code and check
        try {
            User user = getUserByEmailOnly(email);
            int DBConfirmationCode = user.getConfirmationcode();
            log.info("user code=%d, testing confirmation code=%d".formatted(DBConfirmationCode, confirmCode));

            // check parity
            if (confirmCode == DBConfirmationCode) {
                log.info("confirmed user with email={}, code={}, dbcode={}", email, confirmCode, DBConfirmationCode);
                // then mark them as email confirmed and give them a jwt token
                if (authRepo.confirmUserEmail(email)) {
                    return jwtSvc.generateToken(user);
                } else {
                    throw new EmailConfirmationException("failed to update email confirmed user status");
                }
            }
        } catch (EmptyResultDataAccessException e) {
            log.error(e.getMessage());
        }
        log.error("confirmUserEmailWithCode>>> why are you here, something has gone wrong");
        throw new EmailConfirmationException(email);
    }

    // file paths should be relative
    // src/main/java/dev/leeshuyun/Lifeguild/models/email-template.html
    private String readFile(String filePath) {
        log.info("reading local file: filePath=" + filePath);
        Path templatePath = Paths.get(filePath);
        File fileTemplate = templatePath.toFile();

        if (fileTemplate.exists()) {
            log.info("reading email-template.html");
            // reader
            try {
                FileReader fr = new FileReader(fileTemplate);
                BufferedReader bdf = new BufferedReader(fr);
                String emailText = "";
                // reads line by line until there's nothing next
                String line = "";
                while (null != (line = bdf.readLine())) {
                    emailText += bdf.readLine();
                }
                bdf.close();
                log.info("populated emailTemplate: " + emailText);
                return emailText;
            } catch (FileNotFoundException e) {
                log.error("filePath not found, check email-template.html location" + e.getMessage());
            } catch (IOException e) {
                log.error("email-template.html cannot be read" + e.getMessage());
            }
        }
        return "";
    }
}
