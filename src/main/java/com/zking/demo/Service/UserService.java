package com.zking.demo.Service;

import com.zking.demo.mapper.UserMapper;
import com.zking.demo.model.User;

public interface UserService {
    User findByName(String name);
    User  fincById(Integer id);
}
