package com.thebund1st.daming.time;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class Clock {

    //TODO make it configurable
    private ZoneId zoneId = ZoneId.of("Asia/Shanghai");

    public ZonedDateTime now() {
        return LocalDateTime.now().atZone(zoneId);
    }
}
