package com.zking.demo.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResultVo<T> implements Serializable {

    private static final long serialVersionUID = -1740653398797943872L;
    private Integer code;
    private String msg;
    private T data;
}
