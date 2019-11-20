package com.fly.blog.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @Author 李凡宇
 * 利用 SpringAOP 进行日志记录
 */
@Aspect
@Component
@Slf4j
public class LogAspect {

    /**
     * 配置切面
     */
    @Pointcut("execution(* com.fly.blog.controller.*.*(..))")
    public void log() {

    }

    /**
     * 记录 url, ip, 请求的方法, 参数
     * @param joinPoint 从切面接收的joinPoint, 可用于获取请求的方法和参数
     */
    @Before("log()")
    public void before(JoinPoint joinPoint) {
        //获取 HttpServletRequest
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        //获取 url, ip
        String url = request.getRequestURL().toString();
        String ip = request.getRemoteAddr();
        //获取请求的方法和参数
        String method = joinPoint.getSignature().getDeclaringType() +"."+ joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        String requestLog = new RequestLog(url, ip, method, args).toString();
        log.info("request:{}",requestLog);
    }


    @After("log()")
    public void after() {

    }

    /**
     * 记录返回的结果
     * @param result 结果
     */
    @AfterReturning(returning = "result", pointcut = "log()")
    public void afterReturn(Object result) {
        log.info("result:{}",result);
    }

    /**
     * 日志记录类
     */
    private class RequestLog {
        private String url;
        private String ip;
        private String method;
        private Object[] args;
        public RequestLog(String url, String ip, String method, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.method = method;
            this.args = args;
        }
        @Override
        public String toString() {
            return "{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", method='" + method + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }

}
