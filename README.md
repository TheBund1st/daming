# daming
[![Build Status](https://travis-ci.org/TheBund1st/daming.svg?branch=master)](https://travis-ci.org/TheBund1st/daming)
[![Maintainability](https://api.codeclimate.com/v1/badges/417d2e7b5ac9e45eb803/maintainability)](https://codeclimate.com/github/TheBund1st/daming/maintainability)
[![Test Coverage](https://api.codeclimate.com/v1/badges/417d2e7b5ac9e45eb803/test_coverage)](https://codeclimate.com/github/TheBund1st/daming/test_coverage)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.hippoom/sms-verification-starter/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.hippoom/sms-verification-starter)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)    
Sms verification component for spring boot project

## Quick Start

Add this starter to your project

With gradle:
```groovy
compile group: "com.github.hippoom:sms-verification-starter:${latestVersion}"

# RedisSmsVerificationStore depends on this
compile "org.springframework.data:spring-data-redis:2.1.2.RELEASE"
```

You'll need to setup private key location so that the application can generate a JWT once the mobile number is verified.

```properties
# application-{profile}.properties
daming.jwt.privateKeyFileLocation=/home/your-app/sms-verification-private.der
```
Start your app, and run

```jshelllanguage
>curl -H 'Content-Type: application/json' -XPOST ${host}:${port}/api/sms/verification/code -d '{"mobile": "${your mobile}"}'
```

You'll see the following log entry
```jshelllanguage
Sending verification code ${a code} to mobile {your masked mobile}, the code is expiring in PT1M
```

Copy the code and run this since it does not send you a message by default 

```jshelllanguage
>curl -H 'Content-Type: application/json' -XDELETE ${host}:${port}/api/sms/verification/code -d '{"mobile": "${your mobile}","code":"${the code}"}'
{"token":"{a very long string}"}%
```

## What I can do with the JWT
The JWT contains the mobile phone number that has been verified. The client can sent it to next endpoint such as register or login.
The endpoint should verify the JWT with the public key paired to the private key and extract the mobile:

```java
Jwts.parser().setSigningKey(getPublicKey("./sms-verification-public.der"))
                .parseClaimsJws(actual).getBody().get("mobile")
                
private PublicKey getPublicKey(String filename) throws Exception {

        byte[] keyBytes = Files.readAllBytes(Paths.get(filename))

        X509EncodedKeySpec spec =
                new X509EncodedKeySpec(keyBytes)
        KeyFactory kf = KeyFactory.getInstance("RSA")
        return kf.generatePublic(spec)
    }                
```

 

## Using Aliyun Sms

The starter supports aliyun sms out of box, you can enable it by 

```properties
# application-{profile}.properties
daming.sms.provider=aliyun
```

And access key and secret if you don't have a configured IAcsClient instance

```properties
daming.aliyun.accessKeyId={your key id}
daming.aliyun.accessKeySecret={your key secret}
```

Do remember to include aliyun sdk in your dependencies

```groovy
    compile("com.aliyun:aliyun-java-sdk-core:4.0.6")
    compile("com.aliyun:aliyun-java-sdk-dysmsapi:1.1.0")
```

or with a configured IAcsClient instance named `acsClient`

```java
@Bean(name="acsClient")
public IAcsClient acsClient() {
    IClientProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
    DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", "Dysmsapi", "dysmsapi.aliyuncs.com");
    // other configurations
    return new DefaultAcsClient(profile);
}

```

The last step is to configure sms signature and template code, you can find them on Aliyun Sms's console

```properties
daming.aliyun.sms.signature={your text} #encode it in unicode
daming.aliyun.sms.templateCode={your code}
```





## Why

TBD

## Contributing

Any suggestion and pull request is welcome.

## License

Licensed under Apache-2.0 License (the "License"); You may obtain a copy of the License in the LICENSE file, or at [here](https://github.com/TheBund1st/daming/blob/master/LICENSE).