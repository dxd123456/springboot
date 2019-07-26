package com.zking.demo.Service.impl;

import com.zking.demo.Service.UserService;
import com.zking.demo.common.AdminConst;
import com.zking.demo.common.StatusEnum;
import com.zking.demo.common.impl.ResultEnum;
import com.zking.demo.exception.ResultException;
import com.zking.demo.mapper.UserMapper;
import com.zking.demo.model.User;
import com.zking.demo.util.ResultVoUtil;
import com.zking.demo.util.ShiroUtil;
import org.apache.ibatis.executor.result.ResultMapException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * @author LRL
 */
@Service("userService")
public class UserServiceimpl implements UserService {
  @Autowired
   private UserMapper userMapper;

    /**
     * 查询用户的所有信息
     * @param name
     * @return
     */
    @Override
    public User findByName(String name) {
        return userMapper.findByName(name);
    }
    /**
     * 查询后台数据
     * @return
     */
    @Override
    public List<User> getAll() {
        return userMapper.getAll();
    }
    /**
     * 增加后台用户
     * @param user
     * @return
     */
    @Override
    public int addUser(User user) {
        if(null!=user){
            return  userMapper.addUser(user);
        }
        return  -1;
    }
    /**
     * 修改状态方法
     * @param id
     * @return
     */
    @Override
    public int updateState(Integer id) {
        return userMapper.updateState(id);
    }
    /**
     * 后台用户删除方法
     * @param statusEnum
     * @param id
     * @return
     */
    public Boolean updateState(StatusEnum statusEnum, Integer id) {
        if(ShiroUtil.getSubject().getId()==id&&ShiroUtil.getSubject().getId()!=AdminConst.ADMIN_ID){
            throw  new ResultException(ResultEnum.NOT_ALLOWED_YOURSELF);
        }
        if(statusEnum==StatusEnum.DELETE){
            if(userMapper.delUr(id)!=-1){
                return userMapper.updateState(id)>0;
            }
        }
            return false;
    }
    /**
     * 查询单个用户
     * @param id
     * @return
     */
    @Override
    public User fincById(Integer id) {
        if(null != id){
            return userMapper.fincById(id);
        }
       return  null;
    }

    /**
     * 修改用户基本信息
     * @param u
     * @return
     */
    @Override
    public int updateUser(User  u) {
        if(null != u ){
            if(u.getId()== AdminConst.ADMIN_ID){
                throw new ResultException(ResultEnum.NO_ADMIN_STATUS);
            }
            if(ShiroUtil.getSubject().getId()==u.getId()&&ShiroUtil.getSubject().getId()!=AdminConst.ADMIN_ID){
                throw  new ResultException(ResultEnum.NOT_ALLOWED_YOURSELF);
            }
            return userMapper.updateUser(u);
        }
          return  -1;
    }

    @Override
    public Set<String> getName() {
        return userMapper.getName();
    }

}
