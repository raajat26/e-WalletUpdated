package com.fin.ewallet.ewalletservices;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface wallet_repo extends JpaRepository<wallet_Impl, Integer> {

    public wallet_Impl findByUserId(String user_id);

    @Transactional
    @Modifying
    @Query("update wallet_Impl w set w.user_balance = w.user_balance + :amount where w.userId= :user_Id")
    public void updateWallet(String user_Id, int amount);



}
