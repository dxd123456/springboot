package com.zking.demo.Service;
import com.zking.demo.common.StatusEnum;
import com.zking.demo.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * @author LRL
 */
public interface UserService {
    /**
     * 根据用户名查询
     * @param name
     * @return
     */
    User findByName(@Param("name") String name);
    /**
     * 后台查看用户数据
     * @return
     */
    List<User> getAll();
    /**
     * 增加用户
     * @param user
     * @return
     */
    int addUser(User user);
    /**
     * 修改后台用户状态
     * @param id
     * @return
     */
    int updateState(@Param("id")Integer id);
    /**
     * 后台用户删除方法
     * @param statusEnum
     * @param id
     * @return
     */
     Boolean updateState(StatusEnum statusEnum, Integer id);

    /**
     * 单个查询用户
     * @param id
     * @return
     */
    User fincById(@Param("id") Integer id);
    /**
     * 修改用户基本信息
     * @param u
     * @return
     */
     int updateUser(User u);
    /**
     * 查询后台所有的管理员名字
     * @return
     */
    Set<String> getName();

}
