package com.thebund1st.paa.command.interceptor;


import com.thebund1st.daming.adapter.aspectj.AbstractCommandHandlerInterceptorAspect;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class CommandHandlerWithReturnAspect extends AbstractCommandHandlerInterceptorAspect<Command, Return> {

    @Pointcut("@annotation(com.thebund1st.daming.application.commandhandling.interceptor.CommandHandler) && args(Command))")
    @Override
    protected void commandHandlerMethod() {

    }
}

