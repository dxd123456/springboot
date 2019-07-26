package com.zking.demo.util;

import com.zking.demo.model.User;
import org.apache.shiro.SecurityUtils;

public class ShiroUtil {
    /**
     * 获取ShiroUser对象
     */
    public static User getSubject(){
        return (User) SecurityUtils.getSubject().getPrincipal();
    }
    /**
     * 获取用户IP地址
     */
    public static String getIp(){
        return SecurityUtils.getSubject().getSession().getHost();
    }


}
