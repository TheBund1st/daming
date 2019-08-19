package contracts.sms.verification.code.verify

import org.springframework.cloud.contract.spec.Contract

import static org.springframework.cloud.contract.spec.internal.HttpMethods.HttpMethod.DELETE

Contract.make {
    description "DELETE to verify sms verification code, but code mismatch"
    request {
        urlPath "/api/sms/verification/code"
        method DELETE
        headers {
            contentType applicationJson()
        }
        body([
                scope : value(consumer(regex('.*')), producer('DEMO')),
                mobile: value(consumer(regex('18511112222')), producer('13912222273')),
                code  : value(consumer(regex('2333')), producer('434434'))
        ])
    }
    response {
        status 412
        body([
                error  : '1101',
                message: 'The actual code [434434] does not match the code sent to [139****2273][DEMO].'
        ])
    }
}