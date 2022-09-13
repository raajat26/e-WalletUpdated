package com.fin.ewallet.ewalletservices;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class wallet_service {
    private static Logger logger = LoggerFactory.getLogger(wallet_service.class);
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    wallet_repo walletRepository;

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;
    private static final String WALLET_CREATE_TOPIC = "walletCreate";
    private static final String TOPIC_TRANSACTION_INITIATE = "transactionInitiate";

    private static final String TOPIC_WALLET_UPDATE = "transactionUpdate";
//    private static final String WALLET_CREATE_TOPIC = "wallet_create::";
    @KafkaListener(topics = {WALLET_CREATE_TOPIC}, groupId = "wallet-group")
    public void createWallet(String message) throws JsonProcessingException {
//        JSONObject jsonObject = objectMapper.convertValue(message, JSONObject.class);

        JSONObject jsonObject = objectMapper.readValue(message, JSONObject.class); //previously error came because usage of ConvertValue instead of Readvalue, since we are writing the object in User class, we should read it here
        logger.info("this line is executed after initializing JSON object");
        walletRepository.save(wallet_Impl.
                builder()
                .userId((String) jsonObject.get("userId"))
                .user_balance((Integer) jsonObject.get("balance")).build());
        // there was an error: instead of balance, the user_balance was being used; now sorted User_service and wallet_service are working fine

        logger.info("here the walletRepository has saved the data");
    }
    //////////////////////////// the below wallet consumption updates the wallet everytime //////////////////////////////////
    @KafkaListener(topics = {TOPIC_TRANSACTION_INITIATE }, groupId = "transaction-group")
    public void updateWallet(String message) throws JsonProcessingException {
        JSONObject walletUpdateRequest  = objectMapper.readValue(message, JSONObject.class);
            // fields like to from and amount will come here
        String toUserId = (String)walletUpdateRequest.get("to"); // receiver
        String fromUserId = (String)walletUpdateRequest.get("from"); //sender
        int amount = (Integer) walletUpdateRequest.get("amount"); //get instead of put
        String transactionId = (String) walletUpdateRequest.get("transactionId"); // sending unique UUID key as transaction ID

        wallet_Impl fromWallet = walletRepository.findByUserId(fromUserId);


        ///////////////////////////////////////// below code to create a new request and send the statua via a kafka topic ///////////////////////////

        JSONObject transactionUpdateRequest = new JSONObject();
        transactionUpdateRequest.put("transactionId", transactionId);


        if(fromWallet!=null && fromWallet.getUser_balance() < amount)
        {   transactionUpdateRequest.put("status", "FAILED");
        }
        else {
            walletRepository.updateWallet(fromUserId, 0 - amount);
            walletRepository.updateWallet(toUserId, amount);
            transactionUpdateRequest.put("status", "SUCCESS");
            // since we are using only one function to update the wallet and repository, we are giving negative balance for the sender and positive balance for the receiver since he is receiving the money
        }
        kafkaTemplate.send(TOPIC_WALLET_UPDATE, transactionId, objectMapper.writeValueAsString(transactionUpdateRequest));


    }

}
