package com.fly.blog.resolver;

import com.fly.blog.exception.BlogException;
import com.fly.blog.response.ResponseResult;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author 李凡宇
 * 自定义异常处理类
 */
@Slf4j
@Component
public class BlogExceptionResolver implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest,
                                         HttpServletResponse httpServletResponse, Object handler, Exception e) {

        ResponseResult responseResult = new ResponseResult();

        //判断是否为自己发出的请求
        if (e instanceof BlogException) {
            Integer code = ((BlogException) e).getCode();
            String message = e.getMessage();
            responseResult.setCode(code);
            responseResult.setMessage(message);
            log.error("自定义异常错误: code={},message={}",((BlogException) e).getCode(),e.getMessage());
        } else {
            responseResult.setCode(9999);
            responseResult.setMessage(e.getMessage());
            log.error("非自定义异常错误: message={}",e.getMessage());
        }
        responseResult.setT(null);

        //判断是否是 json 请求或者 页面转发请求
        HandlerMethod handlerMethod = (HandlerMethod)handler;
        ResponseBody annotation = handlerMethod.getMethod().getAnnotation(ResponseBody.class);
        //如果返回的是 json 数据
        if (annotation != null) {
            Gson gson = new Gson();
            String result = gson.toJson(responseResult);
            httpServletResponse.setCharacterEncoding("utf-8");
            httpServletResponse.setContentType("application/json;charset=utf-8");
            try {
                httpServletResponse.getWriter().write(result);
                httpServletResponse.getWriter().flush();
            } catch (IOException ex) {
                log.error("json返回错误: message={}",e.getMessage());
                ex.printStackTrace();
            }
            //返回一个空的 ModelAndView 表示已经手动处理
            return new ModelAndView();
        }

        //若为返回页面的请求, 则构造 ModelAndView 并返回
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("responseResult",responseResult);
        modelAndView.setViewName("/error/error");

        return modelAndView;
    }
}
