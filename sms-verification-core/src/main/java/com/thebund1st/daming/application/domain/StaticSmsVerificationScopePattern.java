package com.thebund1st.daming.application.domain;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class StaticSmsVerificationScopePattern implements SmsVerificationScopePattern {

    private final List<SmsVerificationScope> valid;

    @Override
    public boolean matches(SmsVerificationScope value) {
        return valid.stream().anyMatch(s -> s.equals(value));
    }
}
