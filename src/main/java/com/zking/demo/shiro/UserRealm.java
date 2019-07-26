package com.zking.demo.shiro;

import com.zking.demo.Service.UserService;
import com.zking.demo.model.Menu;
import com.zking.demo.model.Role;
import com.zking.demo.model.User;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class UserRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;
    /**
     * 执行授权逻辑
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("执行授权逻辑");
        User user = (User)principals.fromRealm(this.getClass().getName()).iterator().next();
        List<String> menusList = new ArrayList<>();
        List<String> roleNameList = new ArrayList<>();
        Set<Role> roles = user.getRoles();
        if(CollectionUtils.isEmpty(roles)){
            for (Role role : roles) {
                Set<Menu> menus = role.getMenus();
                if(CollectionUtils.isEmpty(menus)){
                    for (Menu menu : menus) {
                        menusList.add(menu.getPerms());
                    }
                }
            }
        }
        //给资源进行授权
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addStringPermissions(menusList);
        info.addRoles(roleNameList);
        return info;
    }
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("执行认证逻辑");
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();
        User user = userService.findByName(username);
        System.out.println("user====="+user);
        if(null == user){
             //用户名不存在
             return null;//shiro底层会抛出UnKnowAccountException
         }
         //2.判断密码
         return new SimpleAuthenticationInfo(user, user.getPassword(),this.getClass().getName());
    }
}
