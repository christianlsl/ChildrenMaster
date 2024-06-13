package com.cm.dao;

import jakarta.persistence.*;
import lombok.Data;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Data
@Table(name="user")
public class User {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = IDENTITY)
    private long id;
    @Column(name="email")
    private String email;
    @Column(name="password")
    private String password;
    @Column(name="nick_name")
    private String nick_name;
    @Column(name="age")
    private int age;
}
