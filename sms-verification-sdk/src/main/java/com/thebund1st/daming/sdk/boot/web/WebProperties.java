package com.thebund1st.daming.sdk.boot.web;

import lombok.Data;


@Data
public class WebProperties {
    private static final String DEFAULT_JWT_HEADER = "X-SMS-VERIFICATION-JWT";
    private String jwtHeaderName = "X-SMS-VERIFICATION-JWT";
}
