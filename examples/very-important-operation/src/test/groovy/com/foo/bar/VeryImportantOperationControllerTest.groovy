package com.foo.bar

import io.restassured.RestAssured
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

import static io.restassured.RestAssured.given
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("test")
class VeryImportantOperationControllerTest extends Specification {

    @LocalServerPort
    int randomServerPort

    @Autowired
    private SmsVerificationCodeSenderStub senderStub

    def setup() {
        RestAssured.port = randomServerPort
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails()
    }

    def "it should allow very important operation"() {

        when:
        def then = given()
                .contentType(APPLICATION_JSON_UTF8_VALUE)
                .when()
                .post("/very/important/operation")
                .then()

        then:
        then.statusCode(HttpStatus.OK.value())
    }

}
