package com.thebund1st.paa.command.interceptor;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Return {
    @Getter
    private List<String> flags = new ArrayList<>();

    void append(String i) {
        flags.add(i);
    }
}
