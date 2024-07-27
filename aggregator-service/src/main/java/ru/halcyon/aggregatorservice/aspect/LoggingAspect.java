package ru.halcyon.aggregatorservice.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
@Slf4j
public class LoggingAspect {
    @Before("execution(* ru.halcyon.aggregatorservice.service.*.*(..))")
    public void logMethodCall(JoinPoint joinPoint) {
        log.info(String.format(
                "Method %s called with arguments %s",
                joinPoint.getSignature().getName(),
                Arrays.toString(joinPoint.getArgs())
        ));
    }

    @AfterReturning(pointcut = "execution(* ru.halcyon.aggregatorservice.*.*(..))", returning = "result")
    public void logMethodReturn(JoinPoint joinPoint, Object result) {
        log.info(String.format(
                "Method %s returned %s",
                joinPoint.getSignature().getName(),
                result
        ));
    }

    @AfterThrowing(pointcut = "execution(* ru.halcyon.aggregatorservice.*.*(..))", throwing = "e")
    public void logMethodException(JoinPoint joinPoint, Exception e) {
        log.info(String.format(
                "Method %s throw exception %s",
                joinPoint.getSignature().getName(),
                e.getMessage()
        ));
    }
}
