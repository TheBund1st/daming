package com.foo.smslogin;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserRepository {
    private final List<UserHasMobilePhoneNumber> users;

    public UserRepository() {
        this.users = new ArrayList<>();
        UserHasMobilePhoneNumber user = new UserHasMobilePhoneNumber();
        user.setUsername("admin");
        user.setDisplayName("John Doe");
        user.setMobile("13917777777");
        this.users.add(user);
    }

    public UserHasMobilePhoneNumber mustFindByMobile(String mobile) {
        return users.stream().
                filter(u -> u.matchMobile(mobile))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Cannot find user with mobile " + mobile));
    }
}
