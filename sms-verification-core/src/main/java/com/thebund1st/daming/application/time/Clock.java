package com.thebund1st.daming.application.time;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public class Clock {

    //TODO make it configurable
    private ZoneId zoneId = ZoneId.of("Asia/Shanghai");

    public ZonedDateTime now() {
        return ZonedDateTime.now(zoneId);
    }
}
