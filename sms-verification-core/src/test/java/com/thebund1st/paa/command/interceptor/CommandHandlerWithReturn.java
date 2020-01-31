package com.thebund1st.paa.command.interceptor;

import com.thebund1st.daming.application.commandhandling.interceptor.CommandHandler;

public interface CommandHandlerWithReturn {

    @CommandHandler
    Return handle(Command command);

}
