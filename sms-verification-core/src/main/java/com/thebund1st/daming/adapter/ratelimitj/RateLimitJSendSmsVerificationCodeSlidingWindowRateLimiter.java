package com.thebund1st.daming.adapter.ratelimitj;

import com.thebund1st.daming.application.interceptor.CommandHandlerInterceptor;
import com.thebund1st.daming.commands.SendSmsVerificationCodeCommand;
import com.thebund1st.daming.core.SmsVerification;
import com.thebund1st.daming.security.ratelimiting.Errors;
import com.thebund1st.daming.security.ratelimiting.TooManyRequestsException;
import es.moki.ratelimitj.core.limiter.request.RequestRateLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
//@Order(1001)// after RedisSendSmsVerificationCodeNextWindowRateLimiter
public class RateLimitJSendSmsVerificationCodeSlidingWindowRateLimiter
        implements
//        RateLimitingHandler<SendSmsVerificationCodeCommand>,
        CommandHandlerInterceptor<SendSmsVerificationCodeCommand, SmsVerification> {

    private final RequestRateLimiter requestRateLimiter;

    private String keyPrefix = "sms.verification.rate.limiting.sliding.window";

    //    @Override
    @Deprecated
    public void check(SendSmsVerificationCodeCommand command, Errors errors) {
        //noinspection ConstantConditions
        boolean over = requestRateLimiter.overLimitWhenIncremented(toKey(command));
        if (over) {
            errors.append(blockedMessage(command));
        }
    }

    private String blockedMessage(SendSmsVerificationCodeCommand command) {
        return String.format("Too many requests for [%s][%s]",
                command.getMobile().getValue(),
                command.getScope().getValue()
        );
    }

    private String toKey(SendSmsVerificationCodeCommand command) {
        return String.format("%s.%s.%s", keyPrefix, command.getMobile().getValue(), command.getScope().getValue());
    }

    //    @Override
    @Deprecated
    public void count(SendSmsVerificationCodeCommand command) {
        // don't need this
    }

    @Override
    public void preHandle(SendSmsVerificationCodeCommand command) {
        //noinspection ConstantConditions
        boolean over = requestRateLimiter.overLimitWhenIncremented(toKey(command));
        if (over) {
            throw new TooManyRequestsException(blockedMessage(command));
        }
    }
}
