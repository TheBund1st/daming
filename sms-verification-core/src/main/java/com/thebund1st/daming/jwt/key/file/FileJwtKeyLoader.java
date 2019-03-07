package com.thebund1st.daming.jwt.key.file;

import com.thebund1st.daming.jwt.key.JwtKeyLoader;
import lombok.Setter;
import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Key;
import java.security.KeyFactory;
import java.security.spec.PKCS8EncodedKeySpec;

public class FileJwtKeyLoader implements JwtKeyLoader {

    @Setter
    private String privateKeyFileLocation = "./sms-verification-private.der";

    @Override
    @SneakyThrows
    public Key getKey() {

        byte[] keyBytes = Files.readAllBytes(Paths.get(privateKeyFileLocation));

        PKCS8EncodedKeySpec spec =
                new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);
    }
}
