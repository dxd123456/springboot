package com.zking.demo.Service;

public interface AdminService {

    /**
     * 专门写一个方法用来判断是否是管理员
     * @param id
     * @return
     */
    Boolean  isAdmin(Integer id);
}
