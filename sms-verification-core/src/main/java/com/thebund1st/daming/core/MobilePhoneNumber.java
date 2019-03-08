package com.thebund1st.daming.core;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import static lombok.AccessLevel.PRIVATE;

@Getter
@EqualsAndHashCode
@AllArgsConstructor(access = PRIVATE)
public class MobilePhoneNumber {

    private String value;

    @Override
    public String toString() {
        if (this.value == null || this.value.trim().equals("")) {
            return value;
        }
        String result;
        if (value.length() > 8) {
            if (value.contains("-")) {
                String[] temp = value.split("-");
                result = temp[0] + "****" + temp[1].substring(temp[1].length() - 4, temp[1].length());
            } else {
                result = value.substring(0, 3) + "****" + value.substring(value.length() - 4, value.length());
            }
        } else if (value.length() > 4) {
            result = "****" + value.substring(value.length() - 4, value.length());
        } else {
            result = value;
        }
        return result;
    }

    public static MobilePhoneNumber mobilePhoneNumberOf(String value) {
        return new MobilePhoneNumber(value);
    }
}
