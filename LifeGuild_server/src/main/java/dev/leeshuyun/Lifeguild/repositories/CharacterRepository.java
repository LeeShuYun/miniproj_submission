package dev.leeshuyun.Lifeguild.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.lang.Nullable;

import dev.leeshuyun.Lifeguild.exceptions.RegisteringUserFailedException;
import dev.leeshuyun.Lifeguild.exceptions.TradePetFailedException;
import dev.leeshuyun.Lifeguild.exceptions.UpdateCharacterAndPetException;
import dev.leeshuyun.Lifeguild.models.CharacterDetails;
import dev.leeshuyun.Lifeguild.models.Pet;
import dev.leeshuyun.Lifeguild.models.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import java.sql.PreparedStatement;
import java.sql.Statement;

import static dev.leeshuyun.Lifeguild.repositories.SQLqueries.*;

/*
 * Deals anything to do with characters.. Characters =/= User
 */
@Slf4j
@Repository
public class CharacterRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();

    public CharacterDetails getCharacterByUserId(int userid) {
        return jdbcTemplate.queryForObject(SQL_GET_CHARACTER_BY_USERID,
                BeanPropertyRowMapper.newInstance(CharacterDetails.class), userid);
    }

    // reusable, cool.
    public void updateBalance(int userid, int amount) throws TradePetFailedException {
        // template.update returns the number of rows affected, so if no rows affected,
        // throw exception and trigger transaction rollback
        if (jdbcTemplate.update(SQL_UPDATE_USER_WALLET, amount, userid) <= 0)
            throw new TradePetFailedException("updating userid %s account failed".formatted(userid));
    }

    public void addZenny(int userid, int amount) throws TradePetFailedException {
        log.info("adding coins to the adoption agency(admin) {}", amount);
        updateBalance(userid, amount);
    }

    public void deductZenny(int userid, int amount) throws TradePetFailedException {
        log.info("deducting coins from user {}, amount {}", userid, amount);
        updateBalance(userid, -amount);
    }

    public void changePetOwner(int petid, int toUserId) throws TradePetFailedException {
        if (jdbcTemplate.update(SQL_UPDATE_PET_OWNER, toUserId, petid) <= 0)
            throw new TradePetFailedException("updating  %s pet owner failed".formatted(petid));
    }

    public void changeCurrentPet(int petid, int toUserId) throws TradePetFailedException {
        if (jdbcTemplate.update(SQL_UPDATE_CHARACTER_PETID, petid, toUserId) <= 0)
            throw new TradePetFailedException("changeCurrentPet failed for userid %s".formatted(toUserId));
    }

    public CharacterDetails getCharacterByUserId(String email) {
        return jdbcTemplate.queryForObject(SQL_GET_CHARACTER_BY_EMAIL,
                BeanPropertyRowMapper.newInstance(CharacterDetails.class), email);
    }

    // future use
    // TODO - for later. fix the healingamount and healing it caused a bug
    public List<Pet> getPetListByUserid(int userid) {
        return jdbcTemplate.query(SQL_GET_ALL_PETS_BY_USERID, new ResultSetExtractor<List<Pet>>() {
            @Override
            @Nullable
            public List<Pet> extractData(ResultSet rs) throws SQLException,
                    DataAccessException {
                List<Pet> petList = new ArrayList<Pet>();

                while (rs.next()) {
                    Pet pet = new Pet();
                    pet.setPetid(rs.getInt("petid"));
                    pet.setUserid(rs.getInt("userid"));
                    pet.setHealing(rs.getInt("healingamount"));
                    pet.setImage(rs.getString("image"));
                    petList.add(pet);
                }
                return petList;
            }
        });
    }

    // public Pet getPetByUserid(int userid) {
    // return jdbcTemplate.queryForObject(SQL_GET_ALL_PETS_BY_USERID,
    // BeanPropertyRowMapper.newInstance(Pet.class), userid);
    // }

    public Pet getPetByUserid(int userid) {
        SqlRowSet rs = jdbcTemplate.queryForRowSet(SQL_GET_ALL_PETS_BY_USERID, userid);
        // if there's nothing next, then we can return empty
        Pet pet = new Pet();

        if (!rs.next()) {
            return pet;
        }
        pet.setPetid(rs.getInt("petid"));
        pet.setUserid(rs.getInt("userid"));
        pet.setHealing(rs.getInt("healingamount"));
        pet.setImage(rs.getString("image"));
        return pet;
    }

    public Pet addDefaultPet(int userid) throws RegisteringUserFailedException {
        log.info("CharacterRepository>Inserting starter Pet %d".formatted(userid));

        // test
        int USERID = 0;

        int rowsAffected = jdbcTemplate.update(conn -> {
            PreparedStatement prepState = conn.prepareStatement(SQL_INSERT_PET, Statement.RETURN_GENERATED_KEYS);
            prepState.setInt(1, USERID);
            prepState.setInt(2, 15);
            prepState.setString(3, "dragon.avif");
            return prepState;
        }, generatedKeyHolder);

        // Get auto-incremented ID
        int petid = generatedKeyHolder.getKey().intValue();
        log.info("rowsAffected = {}, petid={}", rowsAffected, petid);
        if (petid < 2) {
            throw new RegisteringUserFailedException("cannot make new starter pet for userid=%d".formatted(USERID));
        }
        Pet starterPet = new Pet(userid);
        starterPet.setPetid(petid);
        log.info("created new starter pet {}", starterPet.toString());

        return starterPet;
    }

    // MUST have a new pet created first before accessing this
    // needs testing
    public CharacterDetails addDefaultCharacterWithPet(int userid, int petid) throws RegisteringUserFailedException {
        log.info("CharacterRepository>Inserting starter character %d".formatted(userid));
        int rowsAffected = jdbcTemplate.update(conn -> {
            PreparedStatement prepState = conn.prepareStatement(SQL_INSERT_DEFAULT_CHARACTER,
                    Statement.RETURN_GENERATED_KEYS);
            prepState.setInt(1, userid);
            prepState.setInt(2, petid);
            prepState.setString(3, "character.avif");
            return prepState;
        }, generatedKeyHolder);

        // Get auto-incremented ID
        int characterid = generatedKeyHolder.getKey().intValue();
        log.info("rowsAffected = {}, characterid={}", rowsAffected, characterid);

        CharacterDetails newDefaultChara = new CharacterDetails(characterid, userid, petid);
        log.info("created new starter character %s".formatted(newDefaultChara.toString()));

        return newDefaultChara;
    }

    public Pet getPetByUserId(int userid) {
        return null;
    }

    public List<Pet> getDefaultTradablePets() {

        return jdbcTemplate.query(SQL_GET_ALL_MARKETPLACEPETS, new ResultSetExtractor<List<Pet>>() {
            @Override
            @Nullable
            public List<Pet> extractData(ResultSet rs) throws SQLException,
                    DataAccessException {
                List<Pet> petList = new ArrayList<Pet>();

                while (rs.next()) {
                    Pet pet = new Pet();
                    // System.out.println(rs.getInt("petid"));
                    pet.setPetid(rs.getInt("petid"));
                    pet.setUserid(rs.getInt("userid"));
                    pet.setHealing(rs.getInt("healingamount"));
                    pet.setImage(rs.getString("image"));
                    petList.add(pet);
                }
                return petList;
            }
        });
    }

    public boolean deletePetFromMarketPlace(int petId) {
        int rowaffected = jdbcTemplate.update(SQL_DELETE_PET_FROM_MARKETPLACE, petId);
        log.info("pet with id {} deleted from marketplace", petId);
        return rowaffected > 0 ? true : false;
    }

    public void updateCharacter(CharacterDetails character) throws UpdateCharacterAndPetException {
        if (jdbcTemplate.update(SQL_UPDATE_ENTIRE_CHARACTER,
                character.getHealth(),
                character.getCoinwallet(),
                character.getCurrentpetid(),
                character.getImageUrl(),
                character.getUserid()) <= 0)
            throw new UpdateCharacterAndPetException("update failed");
    }

    public void updatePet(Pet pet) throws UpdateCharacterAndPetException {
        if (jdbcTemplate.update(SQL_UPDATE_PET,
                pet.getUserid(),
                pet.getHealing(),
                pet.getImage(),
                pet.getPetid()) <= 0)
            throw new UpdateCharacterAndPetException("update failed");
    }

}
