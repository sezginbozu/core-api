package com.boz.control;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.In;
import javax.inject.Inject;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity.IgnoredRequestConfigurer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

    @Inject
    ObjectProvider<SecurityExceptionConfig> exceptionsProvider;

    @Override
    public void configure(WebSecurity web) throws Exception {
        IgnoredRequestConfigurer setting = web.ignoring()
                .antMatchers("/actuator/**")
                .antMatchers("/favicon.ico")
                .antMatchers("/configuration/ui")
                .antMatchers("/swagger-resources")
                .antMatchers("/configuration/security")
                .antMatchers("/v3/api-docs")
                .antMatchers("/v3/api-docs/swagger-config")
                .antMatchers("/swagger-ui/index.html")
                .antMatchers("/swagger-ui/favicon-32x32.png")
                .antMatchers("/swagger-ui/favicon-16x16.png")
                .antMatchers("/swagger-ui/swagger-ui.css")
                .antMatchers("/swagger-ui/swagger-ui-bundle.js")
                .antMatchers("/auth/jwt")
                .antMatchers("/swagger-ui/swagger-ui-standalone-preset.js");

        SecurityExceptionConfig exceptions = exceptionsProvider.getIfAvailable();
        if (exceptions != null) {
            exceptions.noAuthCheckForUrls().forEach(setting::antMatchers);
        }
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/**")
                .addFilterBefore(new AuthenticationFilter(getApplicationContext()), BasicAuthenticationFilter.class)
                .authorizeRequests().anyRequest().authenticated().and().httpBasic().and().cors().and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    }

    @Bean
    public RequestContextListener requestContextListener() {
        return new RequestContextListener();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*").allowedMethods("GET", "POST", "PUT", "DELETE");
    }

    // for Swagger token
    @Bean
    public OpenAPI customOpenAPI() {

        return new OpenAPI()
                .components(
                        new Components()
                                .addSecuritySchemes("bearerAuth",
                                        new SecurityScheme()
                                                .name("bearerAuth")
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT"))
                                .addSecuritySchemes("X-Authenticated-User-Roles",
                                        new SecurityScheme()
                                                .name("X-Authenticated-User-Roles")
                                                .type(SecurityScheme.Type.APIKEY)
                                                .in(In.HEADER))

                )
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .addSecurityItem(new SecurityRequirement().addList("X-Authenticated-User-Roles"));

    }

}
