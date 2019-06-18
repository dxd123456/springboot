package com.zking.demo.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Trigger implements Serializable {
    private static final long serialVersionUID = -4364408847147536820L;
    private Integer id;
    private String cron;  //时间表达式

    private String status; //使用状态 0：禁用   1：启用

    private String jobName; //任务名称

    private String jobGroup; //任务分组

}