package com.thebund1st.paa.command.interceptor;


import com.thebund1st.daming.application.interceptor.AbstractCommandHandlerInterceptorAspect;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class CommandHandlerWithReturnAspect extends AbstractCommandHandlerInterceptorAspect<Command, Return> {

    @Pointcut("@annotation(com.thebund1st.daming.application.interceptor.CommandHandler) && args(Command))")
    @Override
    protected void commandHandlerMethod() {

    }
}

