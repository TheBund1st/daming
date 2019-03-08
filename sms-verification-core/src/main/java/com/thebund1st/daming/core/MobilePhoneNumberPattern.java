package com.thebund1st.daming.core;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MobilePhoneNumberPattern {

    // TODO make it configurable
    // The pattern isn't too strict to avoid more pattern is provided by telecom providers over time.
    private final String regex = "^(13|14|15|16|17|18|19)\\d{9}$";

    public boolean matches(MobilePhoneNumber value) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(value.getValue());
        return matcher.find();
    }
}
