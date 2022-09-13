package com.fin.eWalletTransactionServices;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.kafka.core.KafkaTemplate;


import java.util.UUID;

@Service
public class transaction_service {

    @Autowired
    transaction_Repo trans_repo;

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    ObjectMapper objectMapper;

    private static final String TOPIC_TRANSACTION_INITIATE = "transactionInitiate";
    private static final String TOPIC_WALLET_UPDATE = "transactionUpdate";



    private static Logger logger = LoggerFactory.getLogger(transaction_service.class);

    public void createTransaction(transaction_req_impl trans_req_impl) throws JsonProcessingException {
        // writing logic for initiating transaction

        transaction_impl trans_impl = transaction_impl.builder()
                .fromUser(trans_req_impl.getFromUserId())
                .toUser(trans_req_impl.getToUserId())
                .amount(trans_req_impl.getAmount())
                .transaction_external_UUID(UUID.randomUUID().toString())
//                .transactionStatus(TransactionStatus.)
                .transactionStatus(TransactionStatus.PENDING)
                .remarks(trans_req_impl.getRem())
                        .build();

        trans_impl = trans_repo.save(trans_impl);
        logger.info("Checkpoint for transaction data got saved into DB till now, proceeding further");
            // now establishing communication with wallet to update wallet everytime
        JSONObject walletUpdateRequest = new JSONObject();
        walletUpdateRequest.put("to", trans_req_impl.getToUserId()); // receiver
        walletUpdateRequest.put("from", trans_req_impl.getFromUserId()); //sender
        walletUpdateRequest.put("amount", trans_req_impl.getAmount());
        walletUpdateRequest.put("transactionId", trans_impl.getTransaction_external_UUID() );

        kafkaTemplate.send(TOPIC_TRANSACTION_INITIATE,
                trans_impl.getTransaction_external_UUID(),
                objectMapper.writeValueAsString(walletUpdateRequest));



//                .transactionStatus(trans_status.PENDING)
//        private TransactionStatus transactionStatus;


                //UUID.randomUUID().toString()


        /////////////////////////// now listening to a new topic to update the status ///////////////////////////////////////////



    }
    @KafkaListener(topics = {TOPIC_WALLET_UPDATE}, groupId = "transaction-group")
    public void updateTransaction(String message) throws JsonProcessingException {
    JSONObject walletUpdateRequestConsuming = objectMapper.readValue(message, JSONObject.class);
    String transactionIdConsumeing = (String) walletUpdateRequestConsuming.get("transactionId");
    String status = (String)walletUpdateRequestConsuming.get("status");


    // now update the transaction status in DB and then notify the user
    trans_repo.updateTransactionStatus(transactionIdConsumeing, TransactionStatus.valueOf(status));



    }

}
