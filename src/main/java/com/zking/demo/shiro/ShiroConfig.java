package com.zking.demo.shiro;
import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
@Configuration
public class ShiroConfig {
    /**
     * 创建ShiroFilterFactoryBean
     */
    @Bean("shiroFiter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("securityManager")SecurityManager securityManager){
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        //设置安全管理器
       bean.setSecurityManager(securityManager);
       bean.setLoginUrl("/admin/toLogin");
       bean.setSuccessUrl("/admin/index");
       bean.setUnauthorizedUrl("/unauthorized");

        /**
         * 添加自定义拦截器
         */
        HashMap<String, Filter> myFilters = new HashMap<>();
        myFilters.put("userAuth", new UserAuthFilter());
        bean.setFilters(myFilters);
        /**
           * Shiro内置过滤器，可以实现权限相关的拦截器
           *    常用的过滤器：
           *       anon: 无需认证（登录）可以访问
           *       authc: 必须认证才可以访问
           *       user: 如果使用rememberMe的功能可以直接访问
           *       perms： 该资源必须得到资源权限才可以访问
           *       role: 该资源必须得到角色权限才可以访问
           */
       LinkedHashMap<String,String> filterMap = new LinkedHashMap<>();
        filterMap.put("/admin/toLogin","anon");
        filterMap.put("/admin/login","anon");
        filterMap.put("/css/**", "anon");
        filterMap.put("/js/**", "anon");
        filterMap.put("/images/**", "anon");
        filterMap.put("/lib/**", "anon");
        filterMap.put("/**", "userAuth");
        bean.setFilterChainDefinitionMap(filterMap);
        return bean;
    }
    @Bean("securityManager")
    public SecurityManager securityManager (@Qualifier("userRealm") UserRealm userRealm){
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(userRealm);
        return  manager;
    }
    /**
     * 创建Realm
     * @return
     */
    @Bean("userRealm")
    public UserRealm getRealm(@Qualifier("credentialMatcher") CredentialMatcher credentialMatcher){
       UserRealm userRealm = new UserRealm();
       userRealm.setCredentialsMatcher(credentialMatcher);
       return  userRealm;
    }
    @Bean("credentialMatcher")
    public CredentialMatcher credentialMatcher(){
        return  new CredentialMatcher();
    }
    /**
     * 配置ShiroDialect，用于thymeleaf和shiro标签配合使用
     */
    @Bean
    public ShiroDialect getShiroDialect(){
        return new ShiroDialect();
    }
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager") SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return creator;
    }
}
