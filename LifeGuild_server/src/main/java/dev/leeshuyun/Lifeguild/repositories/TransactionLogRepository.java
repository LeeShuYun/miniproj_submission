package dev.leeshuyun.Lifeguild.repositories;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import dev.leeshuyun.Lifeguild.exceptions.TradePetFailedException;
import dev.leeshuyun.Lifeguild.models.TransactionLog;
import dev.leeshuyun.Lifeguild.models.TransactionPetAdoptionDetails;

import static dev.leeshuyun.Lifeguild.utils.Constants.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import static dev.leeshuyun.Lifeguild.utils.Constants.*;

@Repository
public class TransactionLogRepository {

    Logger logger = LoggerFactory.getLogger(TransactionLogRepository.class);

    @Autowired
    MongoTemplate mongoTemplate;

    public void insertTransactionLog(TransactionLog txnLog) throws TradePetFailedException {
        Document txn = new Document();
        txn.put("transactionId", txnLog.getTransactionId());
        txn.put("fromUserId", txnLog.getFromUserId());
        txn.put("toUserId", txnLog.getToUserId());
        txn.put("petId", txnLog.getPetId());
        txn.put("amount", txnLog.getAmount());
        txn.put("timestamp", txnLog.getTimestamp());
        mongoTemplate.insert(txn, COLLECTION_TRANSACTIONSLOG);
        logger.info("Pet Adoption Transaction recorded into MongoDB: " + txn.toString());
    }

    public List<TransactionLog> getTransactionsByUserid(String userid) {
        Criteria criterial = Criteria.where("toUserId").is(userid);
        Query query = Query.query(criterial);
        return mongoTemplate.find(query, TransactionLog.class, COLLECTION_TRANSACTIONSLOG);
    }
}
