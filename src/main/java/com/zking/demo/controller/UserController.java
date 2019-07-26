package com.zking.demo.controller;
import com.zking.demo.Service.AdminService;
import com.zking.demo.Service.RoleService;
import com.zking.demo.Service.UserService;
import com.zking.demo.common.AdminConst;
import com.zking.demo.common.StatusEnum;
import com.zking.demo.common.impl.ResultEnum;
import com.zking.demo.exception.ResultException;
import com.zking.demo.model.Role;
import com.zking.demo.model.User;
import com.zking.demo.util.ResultVoUtil;
import com.zking.demo.util.ShiroUtil;
import com.zking.demo.util.StatusUtil;
import com.zking.demo.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@Controller
@RequestMapping("/sys/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private  Role role;
    @Autowired
    private User user;
    @GetMapping("/index")
    public String toIndex(Model model){
        Map<Long,Object> map = new HashMap<>();
        List<User> user = userService.getAll();
        user.forEach(user1 -> {
            map.put(Long.valueOf(user1.getId()),user1);
        });
       model.addAttribute("users",user);
        return  "/admin/user/index";
    }
    /**
     * 跳转到role页面
     * @return
     */
    @GetMapping(value = "/role/{id}")
    public String toRole(@PathVariable("id") Integer id,
                                             Model model){
        List<Role> myroles = roleService.getByid(id);
        List<Role> roles = roleService.getAll(null);
        //存放用户id
        model.addAttribute("userId",id);
        //获取用户指定角色数据
        model.addAttribute("authRoles",myroles);
        //获取所有的角色列表
        model.addAttribute("roles",roles);
        return "/admin/user/role";
    }

    /**
     * 保存用户角色信息
     */
    @PostMapping("/role")
    @ResponseBody
    public ResultVo auth(@RequestParam(value = "id")Integer id,
                         @RequestParam(value = "rid")Integer[] rid){
            if(id==AdminConst.ADMIN_ID && ShiroUtil.getSubject().getId()!=AdminConst.ADMIN_ID){
               throw new ResultException(ResultEnum.NO_ADMIN_AUTH);
             }
             if(Arrays.asList(rid).contains(1)){
                 throw  new  ResultException(ResultEnum.NO_ADMINROLE_AUTH);
             }
               user.setId(id);
               int n = roleService.deleteRole(user.getId());
                if(n!=-1) {
                    for (Integer roles : rid) {
                            role.setRid(roles);
                            roleService.addRoleByid(user, role);
                    }
                }
              return ResultVoUtil.SAVE_SUCCESS;
      }

    /**
     * 跳转添加页面
     * @return
     */
    @GetMapping("/add")
    public String Toadd(){
          return "/admin/user/add";
    }


    /**
     * 增加后台员工
     * @param u
     * @return
     */
    @PostMapping("/save")
    @ResponseBody
    public ResultVo addUser(User u){
        if(""==u.getName()||""==u.getPassword()){
            return  ResultVoUtil.error("账号或者密码为空");
        }
        Set<String> name = userService.getName();
        for (String s : name) {
            if(u.getName().equals(s)){
                return  ResultVoUtil.error("该用户名已经存在");
            }
        }
        u.setState(1);
        int i = userService.addUser(u);
        if(i!=-1){
            return ResultVoUtil.SAVE_SUCCESS;
        }
        return  ResultVoUtil.error("保存失败");
    }

    /**
     * 删除用户
     * @param param
     * @param id
     * @return
     */
    @RequestMapping("/status/{param}")
    @ResponseBody
    public ResultVo updateStatus(@PathVariable("param") String param,
                                  @RequestParam(value = "id",required = false) Integer id){
        //更新状态
        StatusEnum statusEnum = StatusUtil.getStatusEnum(param);
        //不能修改超级管理员的权限
        if(id==AdminConst.ADMIN_ID){
            throw new ResultException(ResultEnum.NO_ADMIN_STATUS);
        }
        if(userService.updateState(statusEnum,id)){
           return ResultVoUtil.success(statusEnum.getMessage()+"成功");
        }
         else {
             return ResultVoUtil.error(statusEnum.getMessage()+"失败，请重新操作");
        }
    }

    /**
     * 编辑用户
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/edit/{id}")
    public String toDetail(@PathVariable("id") Integer id,
                           Model model){
        User user = userService.fincById(id);
        model.addAttribute("user",user);
        return "/admin/user/edit";
    }

    /**
     * 修改基本信息
     * @param u
     * @return
     */
     @PostMapping("/edit")
     @ResponseBody
    public ResultVo edit(User u){
         if(""==u.getName()||""==u.getPassword()){
             return  ResultVoUtil.error("账号或者密码为空");
         }
         Set<String> name = userService.getName();
         for (String s : name) {
             if(u.getName().equals(s)){
                 return  ResultVoUtil.error("该用户名已经存在");
             }
         }
         int i = userService.updateUser(u);
         if(i!=-1){
             return ResultVoUtil.SAVE_SUCCESS;
         }
         return  ResultVoUtil.error("保存失败");
       }
}
