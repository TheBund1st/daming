package com.thebund1st.daming.sdk.jjwt;

import io.jsonwebtoken.Clock;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

public class ZonedClock implements Clock {
    //TODO make it configurable
    private ZoneId zoneId = ZoneId.of("Asia/Shanghai");

    @Override
    public Date now() {
        return Date.from(ZonedDateTime.now(zoneId).toInstant());
    }
}
