package com.zking.demo.actionLog.model;

import com.zking.demo.actionLog.ActionMap;
import com.zking.demo.common.ActionLogEnum;
import lombok.Getter;

/**
 * @author lrl
 */
@Getter
public class BusinessType extends ActionModel {
    //日志名称
    protected  String name;
    //日志消息
    protected String message;
    //日志类型
    protected  Byte type= ActionLogEnum.BUSINESS.getCode();

    public BusinessType(String message) {
        this.message = message;
    }
    public BusinessType(String name, String message) {
        this.name = name;
        this.message = message;
    }
}
