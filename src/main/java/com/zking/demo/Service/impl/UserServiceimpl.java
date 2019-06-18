package com.zking.demo.Service.impl;

import com.zking.demo.Service.UserService;
import com.zking.demo.mapper.UserMapper;
import com.zking.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceimpl implements UserService {
  @Autowired
   private UserMapper userMapper;
    @Override
    public User findByName(String name) {
        return userMapper.findByName(name);
    }

    @Override
    public User fincById(Integer id) {
        return userMapper.fincById(id);
    }
}
