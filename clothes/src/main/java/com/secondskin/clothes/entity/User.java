package com.secondskin.clothes.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder


public class User {

    private Long id;
    private String username;
    private String email;
    private String password;
    private String roles;
}