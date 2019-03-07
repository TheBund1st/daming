package com.thebund1st.daming.jwt;

import java.security.Key;

public interface JwtKeyLoader {
    Key getKey();
}
