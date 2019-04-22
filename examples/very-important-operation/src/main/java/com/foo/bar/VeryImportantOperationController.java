package com.foo.bar;

import com.thebund1st.daming.sdk.jwt.SmsVerificationClaims;
import com.thebund1st.daming.sdk.web.annotation.SmsVerificationRequired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@RestController
public class VeryImportantOperationController {


    @SmsVerificationRequired
    @PostMapping(path = "/very/important/operation")
    public Object theOperationRequiresSmsVerification(SmsVerificationClaims smsVerificationClaims) {
        return smsVerificationClaims;
    }

    @GetMapping(path = "/very/important/operation")
    public Object theOperationDoesNotRequireSmsVerification() {
        return new HashMap<>();
    }

    @SmsVerificationRequired
    @PostMapping(path = "/very/very/important/operation")
    public Object theOperationRequiresBothLoginAndSmsVerification(SmsVerificationClaims smsVerificationClaims) {
        return smsVerificationClaims;
    }
}
