package com.thebund1st.daming.boot.aliyun;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
public class AliyunCredentialsProperties {
    @Getter
    @Setter
    private String accessKeyId;
    @Getter
    @Setter
    private String accessKeySecret;
}
