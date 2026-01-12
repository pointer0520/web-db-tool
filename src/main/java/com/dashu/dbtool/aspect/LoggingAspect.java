package com.dashu.dbtool.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LoggingAspect {
    /**
     * 定义切点：拦截 Controller 层的所有方法
     */
    @Pointcut("execution(* com.dashu.dbtool.controller..*.*(..))")
    public void controllerPoint() {}

    @Around("controllerPoint()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        // 获取类名和方法名
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();

        // 记录方法入参
        Object[] args = joinPoint.getArgs();
        if (args != null && args.length > 0) {
            log.info("{}.{}() - 参数：{}", className,methodName, args);
        } else {
            log.info("{}.{}() - 无参数", className, methodName);
        }
        Object result = null;
        try {
            result = joinPoint.proceed();

            long executionTime = System.currentTimeMillis() - startTime;
            log.info("{}.{}() - 执行时间：{}ms", className, methodName, executionTime);
            return result;
        } catch (Exception e) {
            long executionTime = System.currentTimeMillis() - startTime;
            log.error("{}.{}() - 执行时间：{}ms，异常：{}",
                    className, methodName, executionTime, e.getMessage(), e);
            throw e;
        }
    }
}
