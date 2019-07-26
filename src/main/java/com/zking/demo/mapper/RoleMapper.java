package com.zking.demo.mapper;

import com.zking.demo.model.Role;
import com.zking.demo.model.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author LRL
 */
@Service("roleMapper")
public interface RoleMapper {
    /**
     * 获取所有的角色列表
     * @return
     */
    List<Role>getAll(@Param("rname")String rname);
    /**
     *指定用户获取对应的权限
     * @param id
     * @return
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
    int addRoleByid(@Param("user")User u,@Param("role") Role r);
    /**
     * 修改后台角色状态
     * @param rid
     * @return
     */
    int updateState(@Param("rid")Integer rid,@Param("state") Integer state);
    /**
     * 增加角色
     * @param role
     * @return
     */
    int addRole(Role role);
}
