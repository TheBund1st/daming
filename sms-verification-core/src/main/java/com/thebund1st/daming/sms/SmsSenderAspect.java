package com.thebund1st.daming.sms;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Aspect
@Component
public class SmsSenderAspect implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Around("@annotation(smsSender)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint, SmsSender smsSender) throws Throwable {
        Object returning = joinPoint.proceed();
        delegateTo(smsSender, returning);
        return returning;
    }

    private void delegateTo(SmsSender smsSender, Object returning)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Object delegate = applicationContext.getBean(smsSender.delegateTo());
        Method method = delegate.getClass().getDeclaredMethod("send", returning.getClass());
        method.invoke(delegate, returning);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
