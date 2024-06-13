package com.cm.dto;

import lombok.Data;

@Data
public class LoginFormDTO {
    private String email;
    private String code;
    private int age;
    private String nick_name;
    private String password;
}
