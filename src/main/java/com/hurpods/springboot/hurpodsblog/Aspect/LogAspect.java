package com.hurpods.springboot.hurpodsblog.Aspect;

import com.hurpods.springboot.hurpodsblog.utils.MyUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;

@Aspect
@Component
public class LogAspect {
    @Pointcut("execution(public * com.hurpods.springboot.hurpodsblog.controller.*.*(..))")
    public void webLog() {
    }


    @Before("webLog()")
    public void doBefore(JoinPoint jp) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();
        long startTime = System.currentTimeMillis();
        String log =
                new Timestamp(new Date().getTime()).toString() + " action start====================================================\n" +
                        "URL: " + request.getRequestURL().toString() + "\n" +
                        "HTTP_METHOD : " + request.getMethod() + "\n" +
                        "IP : " + request.getRemoteAddr() + "\n" +
                        "CLASS_METHOD : " + jp.getSignature().getDeclaringTypeName() + "." + jp.getSignature().getName() + "\n" +
                        "ARGS : " + Arrays.toString(jp.getArgs());
        MyUtil.writeLog(log);
        log =
                new Timestamp(new Date().getTime()).toString() + " action end====================================================" + "\n" +
                        "TIME_COST : " + (System.currentTimeMillis() - startTime);

        MyUtil.writeLog(log);
    }

    @AfterThrowing(value = "webLog()", throwing = "throwable")
    public void doAfterThrowing(Throwable throwable) throws IOException {
        String log = "ERROR : " + throwable.getMessage() + "\n" +
                "TIME : " + new Timestamp(new Date().getTime()).toString();
        MyUtil.writeLog(log);
    }

}
