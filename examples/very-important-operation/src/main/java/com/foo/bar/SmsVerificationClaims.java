package com.foo.bar;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SmsVerificationClaims {
    private String mobile;
    private String scope;

    public SmsVerificationClaims(String mobile, String scope) {
        this.mobile = mobile;
        this.scope = scope;
    }
}
