package com.thebund1st.daming.application.domain


import java.time.Duration
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit

import static com.thebund1st.daming.application.domain.SmsVerificationCode.smsVerificationCodeOf
import static com.thebund1st.daming.application.domain.MobilePhoneNumber.mobilePhoneNumberOf
import static com.thebund1st.daming.application.domain.SmsVerificationScope.smsVerificationScopeOf
import static TestingMobile.aMobilePhoneNumber
import static TestingSmsVerificationScope.smsLogin
import static TestingVerificationCode.aVerificationCodeOf
import static java.time.temporal.ChronoUnit.SECONDS

class SmsVerificationFixture {
    private SmsVerification target = new SmsVerification()

    def sendTo(String mobile) {
        this.sendTo(mobilePhoneNumberOf(mobile))
    }

    def sendTo(MobilePhoneNumber mobile) {
        target.setMobile(mobile)
        this
    }

    def codeIs(String code) {
        target.setCode(smsVerificationCodeOf(code))
        this
    }

    def with(SmsVerificationScope scope) {
        target.setScope(scope)
        this
    }

    def withScope(String value) {
        this.with(smsVerificationScopeOf(value))
    }

    def createdAt(LocalDateTime localDateTime) {
        createdAt(localDateTime.atZone(ZoneId.of("Asia/Shanghai")))
    }

    def createdAt(ZonedDateTime now) {
        target.setCreatedAt(now)
        this
    }

    def expiresIn(long i, ChronoUnit unit) {
        target.setExpires(Duration.of(i, unit))
        this
    }

    def build() {
        target
    }

    static def aSmsVerification() {
        new SmsVerificationFixture()
                .sendTo(aMobilePhoneNumber())
                .with(smsLogin())
                .codeIs(aVerificationCodeOf(6))
                .createdAt(ZonedDateTime.now())
                .expiresIn(60, SECONDS)
    }
}
