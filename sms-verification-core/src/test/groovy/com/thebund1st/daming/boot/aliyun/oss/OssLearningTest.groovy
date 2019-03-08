package com.thebund1st.daming.boot.aliyun.oss

import com.aliyun.oss.OSSClient
import com.aliyun.oss.model.OSSObject
import spock.lang.Specification

import static java.lang.System.getenv

class OssLearningTest extends Specification {

    def "it should download key from OSS private bucket"() {
        given:
        String endpoint = "https://oss-cn-shenzhen.aliyuncs.com"
        String accessKeyId = getenv("ALIYUN_ACCESS_KEY_ID")
        String accessKeySecret = getenv("ALIYUN_ACCESS_KEY_SECRET")
        String bucketName = "keepsecret"
        String objectName = "sms-verification-private.der"


        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret)

        when:
        OSSObject ossObject = ossClient.getObject(bucketName, objectName)
        BufferedReader reader = new BufferedReader(new InputStreamReader(ossObject.getObjectContent()))
        String body = null
        while (true) {
            String line = reader.readLine()
            if (line == null) break
            body = body + "\n" + line
            System.out.println("\n" + line)
        }
        reader.close()

        ossClient.shutdown()

        then:
        println(body)
    }
}
