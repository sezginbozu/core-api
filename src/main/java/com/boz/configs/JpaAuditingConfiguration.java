package com.boz.configs;

import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.boz.entity.User;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class JpaAuditingConfiguration implements AuditorAware<User>{

    @Bean
    public AuditorAware<String> auditorProvider() {
        return new AuditorAware<String>() {
            @Override
            public java.util.Optional<String> getCurrentAuditor() {
            	UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken)SecurityContextHolder.getContext().getAuthentication();
                return Optional.ofNullable(((User)auth.getPrincipal()).getUserId());
            }
        };
    }

	@Override
	public Optional<User> getCurrentAuditor() {
		return Optional.ofNullable(SecurityContextHolder.getContext())
	            .map(SecurityContext::getAuthentication)
	            .filter(Authentication::isAuthenticated)
	            .map(Authentication::getPrincipal)
	            .map(User.class::cast);
	}
}
