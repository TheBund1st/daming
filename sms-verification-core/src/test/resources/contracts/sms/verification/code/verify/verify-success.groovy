package contracts.sms.verification.code.verify

import org.springframework.cloud.contract.spec.Contract

import static org.springframework.cloud.contract.spec.internal.HttpMethods.HttpMethod.POST

Contract.make {
    ignored()
    description "verify success"
    request {
        urlPath "/api/sms/verification/code/verify"
        method POST
        headers {
            contentType applicationJson()
        }
        body([
                scope : value(consumer(regex('.*')), producer('DEMO')),
                mobile: value(consumer(regex('18522223333')), producer('13912222274')),
                code  : value(consumer(regex('2333')), producer('13912222274'))
        ])
    }
    response {
        status 202
        body([
                token: 'success token from server'
        ])
    }
}