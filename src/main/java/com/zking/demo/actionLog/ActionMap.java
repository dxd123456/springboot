package com.zking.demo.actionLog;

import com.zking.demo.actionLog.model.BusinessMethod;
import com.zking.demo.actionLog.model.BusinessType;

import java.util.HashMap;

public abstract class ActionMap {
    protected HashMap<String,Object> dictory = new HashMap<>();
    public ActionMap(){
        init();
    }
    /**
     * 初始化行为列表
     */
    protected abstract void init();
    /**
     * 获取指定的行为
     * @param key
     * @return
     */
    public Object get(String key){
        return  this.dictory.get(key);
    }
    /**
     * 添加行为日志
     * @param key
     * @param modelType
     */
    public void put(String key,Object modelType){
        this.dictory.put(key,modelType);
    }
    /**
     * 添加行为-默认类型(业务)
     * @param key 行为key
     * @param message 日志消息
     */
    public void put(String key,String message){
        this.dictory.put(key,new BusinessType(message));
    }
    /**
     * 添加行为方法名
     * @param key 行为key
     * @param modelMethod 模型方法名对象
     */
    public void putMethod(String key,Object modelMethod){
        this.dictory.put(key,modelMethod);
    }
    /**
     * 添加行为方法名-默认类型(业务)
     * @param key 行为key
     * @param name 日志名称
     * @param method 方法名
     */
    public void putMethod(String key,String name,String method){
        this.dictory.put(key,new BusinessMethod(method));
    }
    /**
     * 添加行为方法名-默认类型(业务)
     * @param key 行为key
     * @param method 方法名
     */
    public void putMethod(String key,String method){
        this.dictory.put(key,new BusinessMethod(method));
    }

}
