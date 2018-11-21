package com.thebund1st.daming.boot;

import com.thebund1st.daming.core.MobilePhoneNumber;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;


@Data
public class SmsVerificationCodeProperties {
    private List<String> whitelist = new ArrayList<>();

    public List<MobilePhoneNumber> whitelist() {
        return getWhitelist().stream().map(MobilePhoneNumber::mobilePhoneNumberOf).collect(toList());
    }
}
