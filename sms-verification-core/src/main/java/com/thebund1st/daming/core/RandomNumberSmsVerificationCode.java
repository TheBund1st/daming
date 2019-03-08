package com.thebund1st.daming.core;

import lombok.extern.slf4j.Slf4j;

import java.security.SecureRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.thebund1st.daming.core.SmsVerificationCode.smsVerificationCodeOf;

@Slf4j
public class RandomNumberSmsVerificationCode implements SmsVerificationCodePattern, SmsVerificationCodeGenerator {

    // TODO make it configurable
    private final String regex = "^\\d{6}$";

    @Override
    public SmsVerificationCode generate() {
        SecureRandom rNo = new SecureRandom();
        final int code = rNo.nextInt((999999 - 100000) + 1) + 100000;
        return smsVerificationCodeOf(String.valueOf(code));
    }

    @Override
    public boolean matches(SmsVerificationCode value) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(value.getValue());
        return matcher.find();
    }
}
