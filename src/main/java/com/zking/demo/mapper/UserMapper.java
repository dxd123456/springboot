package com.zking.demo.mapper;
import com.zking.demo.common.StatusEnum;
import com.zking.demo.model.User;
import com.zking.demo.vo.ResultVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Set;

/**
 * @author lrl
 */
public interface UserMapper {
    /**
     * 后台管理员登录信息
     * @param name
     * @return
     */
    User findByName(@Param("name") String name);

    /**
     * 管理员后台查看用户数据
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
     * 删除关系表
     * @param id
     * @return
     */
    int delUr(@Param("id") Integer id);

    /**
     * 修改后台用户状态
     * @param id
     * @return
     */
    int updateState(@Param("id")Integer id);

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
