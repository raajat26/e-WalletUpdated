package com.fin.ewalletUserServices;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class user_impl implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true)
    private String userId;


    @Column(unique = true)
    private String emailId;

    private int age;
    private String name;
//    private String remarks;


}