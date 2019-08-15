package contracts.sms.verification.code.send

import org.springframework.cloud.contract.spec.Contract

import static org.springframework.cloud.contract.spec.internal.HttpMethods.HttpMethod.POST

Contract.make {
    description "fetch by invalid phone"
    request {
        urlPath "/api/sms/verification/code"
        method POST
        headers {
            contentType applicationJson()
        }
        body([
                scope: value(consumer(regex('.*')), producer('DEMO')),
                mobile: value(consumer(regex('^((?!(18522223333|18511112222)).)+$')), producer('18511112222'))
        ])
    }
    response {
        status 202
        body([
            msg: 'phone number is not exist',
            hasError: true
        ])
    }
}