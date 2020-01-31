package com.thebund1st.paa.command.interceptor;


import com.thebund1st.daming.application.commandhandling.interceptor.CommandHandlerInterceptor;

public class CommandHandlerWithReturnInterceptor implements
        CommandHandlerInterceptor<Command, Return> {
    private String flag;

    public CommandHandlerWithReturnInterceptor(String flag) {
        this.flag = flag;
    }


    @Override
    public void preHandle(Command command) {
        command.append(flag);
    }

    @Override
    public void postHandle(Command command, Return returning) {
        returning.append(flag);
    }
}

