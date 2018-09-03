package com.psys.hobb.sys.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2SsoDefaultConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableWebSecurity
public class WebSecurityConfig {

    @Configuration
    @Order(99) //默认的OAuth2SsoDefaultConfiguration顺序未100,不得已覆盖其顺序,解决csrf无效问题
    public static class ApiWebSecurityConfigurationAdapter extends OAuth2SsoDefaultConfiguration {

        private ApplicationContext applicationContext;

        @Autowired
        public ApiWebSecurityConfigurationAdapter(ApplicationContext applicationContext) {
            super(applicationContext);
        }

        protected void configure(HttpSecurity http) throws Exception {
            http
                .authorizeRequests()
                    /** 配置项目请求对应的角色权限 */
                    .antMatchers("/tourist/**").permitAll()
                    .antMatchers("/assets/**", "/resource/**").permitAll()
                    .anyRequest().authenticated()
                .and().csrf().disable();
            super.configure(http);
        }

    }

}