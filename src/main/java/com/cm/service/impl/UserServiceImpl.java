package com.cm.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.RandomUtil;
import com.cm.converter.UserConverter;
import com.cm.dao.User;
import com.cm.dao.UserRepository;
import com.cm.dto.LoginFormDTO;
import com.cm.dto.Result;
import com.cm.dto.UserDTO;
import com.cm.service.EmailService;
import com.cm.service.UserService;
import com.cm.utils.RegexUtils;
import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.cm.utils.RedisConstants.*;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailService emailService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public Result sendCode(String email) {
        // 检查邮箱账号是否有效
        if (RegexUtils.isEmailInvalid(email)) {
            //not match, return error
            return Result.fail("邮箱格式错误！");
        }

        //生成验证码
        String code = RandomUtil.randomNumbers(6);
        //发验证码
        Result sendResult = emailService.sendText("1754583493@qq.com",email,"邮箱验证码",code);
        //保存到redis
        if (sendResult.getSuccess()){
            stringRedisTemplate.opsForValue().set(LOGIN_CODE_KEY + email, code, 2, TimeUnit.MINUTES);
            log.debug("发送并存储验证码成功，code:{}", code);
            return Result.ok();
        }
        return Result.fail("邮件发送失败！");
    }

    @Override
    public Result register(LoginFormDTO loginForm) {
        //check email
        String email = loginForm.getEmail();
        if (RegexUtils.isEmailInvalid(email)) {
            return Result.fail("邮箱格式错误！");
        }
        User user = userRepository.findByEmail(email);
        if (user!=null){
            return Result.fail("邮箱"+email+"已被注册!");
        }
        //check code
        String cacheCode = stringRedisTemplate.opsForValue().get(LOGIN_CODE_KEY + email);
        String code = loginForm.getCode();
        if (cacheCode == null || !cacheCode.equals(code)) {
            return Result.fail("验证码错误");
        }

        //check user exist
        //not exist, create new user
        user = createUserWithEmail(loginForm);

        //save user to redis
        //randomly create token
        String token = UUID.randomUUID().toString(true);
        //User to hash
        UserDTO userDTO = BeanUtil.copyProperties(user, UserDTO.class);
        //save
        Map<String, Object> userMap = BeanUtil.beanToMap(userDTO, new HashMap<>(),
                CopyOptions.create()
                        .setIgnoreNullValue(true).
                        setFieldValueEditor((fieldName,fieldValue)->fieldValue.toString()));
        String tokenKey = LOGIN_USER_KEY+token;
        stringRedisTemplate.opsForHash().putAll(tokenKey, userMap);
        stringRedisTemplate.expire(tokenKey, LOGIN_USER_TTL, TimeUnit.MINUTES);
        //return token
        return Result.ok(token);
    }

    @Override
    public Result resetPasswd(LoginFormDTO loginForm) {
        //check email
        String email = loginForm.getEmail();
        if (RegexUtils.isEmailInvalid(email)) {
            //not match, return error
            return Result.fail("邮箱格式错误！");
        }
        User userInDB = userRepository.findByEmail(email);
        if (userInDB==null){
            return Result.fail("此邮箱"+email+" 还未注册！");
        }
        //check code
        String cacheCode = stringRedisTemplate.opsForValue().get(LOGIN_CODE_KEY + email);
        String code = loginForm.getCode();
        if (cacheCode == null || !cacheCode.equals(code)) {
            //different, error
            return Result.fail("验证码错误");
        }

        if (StringUtils.hasLength(loginForm.getNick_name()) && !userInDB.getNick_name().equals(loginForm.getNick_name())){
            userInDB.setNick_name(loginForm.getNick_name());
        }
        if (loginForm.getAge()>0 && userInDB.getAge()!=loginForm.getAge()){
            userInDB.setAge(loginForm.getAge());
        }
        if (StringUtils.hasLength(loginForm.getPassword()) && !userInDB.getPassword().equals(loginForm.getPassword())){
            userInDB.setPassword(loginForm.getPassword());
        }
        User user = userRepository.save(userInDB);

        //save user to redis
        //randomly create token
        String token = UUID.randomUUID().toString(true);
        //User to hash
        UserDTO userDTO = BeanUtil.copyProperties(user, UserDTO.class);
        //save
        Map<String, Object> userMap = BeanUtil.beanToMap(userDTO, new HashMap<>(),
                CopyOptions.create()
                        .setIgnoreNullValue(true).
                        setFieldValueEditor((fieldName,fieldValue)->fieldValue.toString()));
        String tokenKey = LOGIN_USER_KEY+token;
        stringRedisTemplate.opsForHash().putAll(tokenKey, userMap);
        stringRedisTemplate.expire(tokenKey, LOGIN_USER_TTL, TimeUnit.MINUTES);
        //return token
        return Result.ok(token);

    }

    @Override
    public Result login(LoginFormDTO loginForm){
        String email = loginForm.getEmail();
        if (RegexUtils.isEmailInvalid(email)) {
            return Result.fail("邮箱格式错误！");
        }
        User user = userRepository.findByEmail(email);
        if (user==null){
            return Result.fail("此邮箱"+email+" 还未注册！");
        }
        //check code
        String passwd = user.getPassword();
        if (!passwd.equals(loginForm.getPassword())) {
            return Result.fail("密码错误！");
        }

        //save user to redis
        //randomly create token
        String token = UUID.randomUUID().toString(true);
        //User to hash
        UserDTO userDTO = BeanUtil.copyProperties(user, UserDTO.class);
        //save
        Map<String, Object> userMap = BeanUtil.beanToMap(userDTO, new HashMap<>(),
                CopyOptions.create()
                        .setIgnoreNullValue(true).
                        setFieldValueEditor((fieldName,fieldValue)->fieldValue.toString()));
        String tokenKey = LOGIN_USER_KEY+token;
        stringRedisTemplate.opsForHash().putAll(tokenKey, userMap);
        stringRedisTemplate.expire(tokenKey, LOGIN_USER_TTL, TimeUnit.MINUTES);
        //return token
        return Result.ok(token);
    }

    private User createUserWithEmail(LoginFormDTO loginForm) {
        User user = new User();
        user.setEmail(loginForm.getEmail());
        user.setAge(loginForm.getAge());
        user.setPassword(loginForm.getPassword());
        user.setNick_name(loginForm.getNick_name());
//        String USER_NICK_NAME_PREFIX;
//        user.setNick_name("user_" + RandomUtil.randomString(10));
        userRepository.save(user);
        return user;
    }


    @Override
    public Result getUserByEmail(String email){
        if (RegexUtils.isEmailInvalid(email)) {
            return Result.fail("邮箱格式错误！");
        }
        User user = userRepository.findByEmail(email);
        if (user==null){
            return Result.fail("此邮箱"+email+" 还未注册！");
        }
        return Result.ok(UserConverter.convertUser(user));
    }

    @Override
    public Result getUserByToken(String token) {
        Map<Object, Object> userMap = stringRedisTemplate.opsForHash().entries(LOGIN_USER_KEY+token);
//        UserDTO userDTO = BeanUtil.mapToBean(userMap, UserDTO.class, CopyOptions.create()
//                .setIgnoreNullValue(true)
//                .setFieldValueEditor((fieldName, fieldValue) -> fieldValue.toString();
//                    return null;
//                }));
        System.out.println(userMap);
        UserDTO userDTO = BeanUtil.mapToBean(userMap, UserDTO.class,true);
        return Result.ok(userDTO);
    }


    @Override
    @Transactional
    public Result updateUserById(long id, int age, String nickName, String password) {
        User userInDB = userRepository.findById(id).orElseThrow(RuntimeException::new);

        if (StringUtils.hasLength(nickName) && !userInDB.getNick_name().equals(nickName)){
            userInDB.setNick_name(nickName);
        }
        if (age>0 && userInDB.getAge()!=age){
            userInDB.setAge(age);
        }
        if (StringUtils.hasLength(password) && !userInDB.getPassword().equals(password)){
            userInDB.setPassword(password);
        }
        User user = userRepository.save(userInDB);

        return Result.ok(UserConverter.convertUser(user));
    }
}
