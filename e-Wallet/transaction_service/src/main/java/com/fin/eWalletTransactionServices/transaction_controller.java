package com.fin.eWalletTransactionServices;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class transaction_controller {
    @Autowired
    transaction_service trans_service;


    @PostMapping("/transaction/sendMoney")
    public void sendMoney(@RequestBody transaction_req_impl trans_req_impl) throws JsonProcessingException {

        if(trans_req_impl.isValid()){  // this valid function checks if a transaction is valid or not
            trans_service.createTransaction(trans_req_impl);

        }




    }
//    public void transferMoney(@RequestParam("fromUser") String fromUserId,
//                              @RequestParam("toUser") String toUserId,
//                              @RequestParam("amount") int amount,
//                              @RequestParam("rem") String rem){

}
