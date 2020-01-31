package com.thebund1st.daming.security.ratelimiting;

import com.thebund1st.daming.application.command.SendSmsVerificationCodeCommand;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@Deprecated
@Aspect
public class RateLimitedAspect {

    @Autowired(required=false)
    private List<RateLimitingHandler<SendSmsVerificationCodeCommand>> sendSmsVerificationCodeRateLimitingHandlers
            = new ArrayList<>();

    @Autowired
    private ErrorsFactory errorsFactory;

    @Around("@annotation(rateLimited)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint, RateLimited rateLimited) throws Throwable {
        SendSmsVerificationCodeCommand command = extractCommandFrom(joinPoint);
        checkAgainst(command);
        Object returning = joinPoint.proceed();
        count(command);
        return returning;
    }

    private void count(SendSmsVerificationCodeCommand command) {
        sendSmsVerificationCodeRateLimitingHandlers.forEach(h -> h.count(command));
    }

    private void checkAgainst(SendSmsVerificationCodeCommand command) {
        sendSmsVerificationCodeRateLimitingHandlers.forEach(h -> {
            Errors errors = errorsFactory.empty();
            h.check(command, errors);
            if (!errors.isEmpty()) {
                throw new TooManyRequestsException(errors);
            }
        });
    }

    private SendSmsVerificationCodeCommand extractCommandFrom(ProceedingJoinPoint joinPoint) {
        Stream<Object> args = Arrays.stream(joinPoint.getArgs());
        //noinspection OptionalGetWithoutIsPresent
        return args
                .filter(a -> a instanceof SendSmsVerificationCodeCommand)
                .map(a -> (SendSmsVerificationCodeCommand) a)
                .findFirst().get();
    }


}
