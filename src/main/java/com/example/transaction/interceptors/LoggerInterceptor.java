package com.example.transaction.interceptors;

import org.springframework.lang.Nullable;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Logger;

public class LoggerInterceptor implements HandlerInterceptor {
    Logger logger = Logger.getLogger(LoggerInterceptor.class.getName());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("PRE Handler for " + request.getMethod()
                + " " + ((HandlerMethod) handler).getMethod().getName() + " method");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        logger.info("AFTER Handler for " + request.getMethod()
                + " " + ((HandlerMethod) handler).getMethod().getName() + " method");
    }

}
