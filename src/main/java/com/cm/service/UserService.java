package com.cm.service;

import com.cm.dto.LoginFormDTO;
import com.cm.dto.Result;

public interface UserService {
    Result sendCode(String phone);

    Result register(LoginFormDTO loginForm);

    Result resetPasswd(LoginFormDTO loginForm);

    Result login(LoginFormDTO loginForm);

    public Result getUserById(long id);

    Result updateUserById(long id, int age, String nickName, String email);


}
