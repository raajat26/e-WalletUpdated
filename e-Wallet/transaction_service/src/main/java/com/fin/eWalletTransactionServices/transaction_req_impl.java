package com.fin.eWalletTransactionServices;


import lombok.*;

import javax.persistence.Entity;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class transaction_req_impl {
        private String fromUserId;
        private String toUserId;
        private int amount;
        private String rem;

        public boolean isValid(){
           return this.fromUserId !=null && this.fromUserId != ""
           &&
           this.toUserId !=null && this.toUserId != ""
                   && this.amount > 0
                   ;
        }


//        UUID.randomUUID().toString()

}
