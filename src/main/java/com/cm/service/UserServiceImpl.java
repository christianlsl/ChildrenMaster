package com.cm.service;

import com.cm.dao.User;
import com.cm.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

    @Override
    public User getUserById(long id){
        return userRepository.findById(id).orElseThrow(RuntimeException::new);
    }
}
