package com.zking.demo.controller;
import com.zking.demo.Service.MenuService;
import com.zking.demo.common.StatusEnum;
import com.zking.demo.model.Menu;
import com.zking.demo.util.HttpServletUtil;
import com.zking.demo.util.ResultVoUtil;
import com.zking.demo.util.StatusUtil;
import com.zking.demo.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Controller
@RequestMapping("/sys/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;
    /**
     * 返回主界面
     */
    @GetMapping("/index")
    public  String toIndex(Model model){
        String search = HttpServletUtil.getRequest().getQueryString();
        model.addAttribute("search",search);
        return "/admin/menu/index";
    }
    /**
     * 菜单数据列表
     */
    @GetMapping("/list")
    @ResponseBody
    public ResultVo list(){
        List<Menu> list = menuService.getList();
        return ResultVoUtil.success(list);
    }
    /**
     * 删除菜单
     * @return
     */
    @GetMapping("/status/{param}")
    @ResponseBody
    public ResultVo del(@PathVariable("param") String param,
                       @RequestParam(value = "mid",required = false) Integer mid){
       StatusEnum statusEnum = StatusUtil.getStatusEnum(param);
       return  menuService.updateStates(statusEnum,mid);
    }

   @GetMapping("/edit")
   @ResponseBody
    public ResultVo edit(@RequestParam(value = "mid",required = false) Integer mid){
        return  null;
    }

}
