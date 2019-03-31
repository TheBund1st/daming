package com.thebund1st.daming.boot.http;

import lombok.Data;


@Data
public class EndpointProperties {

    private String sendSmsVerificationCodePath = "/api/sms/verification/code";

    private String verifySmsVerificationCodePath = "/api/sms/verification/code";


}
