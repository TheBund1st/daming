package com.thebund1st.daming.application.commandhandling.interceptor;

import com.thebund1st.daming.application.command.SendSmsVerificationCodeCommand;
import com.thebund1st.daming.application.domain.SmsVerification;

/**
 * HandlerInterceptor for {@link SendSmsVerificationCodeCommand}.
 *
 * <p>
 * Custom {@link SendSmsVerificationCodeCommandHandlerInterceptor} can be used to implement rate limiting or
 * other alternative path.
 * <p>
 *
 * @since 0.10.0
 */
public interface SendSmsVerificationCodeCommandHandlerInterceptor
        extends CommandHandlerInterceptor<SendSmsVerificationCodeCommand, SmsVerification> {

}
