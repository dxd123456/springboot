package com.zking.demo.common;

import lombok.Getter;

/**
 * 数据库字段查询
 * @author
 */
@Getter
public enum StatusEnum {
    OK(StatusConst.OK, "启用"),
    FREEZED(StatusConst.FREEZED, "冻结"),
    DELETE(StatusConst.DELETE, "删除");
    private Integer code;
    private String message;

    StatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
