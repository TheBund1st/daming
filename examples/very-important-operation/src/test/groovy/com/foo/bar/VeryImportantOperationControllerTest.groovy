package com.foo.bar

import com.thebund1st.daming.jwt.SmsVerifiedJwtIssuer
import io.restassured.RestAssured
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

import static com.thebund1st.daming.core.SmsVerificationFixture.aSmsVerification
import static io.restassured.RestAssured.given
import static org.hamcrest.Matchers.equalTo
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("test")
class VeryImportantOperationControllerTest extends Specification {

    @LocalServerPort
    int randomServerPort

    @Autowired
    private SmsVerificationCodeSenderStub senderStub

    @Autowired
    private SmsVerifiedJwtIssuer smsVerifiedJwtIssuer

    def setup() {
        RestAssured.port = randomServerPort
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails()
    }

    def "it should allow very important operation"() {

        given:
        def smsVerification = aSmsVerification().build()
        def jwt = smsVerifiedJwtIssuer.issue(smsVerification.mobile, smsVerification.scope)

        when:
        def then = given()
                .header("X-SMS-VERIFICATION-JWT", jwt)
                .contentType(APPLICATION_JSON_UTF8_VALUE)
                .when()
                .post("/very/important/operation")
                .then()

        then:
        then
                .statusCode(HttpStatus.OK.value())
                .body('mobile', equalTo(smsVerification.mobile.value))
                .body('scope', equalTo(smsVerification.scope.value))

    }

    def "it should reject very important operation given no jwt"() {

        when:
        def then = given()
                .contentType(APPLICATION_JSON_UTF8_VALUE)
                .when()
                .post("/very/important/operation")
                .then()

        then:
        then.statusCode(HttpStatus.UNAUTHORIZED.value())
    }

    def "it should reject very important operation given bad jwt"() {

        given:
        def jwt = "a.bad.jwt"

        when:
        def then = given()
                .header("X-SMS-VERIFICATION-JWT", jwt)
                .contentType(APPLICATION_JSON_UTF8_VALUE)
                .when()
                .post("/very/important/operation")
                .then()

        then:
        then.statusCode(HttpStatus.UNAUTHORIZED.value())
    }

}
