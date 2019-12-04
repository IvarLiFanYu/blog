package com.fly.blog.enums;

import lombok.Getter;

/**
 * @Author 李凡宇
 * 异常枚举类
 */
@Getter
public enum ResultEnums {

    /** 操作成功.*/
    SUCCESS(25000,"成功"),
    BLOG_IS_NULL(25001,"博客不存在"),
    USER_NOT_LOGIN(25002,"用户未登录"),
    BLOG_COMMENT_ERROR(25003,"评论保存失败")
    ;


    private Integer code;
    private String message;

    ResultEnums(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
