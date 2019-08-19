package contracts.sms.verification.code.send

import org.springframework.cloud.contract.spec.Contract

import static org.springframework.cloud.contract.spec.internal.HttpMethods.HttpMethod.POST

Contract.make {
    description "POST to happy sms verification code"
    request {
        urlPath "/api/sms/verification/code"
        method POST
        headers {
            contentType applicationJson()
        }
        body([
                scope : value(consumer(regex('.*')), producer('DEMO')),
                mobile: value(consumer(regex('18522223333')), producer('13912222273'))
        ])
    }
    response {
        status 202
    }
}