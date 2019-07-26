package com.zking.demo.Service.impl;

import com.zking.demo.Service.MenuService;
import com.zking.demo.common.AdminConst;
import com.zking.demo.common.StatusEnum;
import com.zking.demo.common.impl.ResultEnum;
import com.zking.demo.exception.ResultException;
import com.zking.demo.mapper.MenuMapper;
import com.zking.demo.model.Menu;
import com.zking.demo.util.ResultVoUtil;
import com.zking.demo.util.ShiroUtil;
import com.zking.demo.util.StatusUtil;
import com.zking.demo.vo.ResultVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuMapper menuMapper;
    @Override
    public List<Menu> getList() {
        return menuMapper.getList();
    }
    /**
     * 删除关系表数据
     * @param rid
     * @return
     */
    @Override
    public int delMenu(Integer rid) {
        if( null != rid){
            return menuMapper.delMenu(rid);
        }
        return -1;
    }
    @Override
    public int delMenus(Integer mid) {
        if( null != mid){
            return menuMapper.delMenu(mid);
        }
        return -1;
    }

    /**
     * 获取自定权限资源
     * @param rid
     * @return
     */
    @Override
    public Set<Menu> getByrid(Integer rid) {
        return menuMapper.getByrid(rid);
    }

    /**
     * 为指定角色获取权限
     * @param rid
     * @param mid
     * @return
     */
    @Override
    public int addMenByrid(Integer rid, Integer mid) {
        return menuMapper.addMenByrid(rid,mid);
    }
    public ResultVo addMenByid(Integer rid, Integer[] mid){
        //不允许操作管理员角色
        if(rid== AdminConst.ADMIN_ROLE_ID&&
                ShiroUtil.getSubject().getId()!=AdminConst.ADMIN_ID){
            throw new ResultException(ResultEnum.NO_ADMINROLE_AUTH);
        }
        int i = menuMapper.delMenu(rid);
        if(i!=-1){
            for (Integer integer : mid) {
                addMenByrid(rid,integer);
            }
            return ResultVoUtil.SAVE_SUCCESS;
        }
        return  ResultVoUtil.error("操作失败");
    }
    /**
     * 更新菜单状态
     * @param mid
     * @return
     */
    @Override
    public int updateState(Integer mid) {return menuMapper.updateState(mid);}
    /**
     * 获取相关的所有菜单
     * @param mid
     * @return
     */
    @Override
    public Set<Menu> getAllByMid(Integer mid) {
        return menuMapper.getAllByMid(mid);
    }

    /**
     * 删除处理菜单
     * @param mid
     * @return
     */
    public ResultVo updateStates(StatusEnum statusEnum,Integer mid){
      if(statusEnum==StatusEnum.DELETE&& null !=mid){
              Set<Menu> menus = menuMapper.getAllByMid(mid);
              menus.forEach(menu -> {
                  menuMapper.updateState(menu.getMid());
                  menuMapper.delMenus(menu.getMid());
              });
                   menuMapper.updateState(mid);
                   menuMapper.delMenus(mid);
              return ResultVoUtil.success("操作成功");
      }
             return  ResultVoUtil.error("操作失败");
    }
}
