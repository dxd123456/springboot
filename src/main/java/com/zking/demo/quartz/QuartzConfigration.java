package com.zking.demo.quartz;

import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.io.IOException;
import java.util.Properties;

@Configuration
public class QuartzConfigration  {

    @Autowired
    private MyJobFactory myJobFactory;

    //获取工厂bean
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        try {
            //设置加载quartz.properties文件
            schedulerFactoryBean.setQuartzProperties(quartzProperties());
            //设置JobFactory
            schedulerFactoryBean.setJobFactory(myJobFactory);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return schedulerFactoryBean;
    }

    //指定quartz.properties
    @Bean
    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
        propertiesFactoryBean.afterPropertiesSet();  //初始化后调用
        return propertiesFactoryBean.getObject();
    }

    //创建schedule
    @Bean(name = "scheduler")
    public Scheduler scheduler() {
        return schedulerFactoryBean().getScheduler();
    }

}
