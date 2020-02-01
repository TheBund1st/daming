package com.thebund1st.daming.json

import com.thebund1st.daming.boot.adapter.redis.redis.RedisConfiguration
import org.springframework.boot.test.autoconfigure.json.JsonTest
import org.springframework.boot.test.json.JacksonTester
import spock.lang.Specification

@JsonTest
abstract class AbstractJsonTest extends Specification {


    def setup() {
        JacksonTester.initFields(this, RedisConfiguration.buildMapper())
    }


}
