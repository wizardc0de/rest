package cn.jason.interceptor;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class CtrlInterceptor {

    private Logger logger = LoggerFactory.getLogger(CtrlInterceptor.class);

    @Pointcut("execution(* cn.jason.controller.*.*(..))")
    public void cutController() {
    }

    @Around(value = "cutController()")
    public Object record(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("执行controller开始");
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = null;
        if (!(signature instanceof MethodSignature)) {
            throw new IllegalArgumentException("该注解只能用于方法");
        }
        methodSignature = (MethodSignature) signature;
        Object o = joinPoint.getTarget();
        String className = o.getClass().getName();
        Method me = o.getClass().getMethod(methodSignature.getName(), methodSignature.getParameterTypes());
        Object[] args = joinPoint.getArgs();
        StringBuilder sb = new StringBuilder();
        for (Object arg : args) {
            sb.append(arg + "&");
        }
        logger.info("执行了" + className + "中的" + me.getName() + "参数为" + sb);
        return joinPoint.proceed();
    }
}
