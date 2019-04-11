package com.thebund1st.daming.jwt.key;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.security.Key;
import java.security.KeyFactory;
import java.security.spec.PKCS8EncodedKeySpec;

@RequiredArgsConstructor
public class JwtPrivateKeyLoader implements JwtKeyLoader {

    private final KeyBytesLoader keyBytesLoader;

    @SneakyThrows
    @Override
    public Key getKey() {
        byte[] keyBytes = keyBytesLoader.getBytes();
        PKCS8EncodedKeySpec spec =
                new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);
    }

}
