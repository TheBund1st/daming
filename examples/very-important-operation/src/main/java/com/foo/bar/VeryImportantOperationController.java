package com.foo.bar;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class VeryImportantOperationController {


    @PostMapping(path = "/very/important/operation")
    public Object theOperationRequiresSmsVerification(HttpServletRequest request) {
        return request.getAttribute("smsVerificationClaims");
    }

    @PostMapping(path = "/very/very/important/operation")
    public Object theOperationRequiresBothLoginAndSmsVerification(HttpServletRequest request) {
        return request.getAttribute("smsVerificationClaims");
    }
}
