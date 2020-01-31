package com.foo.smslogin

import io.restassured.RestAssured
import io.restassured.builder.RequestSpecBuilder
import io.restassured.filter.log.RequestLoggingFilter
import io.restassured.filter.log.ResponseLoggingFilter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.ActiveProfiles
import redis.embedded.RedisServer
import spock.lang.Specification

import static com.thebund1st.daming.application.domain.MobilePhoneNumber.mobilePhoneNumberOf
import static com.thebund1st.daming.application.domain.SmsVerificationScope.smsVerificationScopeOf
import static io.restassured.RestAssured.given
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import static org.springframework.http.HttpStatus.*
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("test")
class SmsLoginAcceptanceTest extends Specification {

    @LocalServerPort
    int randomServerPort

    private RedisServer redisServer

    @Autowired
    private SmsVerificationCodeSenderStub smsVerificationCodeSenderStub


    def setup() {
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setPort(randomServerPort)
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .build()
    }

    def "it should allow login via sms"() {

        given:
        given()
                .contentType(APPLICATION_JSON_UTF8_VALUE)
                .body("""
                    {
                        "mobile": "13917777777",
                        "scope": "SMS_LOGIN"
                    }
                """)
                .when()
                .post("/api/sms/verification/code")
                .then().statusCode(ACCEPTED.value())
        def codeMaybe = smsVerificationCodeSenderStub.getTheOnly(mobilePhoneNumberOf("13917777777"),
                smsVerificationScopeOf("SMS_LOGIN"))
        and:
        def jwt = given()
                .contentType(APPLICATION_JSON_UTF8_VALUE)
                .body("""
                    {
                        "mobile": "13917777777",
                        "scope": "SMS_LOGIN",
                        "code": "${codeMaybe.get().code.value}"
                    }
                """)
                .when()
                .delete("/api/sms/verification/code")
                .then().statusCode(OK.value())
                .extract().body()
                .jsonPath().get("token")

        when:
        def cookie = given()
                .contentType(APPLICATION_JSON_UTF8_VALUE)
                .header("X-SMS-VERIFICATION-JWT", jwt)
                .when()
                .post("/login/sms")
                .then().statusCode(OK.value())
                .extract().cookie("JSESSIONID")

        then:
        given()
                .contentType(APPLICATION_JSON_UTF8_VALUE)
                .cookie("JSESSIONID", cookie)
                .when()
                .get("/me")
                .then().statusCode(OK.value())
    }

    def "it should block /me given no login"() {

        given:

        when:
        def assertions = given()
                .contentType(APPLICATION_JSON_UTF8_VALUE)
                .when()
                .get("/me")
                .then()

        then:
        assertions
                .statusCode(UNAUTHORIZED.value())

    }
}
