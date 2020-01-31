package com.thebund1st.paa.command.interceptor;

import com.thebund1st.daming.application.interceptor.CommandHandler;

public interface CommandHandlerWithReturn {

    @CommandHandler
    Return handle(Command command);

}
