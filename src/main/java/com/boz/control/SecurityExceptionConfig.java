package com.boz.control;

import java.util.List;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

/**
 * Implement this class in your api project to give public access to urls
 * ie.
 * return List.of(
 * "/user_authorization",
 * "/user_authorization_status",
 * "/application_messages");
 * 
 *
 */
@Configuration
@ConditionalOnProperty(name = "has.public.urls")
public interface SecurityExceptionConfig {

    public List<String> noAuthCheckForUrls();
}
