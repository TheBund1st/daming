package com.thebund1st.daming.boot.jwt.key.file;

import lombok.Data;
import lombok.Setter;


@Data
public class DeprecatedFileJwtKeyProperties {
    @Setter
    private String privateKeyFileLocation = "./sms-verification-private.der";

}
