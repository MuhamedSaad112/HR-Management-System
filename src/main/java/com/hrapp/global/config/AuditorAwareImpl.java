package com.hrapp.global.config;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("auditorProvider")
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        // الحصول على اسم المستخدم من Spring Security
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.of("system"); // قيمة افتراضية إذا لم يكن هناك مستخدم
        }
        
        return Optional.of(authentication.getName());
    }
}
