package contracts.sms.verification.code.send

import org.springframework.cloud.contract.spec.Contract

import static org.springframework.cloud.contract.spec.internal.HttpMethods.HttpMethod.POST

Contract.make {
    description "POST to send sms verification code, but with invalid phone number"
    request {
        urlPath "/api/sms/verification/code"
        method POST
        headers {
            contentType applicationJson()
        }
        body([
                scope : value(consumer(regex('.*')), producer('DEMO')),
                mobile: value(consumer(regex('18500001111')), producer('DD18511112222'))
        ])
    }
    response {
        status 400
        body([
                errors: [
                        [
                                field: 'mobile',
                        ]
                ]
        ])
    }
}