package com.thebund1st.paa.command.interceptor;

import com.thebund1st.daming.application.commandhandling.interceptor.CommandHandler;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DefaultCommandHandlerWithReturn implements CommandHandlerWithReturn {

    private final Dependency dependency;

    @CommandHandler
    @Override
    public Return handle(Command command) {
        return dependency.handle(command);
    }

    public static class Dependency {

        public Return handle(Command command) {
            return new Return();
        }

    }
}
