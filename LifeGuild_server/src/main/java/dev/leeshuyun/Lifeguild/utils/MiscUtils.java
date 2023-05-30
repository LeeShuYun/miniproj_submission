package dev.leeshuyun.Lifeguild.utils;

import java.security.SecureRandom;
import java.util.Random;
import java.util.UUID;

import org.glassfish.json.JsonUtil;
import jakarta.json.JsonObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.crypto.password.PasswordEncoder;

import dev.leeshuyun.Lifeguild.models.Role;
import dev.leeshuyun.Lifeguild.models.User;

public class MiscUtils {

    public static String createID() {
        return UUID.randomUUID().toString().substring(0, 8);
    }

    public static String createRandPassword2030() {
        Random rand = new SecureRandom();
        String pw = UUID.randomUUID().toString().substring(0, rand.nextInt(12, 30));
        // print
        System.out.println("randomly generated pw: " + pw);
        return pw;
    }

    public static User sqlRowsetToUser(SqlRowSet rowset) {
        User user = User.builder()
        .userid(rowset.getInt("userid"))
                .firstname(rowset.getString("firstname"))
                .lastname(rowset.getString("lastname"))
                .email(rowset.getString("email"))
                .userpassword(rowset.getString("userpassword"))
                .userrole(Role.valueOf(rowset.getString("userrole")))
                .isemailconfirmed(rowset.getBoolean("isemailconfirmed"))
                .isgooglelogin(rowset.getBoolean("isgooglelogin"))
                .telegramchatid(rowset.getInt("telegram_chatid"))
                .build();
        return user;
    }
}
