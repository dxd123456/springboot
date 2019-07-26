package com.zking.demo.controller;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zking.demo.Service.MenuService;
import com.zking.demo.Service.RoleService;
import com.zking.demo.common.AdminConst;
import com.zking.demo.common.StatusEnum;
import com.zking.demo.common.impl.ResultEnum;
import com.zking.demo.exception.ResultException;
import com.zking.demo.model.Menu;
import com.zking.demo.model.Role;
import com.zking.demo.util.ResultVoUtil;
import com.zking.demo.util.StatusUtil;
import com.zking.demo.vo.ResultVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/sys/role")
public class RoleController {
  @Autowired
  private RoleService roleService;
  @Autowired
  private MenuService menuService;
    /**
     * 跳转到角色主页面
     * @return
     */
    @GetMapping("/Toindex")
  public  String   toIndex(){
        return  "/admin/role/index";
    }

    @GetMapping("/index")
    @ResponseBody
    public Map<String,Object> getAll( @Param("limit") int limit,
                                      @Param("page") int page,
                                      @Param("rname") String rname){
        System.out.println(rname);
        Page<Role> objects = PageHelper.startPage(page, limit);
        List<Role>  roes = roleService.getAll(rname);
        System.out.println("roles"+roes);

        Map<String,Object>  maps = new HashMap();
            maps.put("data",roes);
            maps.put("count",objects.getTotal());
            maps.put("code",0);
            return  maps;
    }

    /**
     * 跳转到增加页面
     */
    @GetMapping("/add")
    public String Toadd(){
        return "/admin/role/add";
    }

    /**
     * 添加角色
     * @param r
     * @return
     */
  @PostMapping("/save")
  @ResponseBody
  public ResultVo saveRole(Role r){
      roleService.addRole(r);
      return  ResultVoUtil.SAVE_SUCCESS;
  }
    /**
     * 后台删除用户
     * @param param
     * @param rid
     * @return
     */
    @RequestMapping("/status/{param}")
    @ResponseBody
    public ResultVo updateStatus(@PathVariable("param") String param,
                                 @RequestParam(value = "rid",required = false) Integer rid){
         StatusEnum statusEnum = StatusUtil.getStatusEnum(param);
         //不能修改超级管理员角色状态
         if(rid== AdminConst.ADMIN_ROLE_ID){
             throw  new ResultException(ResultEnum.NO_ADMINROLE_STATUS);
         }
         if(roleService.updateState(statusEnum,rid)){
             return ResultVoUtil.success(statusEnum.getMessage()+"成功");
         }
         else {
             return  ResultVoUtil.error(statusEnum.getMessage()+"失败");
         }
     }
    /**
     * 跳转到授权页面
     */
    @GetMapping("/auth")
    public String toAuth(@RequestParam(value = "rid") Integer rid,
                         Model model){
        model.addAttribute("rid",rid);
        return "/admin/role/auth";
    }
    /**
     * 获取资源列表
     */
    @GetMapping("/authList")
    @ResponseBody
    public ResultVo authList(@RequestParam(value = "rid") Integer rid){
        //获取指定角色权限资源
        Set<Menu> menus = menuService.getByrid(rid);
        //获取全部菜单
        List<Menu> list = menuService.getList();
        list.forEach(menu -> {
            if(menus.contains(menu)){
                menu.setRemark("auth:true");
            }
            else {
                menu.setRemark("");
            }
        });
        return  ResultVoUtil.success(list);
    }
    /**
     * 保存授权
     * @return
     */
    @PostMapping("/auth")
    @ResponseBody
    public ResultVo auth(@RequestParam(value = "rid") Integer rid,
                         @RequestParam(value = "mids",required = false)Integer[] mids){
        //更新菜单角色
        return  menuService.addMenByid(rid,mids);
    }
}
