package com.psys.hobb.zuul.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
public class SecSecurityConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.
            sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .requestMatchers().anyRequest()
            .and()
                .anonymous() //allow anonymous access
            .and()
                .authorizeRequests()
                    .antMatchers("/api-sec/**").permitAll()
                    //.antMatchers(HttpMethod.GET, "/sys/**").access("#oauth2.hasScope('app')")
                    //.antMatchers(HttpMethod.POST, "/sys/**").access("#oauth2.hasScope('app')")
                    .anyRequest().access("#oauth2.hasScope('app')");
    }
}
