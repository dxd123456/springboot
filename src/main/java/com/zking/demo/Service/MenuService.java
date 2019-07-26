package com.zking.demo.Service;

import com.zking.demo.common.StatusEnum;
import com.zking.demo.model.Menu;
import com.zking.demo.vo.ResultVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * @author lrl
 */
public interface MenuService {
    /**
     *总管理员查看所得权限
     * @return
     */
    List<Menu> getList();
    /**根据mid删除权限
     * 删除权限
     * @param rid
     * @return
     */
    int delMenu(@Param("mid")Integer rid);
    /**
     * 根据mid删除权限
     * @param mid
     * @return
     */
    int delMenus(@Param("rid") Integer mid);
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
    ResultVo addMenByid(Integer rid, Integer[] mid);
    /**
     * 修改后台菜单状态
     * @param mid
     * @return
     */
    int  updateState(@Param("mid") Integer mid);
     ResultVo updateStates(StatusEnum statusEnum, Integer mid);
    /**
     * 获取相关的所有菜单
     * @param mid
     * @return
     */
    Set<Menu> getAllByMid(@Param("mid") Integer mid);
}
