package com.cc.common.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by CCK on 2018/3/21.
 */
//@Aspect
//@Configuration //注释掉就不在加载
public class ControllerAop {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(* com.cc.controller..*(..)) @annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void controllerMethodPointcut() {
    }


    @Before("controllerMethodPointcut()")
    public void beforeController(JoinPoint joinPoint) {

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        HttpServletResponse response = attributes.getResponse();
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cahce-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("text/json; charset=utf-8");

        logger.info(joinPoint.getStaticPart() + "------start");
    }


    @After("controllerMethodPointcut()")
    public void afterController(JoinPoint joinPoint) {

        logger.info(joinPoint.getStaticPart() + "------end");
    }


}
