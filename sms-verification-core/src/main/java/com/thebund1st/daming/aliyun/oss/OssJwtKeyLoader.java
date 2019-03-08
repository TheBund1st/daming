package com.thebund1st.daming.aliyun.oss;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObject;
import com.thebund1st.daming.jwt.key.JwtKeyLoader;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.util.StreamUtils;

import java.security.Key;
import java.security.KeyFactory;
import java.security.spec.PKCS8EncodedKeySpec;

@RequiredArgsConstructor
public class OssJwtKeyLoader implements JwtKeyLoader {

    private final OSSClient ossClient;
    @Setter
    private String bucketName = "keepitsecret";
    @Setter
    private String objectName = "sms-verification-private.der";

    @Override
    @SneakyThrows
    public Key getKey() {
        OSSObject ossObject = ossClient.getObject(bucketName, objectName);
        byte[] bytes = StreamUtils.copyToByteArray(ossObject.getObjectContent());
        PKCS8EncodedKeySpec spec =
                new PKCS8EncodedKeySpec(bytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);
    }
}
