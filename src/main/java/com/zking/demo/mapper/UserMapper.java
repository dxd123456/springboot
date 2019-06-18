package com.zking.demo.mapper;

import com.zking.demo.model.User;

public interface UserMapper {
    User findByName(String name);
    User fincById(Integer id);
}
