package com.zking.demo.common;

import lombok.Getter;

/**
 * @author
 */
@Getter
public enum ActionLogEnum {
    BUSINESS((byte)1, "业务"),
    LOGIN((byte)2, "登录"),
    SYSTEM((byte)3, "系统");

    private Byte code;

    private String message;

    ActionLogEnum(Byte code, String message) {
        this.code = code;
        this.message = message;
    }
}
