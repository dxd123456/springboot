package com.zking.demo.Service;

import com.zking.demo.common.StatusEnum;
import com.zking.demo.model.Role;
import com.zking.demo.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleService {
    /**
     * 获取所有的角色列表
     * @return
     */
    List<Role>getAll(@Param("rname")String rname);
    /**
     * 获取当前用户的角色
     */
    List<Role>getByid(Integer id);
    /**
     * 删除权限
     * @param id
     * @return
     */
    int deleteRole(Integer id);

    /**
     * 给用户添加角色
     * @param u
     * @param r
     * @return
     */
    int addRoleByid(@Param("user")User u, @Param("role") Role r);

    /**
     * 修改后台角色状态
     * @param rid
     * @return
     */
    int updateState(@Param("rid")Integer rid,@Param("state") Integer state);
    /**
     * 后台用户删除方法
     * @param statusEnum
     * @param id
     * @return
     */
    public Boolean updateState(StatusEnum statusEnum, Integer id);
    /**
     * 增加角色
     * @param role
     * @return
     */
    int addRole(Role role);
}
