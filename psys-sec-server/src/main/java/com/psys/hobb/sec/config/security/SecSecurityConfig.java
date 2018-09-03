package com.psys.hobb.sec.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
                    .antMatchers("/sys/user/query/getByUserName", "/sys/user/query/getByUserNameAndPassword", "/sys/resourcetourist/query/getTouristMenu",
                            "/sys/code/query/getByCodeType/**").permitAll()
                    //.antMatchers(HttpMethod.GET, "/sys/**").access("#oauth2.hasScope('app')")
                    //.antMatchers(HttpMethod.POST, "/sys/**").access("#oauth2.hasScope('app')")
                    .anyRequest().access("#oauth2.hasScope('app')");
    }
}
