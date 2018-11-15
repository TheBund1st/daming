package com.thebund1st.daming.sms.aliyun;

import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thebund1st.daming.application.SmsVerificationSender;
import com.thebund1st.daming.core.SmsVerification;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component(value = "smsVerificationSender")
@ConfigurationProperties(prefix = "daming.aliyun.sms")
public class AliyunSmsVerificationSender implements SmsVerificationSender {

    private final IAcsClient acsClient;

    @Setter
    private String signature;
    @Setter
    private String templateCode;
    @Setter
    private ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    @Override
    public void send(SmsVerification verification) {
        SendSmsRequest request = new SendSmsRequest();
        request.setPhoneNumbers(verification.getMobile().getValue());
        request.setSignName(signature);
        request.setTemplateCode(templateCode);
        Map<String, String> params = new HashMap<String, String>() {
            {
                put("code", verification.getCode().getValue());
            }
        };

        request.setTemplateParam(toJson(params));

        // This data will be sent back in the confirmation. Maybe we can use it for correlation.
        // request.setOutId("");
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
        log.info(toJson(sendSmsResponse));
    }

    private String toJson(Object params) throws JsonProcessingException {
        return objectMapper.writeValueAsString(params);
    }
}
