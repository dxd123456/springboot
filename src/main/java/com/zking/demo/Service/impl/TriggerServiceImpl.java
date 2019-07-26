package com.zking.demo.Service.impl;
import com.zking.demo.Service.TriggerService;
import com.zking.demo.mapper.TriggerMapper;
import com.zking.demo.model.Trigger;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Slf4j
@Service
public class TriggerServiceImpl implements TriggerService{
    //自动装配QuartzConfigration配置类中的Scheduler
    @Autowired
    private Scheduler scheduler;
    //装配Dao层接口
    @Resource
    private TriggerMapper triggerMapper;
    //定时更新任务
    @Override
    @Scheduled(cron = "0 0 0 23 * ?")
    public void refreshTrigger() throws SchedulerException {
        List<Trigger> jobList = triggerMapper.getTriggers();
        if (jobList != null) {

            for (Trigger trigger : jobList) {
                //获得每个任务触发器的状态
                String status = trigger.getStatus();
                //JobBuild中的JobKey是表明Job身份的一个对象，里面封装了Job的name和group
                //TriggerBuild中的TriggerKey封装了Trigger的name和group
                TriggerKey triggerKey = TriggerKey.triggerKey(trigger.getJobName(), trigger.getJobGroup());
                CronTrigger cronTrigger = (CronTrigger) scheduler.getTrigger(triggerKey);
                if (null == cronTrigger) {
                    if (status.equals("0")) { //如果是禁用，则不用创建触发器
                        continue;
                    }
                    JobDetail jobDetail = null;
                    try {
                        //创建JobDetail（数据库中job_name存的任务全路径，这里就可以动态的把任务注入到JobDetail中）
                        jobDetail = JobBuilder.newJob((Class<? extends Job>) Class.forName(trigger.getJobName()))  //trigger.getJobName() => "com.zking.quartz.MyJob1"
                                .withIdentity(trigger.getJobName(), trigger.getJobGroup())
                                .build();
                               //TODO 可以添加一些额外的参数到任务的上下文中
//                            Integer scheduleTriggerId = trigger.getId();
//                            List<ScheduleTriggerParam> paramList = scheduleTriggerParamMapper.listByScheduleTriggerId(scheduleTriggerId);
//                            JobDataMap可以用来保存任何需要传递给任务实例的对象（这些对象要求是可序列化的）
//                            JobDataMap jobDataMap = jobDetail.getJobDataMap();
//                            for (ScheduleTriggerParam p : paramList) {
//                                jobDataMap.put(p.getName(), p.getValue());
//                            }
                        //表达式调度构建器
                        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(trigger.getCron());
                        //按新的cronExpression表达式构建一个新的trigger
                        cronTrigger = TriggerBuilder.newTrigger()
                                .withIdentity(trigger.getJobName(), trigger.getJobGroup())
                                .withSchedule(scheduleBuilder)
                                .build();
                        //把trigger和jobDetail注入到调度器
                        scheduler.scheduleJob(jobDetail, cronTrigger);

                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    //说明查出来的这条任务，已经设置到quartz中了
                    // Trigger已存在，先判断是否需要删除，如果不需要，再判定是否时间有变化
                    if (status.equals("0")) { //如果是禁用，从quartz中删除这条任务
                        JobKey jobKey = JobKey.jobKey(trigger.getJobName(), trigger.getJobGroup());
                        scheduler.deleteJob(jobKey);
                        continue;
                    }
                    String searchCron = trigger.getCron(); //获取数据库的
                    String currentCron = cronTrigger.getCronExpression();
                    if (!searchCron.equals(currentCron)) { //说明该任务有变化，需要更新quartz中的对应的记录
                        //表达式调度构建器
                        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(searchCron);
                        //按新的cronExpression表达式重新构建trigger
                        cronTrigger = cronTrigger.getTriggerBuilder()
                                .withIdentity(triggerKey)
                                .withSchedule(scheduleBuilder)
                                .build();
                        //按新的trigger重新设置job执行
                        scheduler.rescheduleJob(triggerKey, cronTrigger);
                    }
                }
            }
        }
    }

    @Override
    public void add(Trigger trigger) {
        triggerMapper.add(trigger);
    }
}
