package com.thebund1st.daming.jwt.key.file;

import com.thebund1st.daming.jwt.key.KeyBytesLoader;
import lombok.Setter;
import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Paths;

public class FileKeyLoader implements KeyBytesLoader {

    @Setter
    private String privateKeyFileLocation = "./sms-verification-private.der";

    @Override
    @SneakyThrows
    public byte[] getBytes() {
        return Files.readAllBytes(Paths.get(privateKeyFileLocation));
    }
}
