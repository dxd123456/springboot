package com.zking.demo.common;

import lombok.Getter;

@Getter
public enum MenuTypeEnum {
    TOP_LEVEL((Integer)1, "一级菜单"),
    SUB_LEVEL((Integer)2, "子级菜单"),
    NOT_MENU((Integer)3, "不是菜单");

    private Integer code;

    private String message;

    MenuTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
