package com.thebund1st.paa.command.interceptor;

import com.thebund1st.daming.application.commandhandling.interceptor.CommandHandler;

public class CommandHandlerWithoutReturn {

    @CommandHandler
    public void handle(AnotherCommand command) {

    }


}
