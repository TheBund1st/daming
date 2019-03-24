package com.foo.bar.steps

import com.foo.bar.SmsVerificationCodeSenderStub
import com.thebund1st.daming.commands.SendSmsVerificationCodeCommand
import com.thebund1st.daming.core.SmsVerificationCode
import io.restassured.response.ValidatableResponse
import org.springframework.http.HttpStatus

import static io.restassured.RestAssured.given
import static org.hamcrest.Matchers.notNullValue
import static org.springframework.http.HttpStatus.ACCEPTED
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.TOO_MANY_REQUESTS
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE

class SmsVerificationStep {

    private final SmsVerificationCodeSenderStub senderStub
    private ValidatableResponse response
    private SendSmsVerificationCodeCommand sendSmsVerificationCodeCommand
    private SmsVerificationCode code

    SmsVerificationStep(SmsVerificationCodeSenderStub senderStub) {
        this.senderStub = senderStub
    }

    def askFor(SendSmsVerificationCodeCommand command) {
        this.sendSmsVerificationCodeCommand = command
        def response = given()
                .contentType(APPLICATION_JSON_UTF8_VALUE)
                .body("""
                            {
                                "mobile": "${command.getMobile().getValue()}",
                                "scope": "${command.getScope().getValue()}"
                            }
                        """)
                .when().post("/api/sms/verification/code")
                .then()
        this.response = response
        this
    }

    def shouldReceiveVerificationCodeOnMyPhone() {
        this.response
                .statusCode(ACCEPTED.value())
        def count = senderStub.sendCount(sendSmsVerificationCodeCommand.getMobile(),
                sendSmsVerificationCodeCommand.scope)
        assert count == 1
        senderStub.getTheOnly(sendSmsVerificationCodeCommand.getMobile(),
                sendSmsVerificationCodeCommand.scope).get().getCode()
    }

    def verify(command) {
        this.response = given()
                .contentType(APPLICATION_JSON_UTF8_VALUE)
                .body("""
                            {
                                "mobile": "${command.getMobile().getValue()}",
                                "scope": "${command.getScope().getValue()}",
                                "code": "${command.getCode().getValue()}"
                            }
                        """)

                .when().delete("/api/sms/verification/code")
                .then()
        this
    }

    def shouldPassTheVerification() {
        this.response
                .statusCode(OK.value())
                .body("token", notNullValue())
    }

    def then(String description) {
        this
    }

    def theCodeReceived() {
        code
    }

    def shouldSeeFailure(HttpStatus httpStatus) {
        this.response.statusCode(httpStatus.value())
    }
}
