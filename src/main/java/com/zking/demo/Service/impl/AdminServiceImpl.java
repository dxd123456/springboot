package com.zking.demo.Service.impl;

import com.zking.demo.Service.AdminService;
import com.zking.demo.common.AdminConst;
import org.springframework.stereotype.Service;

@Service("adminService")
public class AdminServiceImpl implements AdminService {
     /**
     * 专门写一个方法用来判断是否是管理员
     * @param id
     * @return
     */
        @Override
    public Boolean isAdmin(Integer id) {
        if(id== AdminConst.ADMIN_ID){
            return  true;
        }
        return false;
    }
}
