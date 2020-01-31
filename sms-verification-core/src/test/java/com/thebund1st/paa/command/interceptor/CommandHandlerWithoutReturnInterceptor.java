package com.thebund1st.paa.command.interceptor;


import com.thebund1st.daming.application.interceptor.CommandHandlerInterceptor;

public class CommandHandlerWithoutReturnInterceptor implements
        CommandHandlerInterceptor<AnotherCommand, Void> {
    private String flag;

    public CommandHandlerWithoutReturnInterceptor(String flag) {
        this.flag = flag;
    }


    @Override
    public void preHandle(AnotherCommand command) {
        command.append(flag);
    }

}

