package com.thebund1st.daming.boot;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
public class SmsVerificationCodeProperties {
    private List<String> whitelist = new ArrayList<>();

    public List<String> whitelist() {
        return getWhitelist();
    }
}
