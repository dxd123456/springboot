package com.zking.demo.mapper;

import com.zking.demo.model.Menu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;

/**
 * @author lrl
 */
@Service
public interface MenuMapper {
    /**
     *获取所有权限
     * @return
     */
    List<Menu> getList();
    /**
     * 根据rid删除权限
     * @param rid
     * @return
     */
    int delMenu(@Param("rid") Integer rid);
    /**
     * 根据mid删除权限
     * @param mid
     * @return
     */
    int delMenus(@Param("mid") Integer mid);
    /**
     * 获取指定权限资源
     */
    Set<Menu> getByrid(@Param("rid") Integer rid);
     /**
     * 为指定角色添加权限
     * @param rid
     * @param mid
     * @return
     */
    int addMenByrid(@Param("rid") Integer rid, @Param("mid") Integer mid);

    /**
     * 修改后台菜单状态
     * @param mid
     * @return
     */
    int  updateState(@Param("mid") Integer mid);
    /**
     * 获取相关的所有菜单
     * @param mid
     * @return
     */
    Set<Menu> getAllByMid(@Param("mid") Integer mid);


}
