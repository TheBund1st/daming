package com.thebund1st.daming.security.ratelimiting;

import java.util.ArrayList;
import java.util.List;

public class Errors {
    private List<String> messages;

    private Errors() {
    }

    public Errors append(String message) {
        if (this.messages == null) {
            this.messages = new ArrayList<>();
        }
        this.messages.add(message);
        return this;
    }

    public static Errors empty() {
        return new Errors();
    }

    public boolean isEmpty() {
        return (messages == null || messages.isEmpty());
    }

    public String toMessage() {
        return isEmpty() ? "" : String.join(";", this.messages);
    }
}
