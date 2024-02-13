package com.edtech.elearning.util;

import com.edtech.elearning.model.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthUtil {

    private AuthUtil() {
    }

    public static User getSession() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Object userDetails = authentication.getPrincipal();

            if (userDetails instanceof User user) {
                return user;
            }
        }

        return null;
    }

    public static String getSessionUserID() {
        User user = getSession();

        if (user != null) {
            return user.getUserId().toString();
        }

        return "0";
    }

}
