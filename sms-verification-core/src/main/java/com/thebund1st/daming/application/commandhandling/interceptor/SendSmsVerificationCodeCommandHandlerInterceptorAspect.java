package com.thebund1st.daming.application.commandhandling.interceptor;

import com.thebund1st.daming.commands.SendSmsVerificationCodeCommand;
import com.thebund1st.daming.core.SmsVerification;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * Aspect to weave {@link SendSmsVerificationCodeCommandHandlerInterceptor}.
 *
 * @since 0.10.0
 */
@Aspect
public class SendSmsVerificationCodeCommandHandlerInterceptorAspect extends
        AbstractCommandHandlerInterceptorAspect<SendSmsVerificationCodeCommand, SmsVerification> {

    @Pointcut("@annotation(com.thebund1st.daming.application.commandhandling.interceptor.CommandHandler) " +
            "&& args(com.thebund1st.daming.commands.SendSmsVerificationCodeCommand))")
    @Override
    protected void commandHandlerMethod() {

    }
}
