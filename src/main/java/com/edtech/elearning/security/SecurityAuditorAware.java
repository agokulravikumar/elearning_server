package com.edtech.elearning.security;

import com.edtech.elearning.util.AuthUtil;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class SecurityAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of(AuthUtil.getSessionUserID());
    }
}
