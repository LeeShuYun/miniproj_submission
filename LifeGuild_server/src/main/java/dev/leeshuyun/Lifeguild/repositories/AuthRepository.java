package dev.leeshuyun.Lifeguild.repositories;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import java.sql.PreparedStatement;

import dev.leeshuyun.Lifeguild.exceptions.RegisteringUserFailedException;
import dev.leeshuyun.Lifeguild.exceptions.TelegramChatLoginFailedException;

import dev.leeshuyun.Lifeguild.models.Role;
import dev.leeshuyun.Lifeguild.models.User;
import dev.leeshuyun.Lifeguild.utils.MiscUtils;
import lombok.extern.slf4j.Slf4j;

import static dev.leeshuyun.Lifeguild.repositories.SQLqueries.*;

@Slf4j
@Repository
public class AuthRepository {
    private Logger logger = LoggerFactory.getLogger(AuthRepository.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Create GeneratedKeyHolder object
    GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
    // return user id if successful, else null

    // DONE
    public int registerUser(User user) throws RegisteringUserFailedException {
        logger.info("AuthRepository>>> Inserting New User %s".formatted(user.toString()));

        try {

            int rowsAffected = jdbcTemplate.update(conn -> {
                PreparedStatement preparedStatement = conn.prepareStatement(SQL_INSERT_NEW_USER,
                        Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, user.getFirstname());
                preparedStatement.setString(2, user.getLastname());
                preparedStatement.setString(3, user.getEmail());
                preparedStatement.setString(4, user.getUsername());
                preparedStatement.setString(5, user.getUserpassword());
                preparedStatement.setString(6, "PLAYER");
                preparedStatement.setInt(7, user.getConfirmationcode());
                preparedStatement.setBoolean(8, user.getIsemailconfirmed());
                preparedStatement.setBoolean(9, user.getIsgooglelogin());
                preparedStatement.setInt(10, user.getTelegramchatid());
                preparedStatement.setDate(11, new Date(System.currentTimeMillis()));
                return preparedStatement;

            }, generatedKeyHolder);
        } catch (DataIntegrityViolationException ex) {
            String msg = "Email primary key '%s' already exists in database. Error %s".formatted(user.getEmail(),
                    ex);
            logger.error(msg);
            throw new RegisteringUserFailedException(msg);
        }
        // if (rowsAffected == 0) {
        // throw new RegisteringUserFailedException("AuthRepo>>> cannnot register
        // User=%s".formatted(user.toString()));
        // }
        // Get auto-incremented ID
        int userid = generatedKeyHolder.getKey().intValue();

        // logger.info("AuthRepo>>> New user created. rowsAffected={}, userid={}",
        // rowsAffected, userid);
        logger.info("AuthRepo>>> New user created. userid={}", userid);
        return userid;
    }

    // public User getUserByEmail(String email) throws DataAccessException {
    // logger.info("getUserByEmail>>> ", email);
    // // return new User();
    // return jdbcTemplate.queryForObject(SQL_GET_USER_BY_EMAIL,
    // BeanPropertyRowMapper.newInstance(User.class), email);
    // }

    public Optional<User> getUserByEmail(String email) throws DataAccessException {
        SqlRowSet rs = jdbcTemplate.queryForRowSet(SQL_GET_USER_BY_EMAIL, email);
        if (!rs.next()) {
            return Optional.empty();
        }
        User user = MiscUtils.sqlRowsetToUser(rs);
        log.info("getUserByEmail {}", user.toString());
        // System.out.println(user.toString()); // print
        return Optional.of(user);
    }

    public boolean confirmUserEmail(String email) {
        Integer result = jdbcTemplate.update(SQL_UPDATE_USER_EMAIL_CONFIRMED, email);
        if (result == 0) {
            String msg = "AuthRepo>>> error cannot update email=%s".formatted(email);
            logger.error(msg);
            return false;
        }
        return true;
    }

    public Optional<User> findUserByUserId(int userid) {
        logger.info("AuthRepo>>> finding user by userid={}", userid);
        SqlRowSet rs = jdbcTemplate.queryForRowSet(SQL_GET_USER_BY_USERID, userid);

        if (!rs.next())
            return Optional.empty();
        User user = new User();
        user.setUserid(rs.getInt("userid"));
        user.setFirstname(rs.getString("firstname"));
        user.setLastname(rs.getString("lastname"));
        user.setEmail(rs.getString("email"));
        user.setUsername(rs.getString("username"));
        user.setUserpassword(rs.getString("userpassword"));
        user.setUserrole(Role.valueOf(rs.getString("userrole")));
        user.setIsgooglelogin(rs.getBoolean("isgooglelogin"));
        return Optional.of(user);
    }

    // login through telegram
    public void loginByTelegramBot(String tchatid, String email) throws TelegramChatLoginFailedException {
        logger.info("AuthRepo>>> updating telegramchatid {} for email {}", tchatid, email);
        Integer result = jdbcTemplate.update(SQL_UPDATE_USER_TELEGRAM_CONFIRMED, tchatid, email);
        if (result == 0) {
            throw new TelegramChatLoginFailedException("cannot update telegramchatid %s".formatted(tchatid));
        }
    }
}
