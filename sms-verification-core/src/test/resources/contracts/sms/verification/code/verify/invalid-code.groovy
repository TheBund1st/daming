package contracts.sms.verification.code.verify

import org.springframework.cloud.contract.spec.Contract

import static org.springframework.cloud.contract.spec.internal.HttpMethods.HttpMethod.DELETE

Contract.make {
    description "DELETE to verify sms verification code, but invalid code"
    request {
        urlPath "/api/sms/verification/code"
        method DELETE
        headers {
            contentType applicationJson()
        }
        body([
                scope : value(consumer(regex('.*')), producer('DEMO')),
                mobile: value(consumer(regex('18522223333')), producer('13912222273')),
                code  : value(consumer(regex('^((?!2333).)+$')), producer('11'))
        ])
    }
    response {
        status 400
        body([
                errors: [
                        [
                                field: 'code',
                        ]
                ]
        ])
    }
}