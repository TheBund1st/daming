package com.thebund1st.daming.core

import spock.lang.Specification

import static com.thebund1st.daming.core.MobilePhoneNumber.mobilePhoneNumberOf

class MobilePhoneNumberTest extends Specification {

    def "it should mask the mobile phone number for security"(String mobile, String masked) {
        expect:
        assert mobilePhoneNumberOf(mobile).toString() == masked

        where:
        mobile        | masked
        '13411111234' | '134****1234'
    }
}
