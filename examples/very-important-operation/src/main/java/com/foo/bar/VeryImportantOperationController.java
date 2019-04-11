package com.foo.bar;

import com.thebund1st.daming.sdk.security.SmsVerificationAuthentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VeryImportantOperationController {


    @PostMapping(path = "/very/important/operation")
    public Object theOperationIsVeryImportant(SmsVerificationAuthentication authentication) {
        return authentication.getPrincipal();
    }
}
