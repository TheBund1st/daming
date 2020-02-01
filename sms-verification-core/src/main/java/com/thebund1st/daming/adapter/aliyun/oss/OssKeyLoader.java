package com.thebund1st.daming.adapter.aliyun.oss;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObject;
import com.thebund1st.daming.jwt.key.KeyBytesLoader;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.util.StreamUtils;

@RequiredArgsConstructor
public class OssKeyLoader implements KeyBytesLoader {

    private final OSSClient ossClient;
    @Setter
    private String bucketName = "keepitsecret";
    @Setter
    private String objectName = "sms-verification-private.der";

    @Override
    @SneakyThrows
    public byte[] getBytes() {
        OSSObject ossObject = ossClient.getObject(bucketName, objectName);
        return StreamUtils.copyToByteArray(ossObject.getObjectContent());
    }
}
