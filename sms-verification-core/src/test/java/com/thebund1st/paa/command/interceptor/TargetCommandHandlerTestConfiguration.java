package com.thebund1st.paa.command.interceptor;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TargetCommandHandlerTestConfiguration {

    @Bean
    public CommandHandlerWithReturnAspect firstAspect() {
        CommandHandlerWithReturnInterceptor interceptor = new CommandHandlerWithReturnInterceptor("1");
        CommandHandlerWithReturnAspect aspect = new CommandHandlerWithReturnAspect();
        aspect.setCommandHandlerInterceptor(interceptor);
        aspect.setOrder(0);
        return aspect;
    }

    @Bean
    public CommandHandlerWithReturnAspect secondAspect() {
        CommandHandlerWithReturnInterceptor interceptor = new CommandHandlerWithReturnInterceptor("2");
        CommandHandlerWithReturnAspect aspect = new CommandHandlerWithReturnAspect();
        aspect.setCommandHandlerInterceptor(interceptor);
        aspect.setOrder(1);
        return aspect;
    }

    @Bean
    public CommandHandlerWithoutReturnAspect firstAspectForCommandHandlerWithoutReturn() {
        CommandHandlerWithoutReturnInterceptor interceptor = new CommandHandlerWithoutReturnInterceptor("1");
        CommandHandlerWithoutReturnAspect aspect = new CommandHandlerWithoutReturnAspect();
        aspect.setCommandHandlerInterceptor(interceptor);
        return aspect;
    }

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Bean
    public CommandHandlerWithReturn targetCommandHandler(DefaultCommandHandlerWithReturn.Dependency dependency) {
        return new DefaultCommandHandlerWithReturn(dependency);
    }

    @Bean
    public CommandHandlerWithoutReturn commandHandlerWithoutReturn() {
        return new CommandHandlerWithoutReturn();
    }
}
