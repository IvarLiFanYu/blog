package com.fly.blog.exception;

import com.fly.blog.enums.ResultEnums;
import lombok.Getter;

/**
 * @Author 李凡宇
 * 自定义博客异常类
 */
@Getter
public class BlogException extends RuntimeException {

    private String message;
    private Integer code;

    public BlogException(ResultEnums resultEnums) {
        this.message = resultEnums.getMessage();
        this.code = resultEnums.getCode();
    }

}
