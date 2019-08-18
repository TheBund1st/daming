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
                mobile: value(consumer(regex('13912222274')), producer('13912222274'))
        ])
    }
    response {
        status 429
        body([
                error  : '1001',
                message: 'Only 1 request is allowed by [DEMO][13912222274] in every 15 seconds'
        ])
    }
}