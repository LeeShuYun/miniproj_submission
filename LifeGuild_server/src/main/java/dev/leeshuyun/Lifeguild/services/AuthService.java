package dev.leeshuyun.Lifeguild.services;

import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;
import java.util.Random;
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

    /*
     * >2 if success since userid auto increments, 0 if failed
     */
    @Transactional(rollbackFor = RegisteringUserFailedException.class)
    public int registerUser(User user2) throws RegisteringUserFailedException {
        // try {
        //     // JsonObject body = user2.getJsonObject("body");
        //     log.info("AuthSvc:registerUser()>>> user=".formatted(user2.toString()));
        //     // body.getString("email");
        // } catch (NullPointerException nulle) {
        //     log.error("no email found %s".formatted(nulle.getMessage()));
        //     throw new RegisteringUserFailedException("no email in payload");
        // }
        // JsonObject body = user2.getJsonObject("body");
        // String email = body.getString("email");
        String email = user2.getEmail();
        log.info("email receiver", email);
        // check if user has registered before.
        try {
            authRepo.getUserByEmail(email);
            // if this doesn't trip the exception...
            throw new RegisteringUserFailedException("user already exists, aborting registration");
        } catch (EmptyResultDataAccessException e) {
            log.info("AuthSvc>>> user with email %s does not exist. Making new Acc..."
                    .formatted(email));

            // generate the code for user to confirm email with
            Random rand = new SecureRandom();
            int confirmationCode = rand.nextInt(100000, 999999);

            // make acc
            // User user = User.builder()
            //         .firstname(body.getString("firstname"))
            //         .lastname(body.getString("lastname"))
            //         .email(body.getString("email"))
            //         .username(body.getString("username"))
            //         .userpassword(body.getString("password"))
            //         .userrole(Role.PLAYER)
            //         .confirmationcode(confirmationCode)
            //         .isemailconfirmed(false)
            //         .isgooglelogin(body.getBoolean("isgooglelogin"))
            //         .build();

            // insert new user into db
            int userid = authRepo.registerUser(user2);

            // userid 1 is admin, 2 is default user, so any new user is >2
            // TODO - refactor later when userid is changed to UUID, keeping it simple for
            // now. no points for registration
            if (userid > 2) {
                // register user success
                // insert a new default character and default pet for them
                Pet starterPet = charaRepo.addDefaultPet(userid);
                charaRepo.addDefaultCharacterWithPet(userid, starterPet.getPetid());

                // new user has no tasks, so leave that empty

                log.info("AuthService>>> sending email to %s, confirmation code %d".formatted(email, confirmationCode));
                // compose confirmation email
                String filePath = "src/main/java/dev/leeshuyun/Lifeguild/models/email-template.html";
                String htmlEmailTemplate = readFile(filePath);
                String subject = "Welcome to LifeGuild, please confirm your email";

                // replace placeholder values
                htmlEmailTemplate = htmlEmailTemplate.replace("${firstname}",
                        user2.getFirstname());
                // htmlEmailTemplate = htmlEmailTemplate.replace("${firstname}",
                //         body.getString("firstname"));
                String finishedEmail = htmlEmailTemplate.replace("${confirmationcode}",
                        confirmationCode + "");

                // send email
                try {
                    emailSvc.sendHtmlEmail(email, subject, finishedEmail);
                    log.info("AuthService>>> email sent! Registering User is success");
                    return userid;
                } catch (MessagingException me) {
                    log.error("failed to send email to registering user=%s"
                            .formatted(user2.toString()));
                    throw new RegisteringUserFailedException(
                            "email failed to send for %s".formatted(user2.toString()));

                }
            }

        }
        throw new RegisteringUserFailedException(
                "AuthSvc: registerUser>>> nothing happened??? your code broke".formatted(email));
    }

    public Optional<Account> getUserByEmailAndPassword(UserLoginDetails loginDetails) {
        User user = authRepo.getUserByEmail(loginDetails.getEmail()).get();
        log.info("AuthSvc>getUserByEmail> {}", user.toString());
        // compare passwords
        if (user.getUserpassword().equals(loginDetails.getPassword())) {
            log.info("correct password, logging in user", loginDetails.toString());

            // fetch character, pets, userid
            int userid = user.getUserid();
            Account account = new Account();
            account.setCharacterDetails(charaRepo.getCharacterByUserId(userid));
            account.setCurrentpet(charaRepo.getPetByUserid(userid));
            return Optional.of(account);
        } else {
            log.error("user doesn't exist");
            return Optional.empty();
        }
    }

    public User getUserByEmailOnly(String email) {
        return authRepo.getUserByEmail(email).get();
    }

    public boolean confirmUserEmailWithCode(JsonObject jsonObj) {
        JsonObject bodyJ = jsonObj.getJsonObject("body");
        log.info("bodyJ {}", bodyJ.toString());
        try {
            // bodyJ.getInt("code");
            // bodyJ.getString("email");
            log.info("confirmUserEmailWithCode: code={}, body={}", bodyJ.getString("code"), bodyJ.getString("email"));
        } catch (NullPointerException nulle) {
            log.error("payload doesn't have email or code");
            return false;
        } catch (ClassCastException classe) {
            log.error("payload doesn't have email or code");
            return false;
        }

        int confirmCode = Integer.valueOf(bodyJ.getString("code"));

        String email = bodyJ.getString("email");

        if (confirmCode > 999999 || confirmCode < 100000) {
            log.info("code given is not 6 digit");
            return false;
        }

        // fetch user code and check
        try {
            User user = getUserByEmailOnly(email);
            int DBConfirmationCode = user.getConfirmationcode();
            log.info("user code=%d, testing confirmation code=%d".formatted(DBConfirmationCode, confirmCode));

            // check parity
            if (confirmCode == DBConfirmationCode) {
                log.info("confirmed user with email={}, code={}, dbcode={}", email, confirmCode, DBConfirmationCode);
                // then mark them as email confirmed
                return authRepo.confirmUserEmail(email);
            }
        } catch (EmptyResultDataAccessException e) {
            log.error(e.getMessage());
        }
        log.info("why");
        return false;
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
