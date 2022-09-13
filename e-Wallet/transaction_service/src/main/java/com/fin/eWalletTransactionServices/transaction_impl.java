package com.fin.eWalletTransactionServices;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.transaction.TransactionStatus;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder // helps in buildin the wallet and other features going fwd.

public class transaction_impl implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int trans_id;


    private String transaction_external_UUID;




    private String toUser;
    private String fromUser;

    private int amount;

    private String remarks;

    @CreationTimestamp
    private Date transactionDate;
//
//    @Enumerated(value = EnumType.STRING) // here we are taking value as a String, we can also take value as a Integer
//    private TransactionStatus transactionStatus;
    private String transactionStatus;

}
