package com.zking.demo.mapper;

import com.zking.demo.model.Trigger;

import java.util.List;

public interface TriggerMapper {

    //添加任务
    void add(Trigger trigger);

    //获得所有任务
    List<Trigger> getTriggers();

}
