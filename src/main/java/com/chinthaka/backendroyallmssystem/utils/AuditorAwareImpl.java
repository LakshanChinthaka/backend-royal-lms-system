package com.chinthaka.backendroyallmssystem.utils;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.empty();
        }
        return Optional.of(authentication.getName());
    }

//    @Override
//    public Optional getCurrentAuditor() {
//        String loggedInUser = SecurityContextHolder.getContext().getAuthentication().getName();
//        return Optional.of(loggedInUser);
//    }
}
