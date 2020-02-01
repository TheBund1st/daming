package com.thebund1st.daming.web

import com.thebund1st.daming.application.commandhandling.SendSmsVerificationCodeCommandHandler
import com.thebund1st.daming.application.commandhandling.VerifySmsVerificationCodeCommandHandler
import com.thebund1st.daming.application.domain.MobilePhoneNumberPattern
import com.thebund1st.daming.application.domain.RandomNumberSmsVerificationCode
import com.thebund1st.daming.application.domain.SmsVerificationCodePattern
import com.thebund1st.daming.application.domain.SmsVerificationScopePattern
import com.thebund1st.daming.application.domain.StaticSmsVerificationScopePattern
import com.thebund1st.daming.boot.http.EndpointsConfiguration
import com.thebund1st.daming.application.jwt.SmsVerifiedJwtIssuer
import io.restassured.module.mockmvc.RestAssuredMockMvc
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static com.thebund1st.daming.application.domain.SmsVerificationScope.smsVerificationScopeOf

@WebMvcTest
@Import([EndpointsConfiguration, MockMvcBuilderCustomizers])
class AbstractWebMvcTest extends Specification {

    @Autowired
    protected MockMvc mockMvc

    @SpringBean
    protected SendSmsVerificationCodeCommandHandler sendSmsVerificationCodeCommandHandler = Mock()

    @SpringBean
    protected VerifySmsVerificationCodeCommandHandler verifySmsVerificationCodeCommandHandler = Mock()

    @SpringBean
    protected SmsVerifiedJwtIssuer smsVerifiedJwtIssuer = Mock()

    @SpringBean
    protected SmsVerificationScopePattern smsVerificationScopePattern =
            new StaticSmsVerificationScopePattern([
                    smsVerificationScopeOf('DEMO'),
                    smsVerificationScopeOf('SMS_LOGIN')
            ])

    @SpringBean
    protected SmsVerificationCodePattern smsVerificationCodePattern =
            new RandomNumberSmsVerificationCode()

    @SpringBean
    protected MobilePhoneNumberPattern mobilePhoneNumberPattern =
            new MobilePhoneNumberPattern()

    def setup() {
        RestAssuredMockMvc.mockMvc(mockMvc)
    }
}
