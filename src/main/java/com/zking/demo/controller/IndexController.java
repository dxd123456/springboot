package com.zking.demo.controller;
import com.zking.demo.Service.MenuService;
import com.zking.demo.common.AdminConst;
import com.zking.demo.common.MenuTypeEnum;
import com.zking.demo.common.StatusEnum;
import com.zking.demo.model.Menu;
import com.zking.demo.model.User;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @author lrl
 */
@Controller
public class IndexController {
    @Autowired
    private MenuService menuService;
    /**
     * 获取后台主题内容
     * @return
     */
    @GetMapping("/")
    public String ToIndex(Model model){
        //获取当前用户
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        // 菜单键值对(ID->菜单)
        Map<Long, Menu> keyMenu = new HashMap<>();
        // 管理员实时更新菜单
        if(user.getId().equals(AdminConst.ADMIN_ID)){
            List<Menu> menus = menuService.getList();
            menus.forEach(menu -> {
                keyMenu.put(Long.valueOf(menu.getMid()),menu);
            });
        }else{
            //其他用户获取菜单资源
             user.getRoles().forEach(role -> {
                role.getMenus().forEach(menu -> {
                    if(menu.getStatus().equals(StatusEnum.OK.getCode())){
                        keyMenu.put(Long.valueOf(menu.getMid()),menu);
                    }
                });
            });
        }
        //对菜单树进行封装
        Map<Long,Menu> treeMenu = new HashMap<>();
         keyMenu.forEach((id, menu) ->{
             if(!menu.getType().equals(MenuTypeEnum.NOT_MENU.getCode())){
                   if(keyMenu.get(Long.valueOf(menu.getPid())) != null){
                           keyMenu.get(Long.valueOf(menu.getPid())).getChildren().put(Long.valueOf(menu.getSort()),menu);
                       }else {
                           if(menu.getType().equals(MenuTypeEnum.TOP_LEVEL.getCode())){
                               treeMenu.put(Long.valueOf(menu.getSort()),menu);
                           }
                   }
             }
         });
         model.addAttribute("user",user);
         model.addAttribute("treeMenu",treeMenu);
        return "admin/index";
    }
}
