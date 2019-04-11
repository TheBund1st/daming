package com.thebund1st.daming.jwt.key;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.security.Key;
import java.security.KeyFactory;
import java.security.spec.X509EncodedKeySpec;

@RequiredArgsConstructor
public class JwtPublicKeyLoader implements JwtKeyLoader {

    private final KeyBytesLoader keyBytesLoader;

    @SneakyThrows
    @Override
    public Key getKey() {
        byte[] keyBytes = keyBytesLoader.getBytes();
        X509EncodedKeySpec spec =
                new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(spec);
    }

}
