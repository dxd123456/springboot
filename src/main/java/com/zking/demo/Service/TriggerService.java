package com.zking.demo.Service;

import com.zking.demo.model.Trigger;
import org.quartz.SchedulerException;

public interface TriggerService {

    //更新任务
    void refreshTrigger() throws SchedulerException;

    //添加任务
    void add(Trigger trigger);

}
