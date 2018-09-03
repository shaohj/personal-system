//package com.psys.hobb.sys.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
//import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
//
//@Configuration
//@EnableOAuth2Sso //这么写的话在BaseController中强转OAuth2AuthenticationDetails detail = (OAuth2AuthenticationDetails)auth.getDetails();时报错：{"errorNO":500,"errorMsg":"org.springframework.security.web.authentication.WebAuthenticationDetails cannot be cast to org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails","result":null}
//public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
//
//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable();
//    }
//}