package com.fin.ewalletUserServices;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
@Repository
public interface user_Repository extends JpaRepository<user_impl, Integer> {
                user_impl findByUserId(String user_id);


}