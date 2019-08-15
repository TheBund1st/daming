package contracts.sms.verification.code.send

import org.springframework.cloud.contract.spec.Contract

import static org.springframework.cloud.contract.spec.internal.HttpMethods.HttpMethod.POST

Contract.make {
    description "verify failed by code"
    request {
        urlPath "/api/sms/verification/code/verify"
        method POST
        headers {
            contentType applicationJson()
        }
        body([
                scope: value(consumer(regex('.*')), producer('DEMO')),
                mobile: value(consumer(regex('18522223333')), producer('13912222274')),
                code: value(consumer(regex('^((?!2333).)+$')), producer('13912222274'))
        ])
    }
    response {
        status 202
        body([
            msg: 'error in code',
            hasError: true
        ])
    }
}