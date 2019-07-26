package com.zking.demo.Service.impl;

import com.zking.demo.Service.RoleService;
import com.zking.demo.common.StatusEnum;
import com.zking.demo.common.impl.ResultEnum;
import com.zking.demo.exception.ResultException;
import com.zking.demo.mapper.MenuMapper;
import com.zking.demo.mapper.RoleMapper;
import com.zking.demo.model.Role;
import com.zking.demo.model.User;
import com.zking.demo.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author LRL
 */
@Service("roleService")
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private MenuMapper menuMapper;
    /**
     * 获取所有的角色列表
     * @return
     */
    @Override
    public List<Role> getAll(String rname) {
        return roleMapper.getAll(rname);
    }

    /**
     * 用户查看自己的角色,并跳转添加角色页面
     * @param id
     * @return
     */
    @Override
    public List<Role> getByid(Integer id) {
        if(null!=id){
            return roleMapper.getByid(id);
        }
        return  null;
    }
    /**
     * 删除用户角色
     * @param id
     * @return
     */
    @Override
    public int deleteRole(Integer id) {
        if(null!=id){
            return roleMapper.deleteRole(id);
        }
        return 0;
    }
    /**
     * 给用户添加角色
     * @param u
     * @param r
     * @return
     */
    @Override
    public int addRoleByid(User u, Role r) {
        if(null!=u&&null!=r){
            return roleMapper.addRoleByid(u,r);
        }
        return 0;
    }
    /**
     * 修改后台角色状态
     * @param rid
     * @param state
     * @return
     */
    @Override
    public int updateState(Integer rid, Integer state) {
        if(null !=rid && null !=state){
            return roleMapper.updateState(rid,state);
        }
       return  -1;
    }

    /**
     * 后台角色删除的方法
     * @param statusEnum
     * @param rid
     * @return
     */
    public Boolean updateState(StatusEnum statusEnum,Integer rid){
       if(statusEnum == StatusEnum.DELETE){
           menuMapper.delMenu(rid);
       }
       return  roleMapper.updateState(rid,3)>0;
    }
    /**
     * 增加后台角色
     * @param role
     * @return
     */
    @Override
    public int addRole(Role role) {
      if(null != role){
          List<Role> all = roleMapper.getAll(null);
          for (Role roles : all) {
                  if(role.getRname().equals(roles.getRname())||role.getRname().indexOf(" ") !=-1){
                      throw new ResultException(ResultEnum.ROLE_EXIST);
              }
          }
          Date date = new Date();
          role.setCreateDate(date);
          role.setUpdateDate(date);
          role.setUpdateDate(null);
          role.setState(1);
           return roleMapper.addRole(role);
      }
        return 0;
    }

}
