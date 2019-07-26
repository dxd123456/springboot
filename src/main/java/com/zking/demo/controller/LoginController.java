package com.zking.demo.controller;
import com.zking.demo.model.User;
import com.zking.demo.url.URL;
import com.zking.demo.util.ResultVoUtil;
import com.zking.demo.vo.ResultVo;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/admin")
public class LoginController {

   /* @RequestMapping("/add")
    public String add(){

        return "/user/add";
    }
    @RequestMapping("/update")
    public String update(){
        return "/user/update";
    }


    @RequestMapping("/noAuth")
    public String noAuth(){
        return "/noAuth";
    }
    *//**
     * 测试thymeleaf
     *//*
    @RequestMapping("/testThymeleaf")
    public String testThymeleaf(){
        System.out.println("执行了testThymeleaf方法");
        //返回test.html
        return "admin/index";
    }*/
    /**
     * 进入登录界面
     * @return
     */
    @RequestMapping("/toLogin")
    public String toLogin(){
        return "admin/login";
    }
    /**
     * 登录逻辑处理
     */
    @PostMapping("/login")
    @ResponseBody
    public ResultVo login(@Param("name") String name,
                          @Param("password") String password
                           ){
        System.out.println("name"+name+"password"+password);
        //1.获取Subject
        Subject subject = SecurityUtils.getSubject();
        //2.封装用户数据
        UsernamePasswordToken token = new UsernamePasswordToken(name,password);
        //3.执行登录方法
        try {
            subject.login(token);
            //验证完成
            User user = (User)subject.getPrincipal();
            System.out.println("完成后的user"+user);
            //登录成功,跳转到test.html
            return ResultVoUtil.success("登录成功", new URL("/"));
        } catch (UnknownAccountException e) {
            //登录失败:用户名不存在
            return  ResultVoUtil.error("您不是后台管理员吧");
        }catch (AuthenticationException e) {
            //登录失败:密码错误
            return ResultVoUtil.error("用户名或密码错误");
        }
    }
    /**
     * 退出登录
     * @return
     */
   @GetMapping("/outLogin")
   public String outLogin(){
      SecurityUtils.getSubject().logout();
        return "redirect:/admin/toLogin";
    }
    /**
     * 处理错误页面
     */
    @GetMapping("/error")
    public String handleError(Model model, HttpServletRequest request){
         Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        String errorMsg = "好像出错了呢！";
         if(statusCode==400){
             errorMsg = "页面找不到了！好像是去火星了~";
         }
         model.addAttribute("statusCode",statusCode);
         model.addAttribute("msg",errorMsg);
        return "/system/error";
    }
}
