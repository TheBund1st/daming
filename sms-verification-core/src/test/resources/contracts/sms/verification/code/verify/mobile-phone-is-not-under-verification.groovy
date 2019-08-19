package contracts.sms.verification.code.verify

import org.springframework.cloud.contract.spec.Contract

import static org.springframework.cloud.contract.spec.internal.HttpMethods.HttpMethod.DELETE

Contract.make {
    description "DELETE to verify sms verification code, but mobile phone is not under verification"
    request {
        urlPath "/api/sms/verification/code"
        method DELETE
        headers {
            contentType applicationJson()
        }
        body([

                scope : value(consumer(regex('.*')), producer('DEMO')),
                mobile: value(consumer(regex('18500001111')), producer('13912222274')),
                code  : value(consumer(regex('2333')), producer('123456'))
        ])
    }
    response {
        status 412
        body([
                error  : '1102',
                message: 'The mobile [139****2274] is not under [DEMO] verification.'
        ])
    }
}