package com.thebund1st.daming.application.domain

import com.thebund1st.daming.application.domain.MobilePhoneNumberPattern
import spock.lang.Specification
import spock.lang.Unroll

import static com.thebund1st.daming.application.domain.MobilePhoneNumber.mobilePhoneNumberOf

class MobilePhoneNumberPatternTest extends Specification {

    private MobilePhoneNumberPattern subject = new MobilePhoneNumberPattern()

    @Unroll(value = "test if #mobile is a valid mobile")
    def "test mobile phone number"(String mobile, boolean expected) {

        expect:
        assert subject.matches(mobilePhoneNumberOf(mobile)) == expected

        where:
        mobile        | expected
        '13917777766' | true
        '1391'        | false
        '12917777766' | false
        '11917777766' | false
        '10917777766' | false
        '1091777776X' | false
        'a'           | false
    }
}
