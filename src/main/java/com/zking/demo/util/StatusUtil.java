package com.zking.demo.util;

import com.zking.demo.common.StatusConst;
import com.zking.demo.common.StatusEnum;
import com.zking.demo.common.impl.ResultEnum;
import org.apache.ibatis.executor.result.ResultMapException;

/**
 * @author LRL
 */
public class StatusUtil {
    //逻辑删除语
   public  static final  String sliceDelete="set status=" + StatusConst.DELETE + " WHERE id=?";

   //不等于逻辑删除条件语
    public static final  String notDelete = "status != " + StatusConst.DELETE;

    /**
     * 获取状态 StatusEnum 对象
     * @param param 状态字符参数
     */
    public static StatusEnum getStatusEnum(String param){
        try{
            return  StatusEnum.valueOf(param.toUpperCase());
        }
        catch (IllegalArgumentException e){
            throw  new ResultMapException(String.valueOf(ResultEnum.STATUS_ERROR));
        }
    }
}
