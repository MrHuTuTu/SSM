package com.hututu.aop;
import org.aspectj.lang.ProceedingJoinPoint;

public class LoggerAspect {
    /*
    该日志切面的功能是 在调用核心功能之前和之后分别打印日志，切面就是原理图中讲的那些辅助功能。
     */
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("start log:" + joinPoint.getSignature().getName());
        /*下句就是将来与某个核心功能编织之后，用于执行核心功能的代码*/
        Object object = joinPoint.proceed();
        System.out.println("end log:" + joinPoint.getSignature().getName());
        return object;
    }
}
