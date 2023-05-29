package dev.leeshuyun.Lifeguild.services;

import java.security.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.leeshuyun.Lifeguild.exceptions.TradePetFailedException;
import dev.leeshuyun.Lifeguild.models.Pet;
import dev.leeshuyun.Lifeguild.models.TransactionLog;
import dev.leeshuyun.Lifeguild.models.TransactionPetAdoptionDetails;
import dev.leeshuyun.Lifeguild.models.User;
import dev.leeshuyun.Lifeguild.repositories.AuthRepository;
import dev.leeshuyun.Lifeguild.repositories.CharacterRepository;
import dev.leeshuyun.Lifeguild.repositories.TransactionLogRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PetAdoptionService {

    @Autowired
    private TransactionLogRepository txnLogRepo;

    @Autowired
    private CharacterRepository charaRepo;

    @Autowired
    private AuthRepository authRepo;

    /**
     * transaction for a pet adopted from the global pool of abandoned pets
     * 
     * @param txnDetails
     * @throws TradePetFailedException
     */
    @Transactional(rollbackFor = TradePetFailedException.class)
    public void tradePet(TransactionPetAdoptionDetails txnDetails) throws TradePetFailedException {

        log.info("Transactional {}", txnDetails.toString());

        // check if there's the users first
        try {

            Optional<User> fromUser = authRepo.findUserByUserId(txnDetails.getFromUserId());
            Optional<User> toUser = authRepo.findUserByUserId(txnDetails.getToUserId());

        } catch (DataAccessException e) {
            throw new TradePetFailedException("pet trading details are wrong");
        }

        String txnId = UUID.randomUUID().toString().substring(0, 8);

        // trying epoch timestamps
        long millis = System.currentTimeMillis();
        Instant timeStamp = Instant.ofEpochMilli(millis);
        // timestamp format: 1972-04-27T23:11:11.044Z
        log.info("timeStamp>>>> " + timeStamp.toEpochMilli());

        TransactionLog txnLog = new TransactionLog(
                txnId,
                txnDetails.getFromUserId(),
                txnDetails.getToUserId(),
                txnId, txnDetails.getAmount(),
                timeStamp);

        // TODO reset the arrivalDate for the pet

        log.info("transaction Log {}", txnLog.toString());

        // all throw TradePetFailedException
        charaRepo.addZenny(txnDetails.getFromUserId(), txnDetails.getAmount());
        charaRepo.deductZenny(txnDetails.getToUserId(), txnDetails.getAmount());
        charaRepo.changeCurrentPet(txnDetails.getPetId(), txnDetails.getToUserId());
        charaRepo.changePetOwner(txnDetails.getPetId(), txnDetails.getToUserId());

        // hm.. handling the exception here allows easier reusablility of the repo
        boolean isSuccessfulDelete = charaRepo.deletePetFromMarketPlace(txnDetails.getPetId());
        log.info("deleting pet successful? {}", isSuccessfulDelete);
        if (!isSuccessfulDelete) {
            throw new TradePetFailedException(" deleting pet off marketplace failed");
        }

        txnLogRepo.insertTransactionLog(txnLog);

        log.info(txnId, txnLog);

    }

    public List<TransactionLog> getTxnLogByUserid(int userid) {
        return txnLogRepo.getTransactionsByUserid(userid);
    }

    public List<Pet> getDefaultTradablePets() {
        return charaRepo.getDefaultTradablePets();
    }
}
