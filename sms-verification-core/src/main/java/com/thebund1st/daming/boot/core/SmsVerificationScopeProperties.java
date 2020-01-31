package com.thebund1st.daming.boot.core;

import com.thebund1st.daming.application.domain.SmsVerificationScope;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;


@Data
public class SmsVerificationScopeProperties {

    private List<String> valid = new ArrayList<>();

    public List<SmsVerificationScope> validScopes() {
        return valid.stream().map(SmsVerificationScope::smsVerificationScopeOf).collect(toList());
    }


}
