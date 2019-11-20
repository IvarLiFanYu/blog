package com.fly.blog.exception;

import com.fly.blog.enums.ResultEnums;

/**
 * @Author 李凡宇
 * 自定义异常抛出类
 */
public class ExceptionCast {

    public static void cast (ResultEnums resultEnums) {
        throw new BlogException(resultEnums);
    }

}
