package com.fin.eWalletTransactionServices;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface transaction_Repo extends JpaRepository<transaction_impl, Integer> {
    @Query("update transaction_impl t set t.transactionStatus = :transactionStatus where t.transaction_external_UUID = :trId")
    @Modifying
    @Transactional
    public void updateTransactionStatus(String trId, TransactionStatus transactionStatus);
}
