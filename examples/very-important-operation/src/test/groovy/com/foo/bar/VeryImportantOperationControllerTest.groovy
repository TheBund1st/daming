package com.foo.bar

import com.thebund1st.daming.jwt.SmsVerifiedJwtIssuer
import io.restassured.RestAssured
import io.restassured.builder.RequestSpecBuilder
import io.restassured.filter.log.RequestLoggingFilter
import io.restassured.filter.log.ResponseLoggingFilter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

import static com.thebund1st.daming.core.SmsVerificationFixture.aSmsVerification
import static io.restassured.RestAssured.given
import static org.hamcrest.Matchers.containsString
import static org.hamcrest.Matchers.equalTo
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import static org.springframework.http.HttpStatus.FOUND
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

        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setPort(randomServerPort)
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .build()
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

    def "it should reject very very important operation given not login"() {

        when:
        def then = given()
                .contentType(APPLICATION_JSON_UTF8_VALUE)
                .when()
                .post("/very/very/important/operation")
                .then()

        then:
        then.statusCode(FOUND.value())
                .header("Location", containsString("/login"))
    }

    def "it should reject very very important operation given login but no jwt"() {

        given:
        def cookie = given()
                .param("username", "admin")
                .param("password", "secret")
                .when()
                .post("/login")
                .then()
                .statusCode(FOUND.value())
                .extract().cookie("JSESSIONID")

        when:
        def then = given()
                .cookie("JSESSIONID", cookie)
                .contentType(APPLICATION_JSON_UTF8_VALUE)
                .when()
                .post("/very/very/important/operation")
                .then()

        then:
        then.statusCode(HttpStatus.FORBIDDEN.value())
    }

    def "it should allow very very important operation given login and a good jwt"() {

        given:
        def cookie = loggedIn()

        and:
        def smsVerification = aSmsVerification().build()
        def jwt = smsVerifiedJwtIssuer.issue(smsVerification.mobile, smsVerification.scope)

        when:
        def then = given()
                .cookie("JSESSIONID", cookie)
                .header("X-SMS-VERIFICATION-JWT", jwt)
                .contentType(APPLICATION_JSON_UTF8_VALUE)
                .when()
                .post("/very/very/important/operation")
                .then()

        then:
        then.statusCode(HttpStatus.OK.value())
                .body('mobile', equalTo(smsVerification.mobile.value))
                .body('scope', equalTo(smsVerification.scope.value))
    }

    private String loggedIn() {
        given()
                .param("username", "admin")
                .param("password", "secret")
                .when()
                .post("/login")
                .then()
                .statusCode(FOUND.value())
                .extract().cookie("JSESSIONID")
    }

    def "it should pass unimportant operation even it has same url with very important operation"() {

        when:
        def then = given()
                .contentType(APPLICATION_JSON_UTF8_VALUE)
                .when()
                .get("/very/important/operation")
                .then()

        then:
        then.statusCode(HttpStatus.OK.value())
    }

}
