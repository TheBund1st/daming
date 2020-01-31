package com.thebund1st.paa.command.interceptor;


import com.thebund1st.daming.application.commandhandling.interceptor.AbstractCommandHandlerInterceptorAspect;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class CommandHandlerWithoutReturnAspect extends AbstractCommandHandlerInterceptorAspect {


    @Pointcut("@annotation(com.thebund1st.daming.application.commandhandling.interceptor.CommandHandler) && args(AnotherCommand))")
    @Override
    protected void commandHandlerMethod() {

    }
}

