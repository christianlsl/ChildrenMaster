package com.cm.controller;

import com.cm.dto.LoginFormDTO;
import com.cm.dto.Result;
import com.cm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;
    /**
     * 发送邮箱验证码
     */
    @PostMapping("/user/code")
    public Result sendCode(@RequestParam("email") String email) {
        return userService.sendCode(email);
    }
    /**注册功能
     * @param loginForm 登录参数，包含email,age,password,nick_name
     * 返回token
     */
    @PostMapping("/user/register")
    public Result register(@RequestBody LoginFormDTO loginForm) {
        return userService.register(loginForm);
    }

    /**登录功能
     * @param loginForm 登录参数，包含邮箱、密码
     * 返回token
     */
    @PostMapping("/user/login")
    public Result login(@RequestBody LoginFormDTO loginForm) {
        return userService.login(loginForm);
    }

    @GetMapping("/user/{id}")
    public Result getUserById(@PathVariable long id) {
        return userService.getUserById(id);
    }

    @PutMapping("/user/{id}")
    public Result updateUserById(@PathVariable long id,
                                            @RequestParam(required = false) String nick_name,
                                            @RequestParam(required = false) int age,
                                            @RequestParam(required = false) String password) {
        return userService.updateUserById(id,age,nick_name,password);
    }
}
