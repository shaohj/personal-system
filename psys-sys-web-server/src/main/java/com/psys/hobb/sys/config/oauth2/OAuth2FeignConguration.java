package com.psys.hobb.sys.config.oauth2;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import java.util.Optional;

@Configuration
public class OAuth2FeignConguration {

    @Bean("oAuth2ClientContext")
    public OAuth2ClientContext oAuth2ClientContext() {

        return new DefaultOAuth2ClientContext() {
            @Override
            public OAuth2AccessToken getAccessToken() {
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                if(auth.getDetails() instanceof WebAuthenticationDetails){
                    return null;
                }
                return Optional.ofNullable(super.getAccessToken())
                        .orElse(new DefaultOAuth2AccessToken(
                                ((OAuth2AuthenticationDetails) auth.getDetails())
                                        .getTokenValue()));
            }
        };
    }

    @Bean
    public OAuth2FeignRequestInterceptor requestInterceptor(@Qualifier("oAuth2ClientContext") OAuth2ClientContext oAuth2ClientContext) {
        return new CustOAuth2FeignRequestInterceptor(oAuth2ClientContext, new OAuth2FeignResourceDetails());
    }

}
