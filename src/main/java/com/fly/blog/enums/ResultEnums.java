package com.fly.blog.enums;

import lombok.Getter;

/**
 * @Author 李凡宇
 * 异常枚举类
 */
@Getter
public enum ResultEnums {

    /** 操作成功.*/
    SUCCESS(25000,"成功")
    ;


    private Integer code;
    private String message;

    ResultEnums(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
